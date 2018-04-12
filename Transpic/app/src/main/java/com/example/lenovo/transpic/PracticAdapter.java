package com.example.lenovo.transpic;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
/**
 * Created by lenovo on 2018/4/10.
 */

public class PracticAdapter extends ArrayAdapter {

    private final int resourceId;

    public PracticAdapter(Context context, int textViewResourceId, List<Practic> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Practic practic = (Practic) getItem(position); // 获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        ImageView practicImage = (ImageView) view.findViewById(R.id.P_imageView2);//获取该布局内的图片视图
        TextView practicName = (TextView) view.findViewById(R.id.P_textView2);//获取该布局内的文本视图
        TextView practicSID = (TextView) view.findViewById(R.id.P_textView3);//获取该布局内的文本视图

        practicImage.setImageResource(practic.getImageId());//为图片视图设置图片资源
        practicName.setText(practic.getName());//为文本视图设置文本内容
        practicSID.setText(practic.getSID());//为文本视图设置文本内容
        return view;
    }



}
