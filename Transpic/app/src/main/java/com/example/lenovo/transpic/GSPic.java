package com.example.lenovo.transpic;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GSPic extends AppCompatActivity {


    private TextView textview;
    private ImageButton Cpic;   // 从笨蛋相册选取图片
    private ImageButton Ppic;   // 拍照
    private ImageButton Rpic;   // 返回主界面
    private ImageButton Gpic;   // 查看服务器处理过的图片

    private ImageButton Spic;   // 保存图片至本地
    private ImageButton Opic;   // 上传图片至云

    private String str="fun";
    private int funs = 0;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        Intent i = getIntent();
        textview=(TextView)findViewById(R.id.textView);
        textview.setText(i.getStringExtra("data"));// 获取参数

        Cpic = (ImageButton) findViewById(R.id.Ch_imagebutton3);
        Cpic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                funs=1;// 从相册选取
                //打开本地相册
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //设定结果返回
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        Ppic = (ImageButton) findViewById(R.id.Ch_imagebutton2);
        Ppic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                funs=2;// 拍照
                //打开相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //设定结果返回
                startActivityForResult(intent, 1);
            }
        });

        Gpic = (ImageButton) findViewById(R.id.Ch_imagebutton1);
        Gpic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                funs=3;// 查看服务器处理过后的图片
                //使用Intent
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(intent);

            }
        });

        Rpic = (ImageButton) findViewById(R.id.CH_imageButton6);
        Rpic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(GSPic.this, MainActivity.class);
                startActivity(i);
            }
        });

        Spic = (ImageButton) findViewById(R.id.Ch_imagebutton5);
        Spic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {



            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (funs == 1){

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

                //获取返回的数据，这里是android自定义的Uri地址
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                //获取选择照片的数据视图
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                //从数据视图中获取已选择图片的路径
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                //将图片显示到界面上
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }else if (funs == 2){
            int i = 0;

            if (resultCode == Activity.RESULT_OK) {

                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Log.v("TestFile",
                            "SD card is not avaiable/writeable right now.");
                    return;
                }

                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                FileOutputStream b = null;
                File file = new File("/sdcard/TranspicImage/");
                file.mkdirs();// 创建文件夹，名称为myimage

                //照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。
                // 网上流传的其他Demo这里的照片名称都写死了，则会发生无论拍照多少张，后一张总会把前一张照片覆盖。
                // 还可以设置这个字符串，比如加上“ＩＭＧ”字样等；然后就会发现ｓｄ卡中ｍｙｉｍａｇｅ这个文件夹下，
                // 会保存刚刚调用相机拍出来的照片，照片名称不会重复。

                String str=null;
                Date date=null;
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//获取当前时间，进一步转化为字符串
                date =new Date();
                str=format.format(date);
                String fileName = "/sdcard/TranspicImage/"+str+".jpg";
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }else if (funs == 3){

                funs = 3;
        }
        }



}








