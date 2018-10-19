package com.hon.librarytest.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.hon.librarytest.AppExecutors;
import com.hon.librarytest.BuildConfig;
import com.hon.librarytest.LibraryTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Frank_Hon on 10/19/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class FileUtil {

    private static final String PICTURE_PATH = Environment.getExternalStorageDirectory() + File.separator
            + BuildConfig.APPLICATION_ID + File.separator + "LibraryTest";

    public static void savePicturesToGallery(File resource){
        try {
            File dir = new File(PICTURE_PATH);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    doSave(dir, resource);
                } else {
                    AppExecutors.getInstance().getMainExecutors().execute(
                            () -> ToastUtil.showToast("failed to save :(")
                    );
                }
            } else {
                doSave(dir, resource);
            }
        } catch (IOException e) {
            AppExecutors.getInstance().getMainExecutors().execute(
                    () -> {
                        ToastUtil.showToast("failed to save :( "+e.getMessage());
                    }
            );
        }
    }

    private static void doSave(File targetFileDir, File resource) throws IOException {
        File targetFile = new File(targetFileDir, resource.getName() + ".png");
        if (!targetFile.exists()) {

            AppExecutors.getInstance().getMainExecutors().execute(
                    ()->ToastUtil.showToast("saving...wait please:)")
            );

            Bitmap bitmap = BitmapFactory.decodeFile(resource.getPath());
            OutputStream outputStream = new FileOutputStream(targetFile);
            boolean result=bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // insert the db of system gallery
//            MediaStore.Images.Media.insertImage(getContentResolver(),
//                    targetFile.getPath(), targetFile.getName(), null);

            if(result){
                AppExecutors.getInstance().getMainExecutors().execute(
                        ()->ToastUtil.showToast("saved successfully :)")
                );
            }else{
                AppExecutors.getInstance().getMainExecutors().execute(
                        () -> ToastUtil.showToast("failed to save :(")
                );
            }


        } else {
            AppExecutors.getInstance().getMainExecutors().execute(
                    () -> ToastUtil.showToast("picture is existing in Gallery")
            );
        }

        Uri uri = Uri.fromFile(targetFile);
        LibraryTest.sLibraryTest.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }
}
