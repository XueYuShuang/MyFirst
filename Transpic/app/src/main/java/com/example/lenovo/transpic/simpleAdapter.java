package com.example.lenovo.transpic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class simpleAdapter extends AppCompatActivity {


    private SimpleAdapter simAdapt;
    private ListView listView;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    private List<Practic> practicList = new ArrayList<Practic>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);

        // 初始化每栏数据数据
        initFruits(R.drawable.yangtuo,"yangtuo","15301146_1");
        initFruits(R.drawable.haibao,"haibao","15301146_2");
        initFruits(R.drawable.kenan,"kenan","15301146_3");
        ListView listView = (ListView) findViewById(R.id.list_view1);
        simAdapt = new SimpleAdapter(
                this,
                data,
                R.layout.activity_gspic,
                new String[] { "imageId", "name", "SID" },// 与下面数组元素要一一对应
                new int[] { R.id.P_imageView2, R.id.P_textView2, R.id.P_textView3 });
        listView.setAdapter(simAdapt);

    }

    private void initFruits(Object image, String name, String sid) {

        Map<String, Object> item = new HashMap<String, Object>();
        item.put("imageId",image);
        item.put("name",name);
        item.put("SID",sid);
        data.add(item);
    }
}
