package com.example.transpic;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SelFunActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView txtColor;
    private TextView txtSketch;
    private TextView txtRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_fun);

        TitleBuilder titleBuilder =  new TitleBuilder(this);
        titleBuilder.setTitleText("美化图片");
        titleBuilder.setLeftIco(R.drawable.btn_back_selector);
        titleBuilder.setRightIco(R.drawable.btn_download_selector);
        titleBuilder.setLeftIcoListening(leftBackListener);
        titleBuilder.setRightIcoListening(rightDownloadListener);

        //判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/

        bindView();
}

    //组件初始化与事件绑定
    private void bindView() {
        txtColor = (TextView)findViewById(R.id.txt_color);
        txtSketch = (TextView)findViewById(R.id.txt_sketch);
        txtRepair = (TextView)findViewById(R.id.txt_repair);

        txtColor.setOnClickListener(this);
        txtSketch.setOnClickListener(this);
        txtRepair.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    public void  resetPreState(){
        txtColor.setPressed(false);
        txtSketch.setPressed(false);
        txtRepair.setPressed(false);
    }

    private View.OnClickListener leftBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //返回主界面
        }
    };

    private View.OnClickListener rightDownloadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           //下载并保存
            Intent intent = new Intent(SelFunActivity.this, ShareActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_color:
                //黑白照片变彩色功能
                resetPreState();
                Toast.makeText(SelFunActivity.this, "color", Toast.LENGTH_LONG).show();
                break;

            case R.id.txt_sketch:
                //漫画线稿上色功能
                resetPreState();
                Toast.makeText(SelFunActivity.this, "sketch", Toast.LENGTH_LONG).show();
                break;

            case R.id.txt_repair:
                //破损图片修复功能
                resetPreState();
                Toast.makeText(SelFunActivity.this, "repair", Toast.LENGTH_LONG).show();
                break;

                default:
                    break;
        }
    }
}
