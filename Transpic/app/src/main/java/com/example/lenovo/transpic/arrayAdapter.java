package com.example.lenovo.transpic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class arrayAdapter extends AppCompatActivity {

    private List<Practic> practicList = new ArrayList<Practic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter);

        initFruits(); // 初始化每栏数据数据
        PracticAdapter adapter = new PracticAdapter(arrayAdapter.this, R.layout.activity_gspic, practicList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void initFruits() {
        Practic Yangtuo = new Practic("Yangtuo", R.drawable.yangtuo, "15301146_1");
        practicList.add(Yangtuo);
        Practic Haibao = new Practic("Haibao", R.drawable.haibao, "15301146_2");
        practicList.add(Haibao);
        Practic Kenan = new Practic("Kenan", R.drawable.kenan, "15301146_3");
        practicList.add(Kenan);

    }
}

