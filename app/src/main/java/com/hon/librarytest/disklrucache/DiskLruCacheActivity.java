package com.hon.librarytest.disklrucache;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hon.librarytest.LibraryTest;
import com.hon.librarytest.R;
import com.hon.librarytest.glide.CircleCrop;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Frank on 2018/4/6.
 * E-mail:frank_hon@foxmail.com
 */

public class DiskLruCacheActivity extends AppCompatActivity{

    private Button mCacheButton;
    private ImageView mCacheImageView;

    private DiskLruCacheService mDiskLruCacheService=new DiskLruCacheService();
    private Bitmap mBitmap=null;
    private Handler mHandler=new MyHandler(this);

    private static final String IMAGE_URL="http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_lru_cache);

        mCacheImageView=findViewById(R.id.iv_cache);
        mCacheButton=findViewById(R.id.btn_cache);
        mCacheButton.setOnClickListener(
                v->{
                    mBitmap=mDiskLruCacheService.getImageFromCache(IMAGE_URL);
                    if(mBitmap!=null){
                        mCacheImageView.setImageBitmap(mBitmap);
                        Toast.makeText(this, "from cache", Toast.LENGTH_SHORT).show();
                    }else{
                        new Thread(
                                ()->{
                                    mBitmap=BitmapFactory.decodeStream(mDiskLruCacheService.getInputStream(IMAGE_URL));

                                    runOnUiThread(
                                            ()->{
                                                mCacheImageView.setImageBitmap(mBitmap);
                                                Toast.makeText(this, "from network", Toast.LENGTH_SHORT).show();
                                            }
                                    );
                                }
                        ).start();
                        mDiskLruCacheService.getImageFromNetwork(IMAGE_URL);
                    }
//                    loadImageFromCache();
//                    mDiskLruCacheService.cache();
                }
        );
        mDiskLruCacheService.setOnCacheCallback(new DiskLruCacheService.Callback() {
            @Override
            public void onCacheSuccess() {
                Message message=Message.obtain();
                mHandler.sendMessage(message);
            }

            @Override
            public void onCacheFailure() {
                Looper.prepare();
                Toast.makeText(DiskLruCacheActivity.this, "fail :)", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

    private void showImage(InputStream inputStream){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBitmap=BitmapFactory.decodeStream(inputStream);
                mCacheImageView.setImageBitmap(mBitmap);
                Toast.makeText(DiskLruCacheActivity.this, "success :)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImageFromCache() {
        Glide.with(this)
                .load(getImageFile())
                .transform(new CircleCrop(this))
                .into(mCacheImageView);
    }

    private Uri getUriString(){
//        ContentResolver.SCHEME_FILE
        String imageUrl="http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        String target="file:/"+ LibraryTest.sCacheDir+"/bitmap/"+mDiskLruCacheService.hashKeyForDisk(imageUrl)+".0.jpg";
        Log.d("DiskLruCacheActivity", "getUriString: "+target);
        return Uri.parse(target);
    }

    private File getImageFile(){
        String imageUrl="http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        File imageFile=new File(LibraryTest.sCacheDir+"/bitmap/"+mDiskLruCacheService.hashKeyForDisk(imageUrl)+".0.jpg");
        return imageFile;
    }

    private static class MyHandler extends Handler{

        private WeakReference<DiskLruCacheActivity> reference;
        MyHandler(DiskLruCacheActivity activity){
            reference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DiskLruCacheActivity activity=reference.get();
            Toast.makeText(activity, "success :)", Toast.LENGTH_SHORT).show();
        }
    }
}
