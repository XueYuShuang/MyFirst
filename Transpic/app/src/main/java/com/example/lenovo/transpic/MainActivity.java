package com.example.lenovo.transpic;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ImgBtnAlbum;
    private ImageButton ImgBtnCamera;

    /*    private ImageView iv_recycle;
    AnimationDrawable ivRecycleAnimation;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImgBtnAlbum = (ImageButton)findViewById(R.id.img_btn_album);
        ImgBtnCamera = (ImageButton)findViewById(R.id.img_btn_camera);

        ImgBtnAlbum.setOnClickListener(this);
        ImgBtnCamera.setOnClickListener(this);

        /*iv_recycle = (ImageView)findViewById(R.id.iv_recycle);
        iv_recycle.setBackgroundResource(R.drawable.img_anim);
        ivRecycleAnimation = (AnimationDrawable)iv_recycle.getBackground();
        ivRecycleAnimation.start();*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_btn_album:
                //选择相册

                break;

            case R.id.img_btn_camera:
               //相机拍照

                break;

                default:
                    break;
        }
    }
}
