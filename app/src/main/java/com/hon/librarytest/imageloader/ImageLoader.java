package com.hon.librarytest.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.hon.librarytest.LibraryTest;
import com.hon.librarytest.disklrucache.DiskLruCache;
import com.hon.librarytest.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Frank on 2018/9/9.
 * E-mail:frank_hon@foxmail.com
 */

public class ImageLoader {

    private static final String TAG=ImageLoader.class.getSimpleName();

    public static final int MESSAGE_POST_RESULT=1;

    private static final int CPU_COUNT=Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE=CPU_COUNT+1;
    private static final int MAXIMUM_POOL_SIZE=CPU_COUNT*2+1;
    private static final long KEEP_ALIVE=10L;

    private static final int TAG_KEY_URI=Integer.valueOf("ImageView");
    private static final long DISK_CACHE_SIZE=1024*1024*50;
    private static final int IO_BUFFER_SIZE=1024*8;
    private static final int DISK_CACHE_INDEX=0;
    private boolean mIsDiskLruCacheCreated=false;

    private static final ThreadFactory sThreadFactory=new ThreadFactory() {

        private final AtomicInteger mCount=new AtomicInteger();

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r,"ImageLoader#"+mCount.getAndIncrement());
        }
    };

    private static final Executor THREAD_POOL_EXECUTOR=new ThreadPoolExecutor(
      CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),sThreadFactory
    );

    private Handler mMainHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result= (LoaderResult) msg.obj;
            ImageView imageView=result.imageView;
            String uri= (String) imageView.getTag(TAG_KEY_URI);

            if(uri.equals(result.url)){
                imageView.setImageBitmap(result.bitmap);
            }else {
                Log.d(TAG, "ignored when url of image changed... ");
            }
        }
    };
    private Context mContext;
    private ImageResizer mImageResizer=new ImageResizer();
    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private ImageLoader(Context context) {
        mContext=context.getApplicationContext();
        int maxMemory= (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheSize=maxMemory/8;
        mMemoryCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };

        File diskCacheDir=new File(LibraryTest.sCacheDir+File.pathSeparator+"imageloader");

        if(!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }

        if(Util.getAvailableSpace(diskCacheDir)>DISK_CACHE_SIZE){
            try {
                mDiskLruCache=DiskLruCache.open(diskCacheDir,1,1,
                        DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ImageLoader build(Context context){
        return new ImageLoader(context);
    }

    private void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if(getBitmapFromMemoryCache(key)==null){
            mMemoryCache.put(key,bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }
    public void bindBitmap(final String uri, final ImageView imageView){
        bindBitmap(uri,imageView,0,0);
    }

    public void bindBitmap(String uri, ImageView imageView, int reqWidth, int reqHeight) {
        imageView.setTag(TAG_KEY_URI,uri);
        Bitmap bitmap=loadBitmapFromMemoryCache(uri);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }

        THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap b=loadBitmap(uri,reqWidth,reqHeight);
                if (b!=null){
                    LoaderResult result=new LoaderResult(imageView,uri,b);
                    mMainHandler
                            .obtainMessage(MESSAGE_POST_RESULT,result)
                            .sendToTarget();
                }
            }
        });

    }

    private Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap=loadBitmapFromMemoryCache(uri);
        if (bitmap!=null){
            return bitmap;
        }

        try {
            bitmap=loadBitmapFromDiskCache(uri,reqWidth,reqHeight);
            if (bitmap!=null){
                return bitmap;
            }
            bitmap=loadBitmapFromHttp(uri,reqWidth,reqHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(bitmap==null&&!mIsDiskLruCacheCreated){
            bitmap=downloadBitmapFromUrl(uri);
        }

        return bitmap;
    }

    private Bitmap downloadBitmapFromUrl(String urlString) {

        if(Looper.myLooper()==Looper.getMainLooper()){
            throw new RuntimeException("can't visit network from UI thread");
        }

        Bitmap bitmap=null;
        InputStream in=null;
        HttpURLConnection connection=null;

        try {
            URL url=new URL(urlString);
            connection= (HttpURLConnection) url.openConnection();
            in=new BufferedInputStream(connection.getInputStream(),IO_BUFFER_SIZE);
            bitmap= BitmapFactory.decodeStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
            try {
                if (in!=null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight) throws IOException {
        if(Looper.myLooper()==Looper.getMainLooper()){
            throw new RuntimeException("can't visit network from UI thread");
        }

        if (mDiskLruCache==null){
            return null;
        }

        String key=hashKeyFromUrl(url);
        DiskLruCache.Editor editor=mDiskLruCache.edit(key);
        if (editor!=null){
            OutputStream outputStream=editor.newOutputStream(DISK_CACHE_INDEX);
            if(downloadUrlToStream(url,outputStream)){
                editor.commit();
            }else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url, reqWidth, reqHeight);
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {

        if(Looper.myLooper()==Looper.getMainLooper()){
            throw new RuntimeException("can't visit network from UI thread");
        }

        HttpURLConnection connection=null;
        OutputStream out=null;
        InputStream in=null;

        try {
            URL url=new URL(urlString);
            connection= (HttpURLConnection) url.openConnection();
            in=new BufferedInputStream(connection.getInputStream(),IO_BUFFER_SIZE);
            out=new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);

            int b;
            while ((b=in.read())!=-1){
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
            try {
                if (out!=null)
                    out.close();
                if (in!=null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Bitmap loadBitmapFromDiskCache(String uri, int reqWidth, int reqHeight) throws IOException {
        if(Looper.myLooper()==Looper.getMainLooper()){
            Log.w(TAG, "loading bitmap from UI thread is not recommended");
        }

        if (mDiskLruCache==null){
            return null;
        }

        Bitmap bitmap=null;
        String key=hashKeyFromUrl(uri);
        DiskLruCache.Snapshot snapshot=mDiskLruCache.get(key);

        if (snapshot!=null){
            FileInputStream fileInputStream= (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor=fileInputStream.getFD();
            bitmap=mImageResizer.decodeSampledBitmapFromFileDescriptor(
                    fileDescriptor,reqWidth,reqHeight
            );
            if (bitmap!=null){
                addBitmapToMemoryCache(key,bitmap);
            }
        }

        return bitmap;
    }

    private Bitmap loadBitmapFromMemoryCache(String url){
        final String key=hashKeyFromUrl(url);
        Bitmap bitmap=getBitmapFromMemoryCache(key);

        return bitmap;
    }

    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey=bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey=String.valueOf(url.hashCode());
        }

        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            String hex=Integer.toHexString(0xFF&bytes[i]);
            if(hex.length()==1){
                sb.append("0");
            }

            sb.append(hex);
        }

        return sb.toString();
    }

    private static class LoaderResult{
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String url, Bitmap bitmap) {
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
