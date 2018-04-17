package com.example.lenovo.transpic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private ImageButton fun1;
    private ImageButton  fun2;
    private ImageButton  fun3;
    private ImageButton  fun4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fun1=(ImageButton )findViewById(R.id.imagebutton1);
        fun2=(ImageButton )findViewById(R.id.imagebutton2);
        fun3=(ImageButton )findViewById(R.id.imagebutton3);
        fun4=(ImageButton )findViewById(R.id.imagebutton4);

        //下面为各个按钮设置事件监听
        fun1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GSPic.class);
                i.putExtra("data", "fun1");// 传递参数
                startActivity(i);
            }
        });

        fun2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(MainActivity.this, GSPic.class);
                //o.putExtra("data", "fun2");// 传递参数
                startActivity(o);
            }
        });

        fun3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, GSPic.class);
                //j.putExtra("data", "fun3");// 传递参数
                startActivity(j);
            }
        });

        fun4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, GSPic.class);
                k.putExtra("data", "fun4");// 传递参数
                startActivity(k);

            }
        });

    }






}
