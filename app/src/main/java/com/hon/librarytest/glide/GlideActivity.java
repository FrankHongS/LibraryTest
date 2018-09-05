package com.hon.librarytest.glide;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.hon.librarytest.R;
import com.hon.librarytest.util.Constants;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class GlideActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        initViews();
    }

    private void initViews() {
        mButton=findViewById(R.id.btn);
        mImageView=findViewById(R.id.iv);

        MultiTransformation<Bitmap> multi=new MultiTransformation<>(
               new BlurTransformation(this),new CropCircleTransformation(this)
        );
        mButton.setOnClickListener(
                view->Glide
                        .with(this)
                        .load(Constants.IMAGE_URI_01)
//                        .dontTransform()
//                        .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
//                        .override(500,500)
//                        .centerCrop()
                        .transform(new CircleCrop(this))
//                        .bitmapTransform(new BlurTransformation(this))
//                        .bitmapTransform(
//                                new BlurTransformation(this),new CropCircleTransformation(this)
//                        )
//                        .bitmapTransform(new MaskTransformation())
                        .into(mImageView)
        );
    }
}
