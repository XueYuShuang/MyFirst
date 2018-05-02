package com.example.transpic;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lnnn on 2018/5/1.
 */

public class TitleBuilder {

    private View view;
    private RelativeLayout rlytTitleBar;
    private TextView txtTitle;
    private ImageView ivLeftIco;
    private ImageView ivRightIco;

    public TitleBuilder(Activity context){
        view = context.findViewById(R.id.rlyt_title_bar);
        rlytTitleBar = (RelativeLayout)view.findViewById(R.id.rlyt_title_bar);
        txtTitle = (TextView)view.findViewById(R.id.txt_title);
        ivLeftIco = (ImageView)view.findViewById(R.id.iv_left_ico);
        ivRightIco = (ImageView)view.findViewById(R.id.iv_right_ico);
    }

    //设置标题栏文字
    public TitleBuilder setTitleText(String titleText){
        if (!TextUtils.isEmpty(titleText)) {
            txtTitle.setText(titleText);
        }
        return this;
    }

    //设置标题栏左边显示图片
    public TitleBuilder setLeftIco(int resId){
        ivLeftIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        ivLeftIco.setImageResource(resId);
        return this;
    }

    //设置标题栏左边显示图片
    public TitleBuilder setRightIco(int resId){
        ivRightIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        ivRightIco.setImageResource(resId);
        return this;
    }

    //用于设置标题栏左边图片的单击事件
    public TitleBuilder setLeftIcoListening(View.OnClickListener listener){
        if(ivLeftIco.getVisibility() == View.VISIBLE){
            ivLeftIco.setOnClickListener(listener);
        }
        return this;
    }

    //用于设置标题栏右边图片的单击事件
    public TitleBuilder setRightIcoListening(View.OnClickListener listener){
        if(ivRightIco.getVisibility() == View.VISIBLE){
            ivRightIco.setOnClickListener(listener);
        }
        return this;
    }
}
