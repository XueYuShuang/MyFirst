package com.example.lenovo.transpic;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private ImageButton fun1;
    private ImageButton fun2;
    private ImageButton fun3;
    private ImageButton fun4;
    public static final int CAMERA_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        fun1 = (ImageButton) findViewById(R.id.imagebutton1);
        fun2 = (ImageButton) findViewById(R.id.imagebutton2);
        fun3 = (ImageButton) findViewById(R.id.imagebutton3);
        fun4 = (ImageButton) findViewById(R.id.imagebutton4);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)
                        .setMessage("申请相机权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        } else {

            //Toast.makeText(this, "相机权限-GET", Toast.LENGTH_SHORT).show();
        }



        //下面为各个按钮设置事件监听
        fun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, GSPic.class);
                i.putExtra("data", "fun1");// 传递参数
                startActivity(i);
            }
        });

        fun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent o = new Intent(MainActivity.this, GSPic.class);
                o.putExtra("data", "fun2");// 传递参数
                startActivity(o);
            }
        });

        fun3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, GSPic.class);
                j.putExtra("data", "fun3");// 传递参数
                startActivity(j);
            }
        });

        fun4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, GSPic.class);
                k.putExtra("data", "fun4");// 传递参数
                startActivity(k);

            }
        });
    }
}
