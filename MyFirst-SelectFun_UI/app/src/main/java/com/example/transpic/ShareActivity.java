package com.example.transpic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView ivQQ;
    private ImageView ivQZone;
    private ImageView ivWechat;
    private ImageView ivMicroblog;
    private ImageView ivCircleOfFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TitleBuilder titleBuilder =  new TitleBuilder(this);
        titleBuilder.setTitleText("保存/分享");
        titleBuilder.setLeftIco(R.drawable.btn_back_selector);
        titleBuilder.setRightIco(R.drawable.btn_home_selector);
        titleBuilder.setLeftIcoListening(leftBackListener);
        titleBuilder.setRightIcoListening(rightHomeListener);

        bindView();
    }

    private void bindView() {
        ivQQ = (ImageView)findViewById(R.id.btn_qq);
        ivQZone = (ImageView)findViewById(R.id.btn_qzone);
        ivWechat= (ImageView)findViewById(R.id.btn_wechat);
        ivMicroblog = (ImageView)findViewById(R.id.btn_microblog);
        ivCircleOfFriends = (ImageView)findViewById(R.id.btn_circle_of_friends);

        ivCircleOfFriends.setOnClickListener(this);
        ivMicroblog.setOnClickListener(this);
        ivWechat.setOnClickListener(this);
        ivQZone.setOnClickListener(this);
        ivQQ.setOnClickListener(this);
    }

    private View.OnClickListener leftBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //返回美化图片界面
            finish();
        }
    };

    private View.OnClickListener rightHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //返回主界面
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qq:
                break;

            case R.id.btn_qzone:
                break;

            case R.id.btn_wechat:
                break;

            case R.id.btn_microblog:
                break;

            case R.id.btn_circle_of_friends:
                break;

                default:
                    break;
        }
    }
}
