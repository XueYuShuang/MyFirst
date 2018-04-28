package com.example.lenovo.transpic;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


//import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

//import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.lenovo.transpic.Common.String2Bitmap;

public class GSPic extends AppCompatActivity {


    private TextView textview;
    private ImageButton Cpic;   // 从笨蛋相册选取图片
    private ImageButton Ppic;   // 拍照
    private ImageButton Rpic;   // 返回主界面
    private ImageButton Gpic;   // 查看服务器处理过的图片
    private ImageButton Spic;   // 保存图片至本地
    private ImageButton Opic;   // 上传图片至云
    private ImageView image;
    private ImageView image1;

    private String str = "fun";
    private int funs = 0;
    private static int RESULT_LOAD_IMAGE = 1;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1 :
                    //bt.setText("正在下载...");
                    textview.setText("123321321");
                    break;
                case 2 :
                    Bitmap bm = (Bitmap) msg.obj;
                    //iv.setImageBitmap(bm);
                    break;
                case 3 :
                    Bundle bundle = msg.getData();
                    String data = bundle.getString("result");
                    String str1 = "";
                    String str2 = "";
                    //String str3 = "";

                    JSONObject json = JSONObject.parseObject(data);
                    str1 = json.getString("imagename");
                    str2 = json.getString("b");
                    //str3 = json.getString("a");

                    if(str1 != null && str2 != null){
                        textview.setText(str1);

                        Bitmap bitmap = ((BitmapDrawable) ((ImageView) image1).getDrawable()).getBitmap();
                        str = Common.convertIconToString(bitmap);
                        Log.v(" - - - -- - - - - - -","***********************");


                        ImageView imageView = (ImageView) findViewById(R.id.CH_imageView);


                        imageView.setImageBitmap(Common.convertStringToIcon(str2));
                     }

                    //imageView.setImageBitmap(bitmap);

                    //bt.setText(data);
                    break;
            }
        }



    };
    private String imagename = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        checkPermission();
        Intent i = getIntent();
        textview = (TextView) findViewById(R.id.textView);
        textview.setText(i.getStringExtra("data"));// 获取主界面跳转参数

        image = (ImageView) findViewById(R.id.CH_imageView);

        // 本地选取图片
        Cpic = (ImageButton) findViewById(R.id.Ch_imagebutton3);
        Cpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                funs = 1;// 从相册选取

                //打开本地相册
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //这里要传一个整形的常量RESULT_LOAD_IMAGE到startActivityForResult()方法。
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });



        // 拍照获取图片
        Ppic = (ImageButton) findViewById(R.id.Ch_imagebutton2);
        Ppic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                funs = 2;// 拍照
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

                funs = 3;// 查看服务器处理过后的图片

                //启动定时器
                mHandler.postDelayed(runnable, 1000);

                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://storage/emulated/0/DCIM/TranspicImage"));
                //startActivity(intent);
                //使用Intent
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                //startActivity(intent);

            }
        });

        // 返回按钮
        Rpic = (ImageButton) findViewById(R.id.CH_imageButton6);
        Rpic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(GSPic.this, MainActivity.class);
                startActivity(i);
            }
        });


        //保存图片至
        Spic = (ImageButton) findViewById(R.id.Ch_imagebutton4);
        Spic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ImageView imageView = (ImageView) findViewById(R.id.CH_imageView);
                imageView.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/screenshots/IMG_20170115_083039.jpg"));
            }
        });


        // 上传数据
        Opic = (ImageButton) findViewById(R.id.Ch_imagebutton5);
        Opic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                image1 = (ImageView) findViewById(R.id.imageView);
                //上传到服务器...
                //要传送的数据还有不仅仅是图片。
                // 之前传送数据都是通过json或者map转成String再转成byte[]实现的，
                // 那么现在传送的是图片+其他数据，思路便是：图片->String，
                // 然后和其他数据一起打包成json->String->byte[]->Bitmap
                //updateHeadImage(userId, headImage, handler);
                String userId = "15301146";
                Bitmap bitmap = ((BitmapDrawable) ((ImageView) image).getDrawable()).getBitmap();

                //根据返回的信息  执行不同更新操作
                Handler handler = new Handler()
                {
                    public void handleMessage(Message msg) {

                        switch (msg.what) {
                            case 1 :
                                //bt.setText("正在下载...");
                                textview.setText("123321321");
                                break;
                            case 2 :
                                Bitmap bm = (Bitmap) msg.obj;
                                //iv.setImageBitmap(bm);
                                break;
                            case 3 :
                                Bundle bundle = msg.getData();
                                String data = bundle.getString("result");
                                String str1 = "";
                                String str2 = "";
                                //String str3 = "";

                                JSONObject json = JSONObject.parseObject(data);
                                str1 = json.getString("a");
                                str2 = json.getString("b");
                                //str3 = json.getString("a");

                                if(str1 != null ){

                                    textview.setText(str1);
                                    imagename = str1;

                                    Bitmap bitmap = ((BitmapDrawable) ((ImageView) image1).getDrawable()).getBitmap();
                                    str = Common.convertIconToString(bitmap);
                                    Log.v(" - - - -- - - - - - -","***********************");


                                    ImageView imageView = (ImageView) findViewById(R.id.imageView);


                                    imageView.setImageBitmap(Common.convertStringToIcon(str2));

                                }




                                //imageView.setImageBitmap(bitmap);

                                //bt.setText(data);
                                break;
                        }
                    }
                };
                updateHeadImage(userId, bitmap, handler);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (funs == 1) {
            /*
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
                ImageView imageView = (ImageView) findViewById(R.id.CH_imageView);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                */

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                //查询我们需要的数据
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView imageView = (ImageView) findViewById(R.id.CH_imageView);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                Toast.makeText(this, picturePath, Toast.LENGTH_SHORT).show();
            }
        } else if (funs == 2) {
            int i = 0;

            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "fun2", Toast.LENGTH_SHORT).show();

                /*
                String sdStatus = Environment.getExternalStorageState();

                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Log.v("TestFile",
                            "SD card is not avaiable/writeable right now.");
                    return;
                }*/

                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                FileOutputStream b = null;

                File file = new File("/storage/emulated/0/DCIM/TranspicImage/");
                file.mkdirs();// 创建文件夹
                if (!file.exists()) {
                    Log.v("####",
                            "文件夹不存在");
                    file.mkdirs();// 创建文件夹
                    Log.v("####",
                            "文件夹已创建");
                }

                //照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。

                String str = null;
                Date date = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//获取当前时间，进一步转化为字符串
                date = new Date();
                str = format.format(date);
                String fileName = "/storage/emulated/0/DCIM/TranspicImage/" + str + ".jpg";
                Log.v("####",
                        "fileName");
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                    Toast.makeText(this, "图片保存成功:(DCIM/TranspicImage/" + str + ".jpg)", Toast.LENGTH_SHORT).show();

                    ImageView imageView = (ImageView) findViewById(R.id.CH_imageView);
                    imageView.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    Log.v("####",
                            "写入文件出错");
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
        } else if (funs == 3) {


            Bitmap bitmap = ((BitmapDrawable) ((ImageView) image).getDrawable()).getBitmap(); // 获取Bitmap
            FileOutputStream b = null;

            File file = new File("/storage/emulated/0/DCIM/TranspicImage/");
            if (!file.exists()) {
                file.mkdirs();// 创建文件夹
            }
            String str = null;
            Date date = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//获取当前时间，进一步转化为字符串
            date = new Date();
            str = format.format(date);
            String fileName = "/storage/emulated/0/DCIM/TranspicImage/" + str + ".jpg";
            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                Toast.makeText(this, "图片保存成功:(DCIM/TranspicImage/" + str + ".jpg)", Toast.LENGTH_SHORT).show();

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
    }

    public static void updateHeadImage(final String userId, final Bitmap image, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                RequestParams params = new RequestParams();

                try {
                    //jsonObject.put("userId", userId);

                    params.put("username", "15301146");
                    params.put("userImageContent", Common.convertIconToString(image));


                    //将Bitmap转成String
                    //jsonObject.put("userImageContent", Common.Bitmap2String(image));


                    String content = String.valueOf(params);

                    Log.i("content  - - ", content);

                    //请求地址
                    //String url = ConfigModel.getServerURL() + "user/updateImage";
                    String url = "http://47.94.146.51:8888/login";
                    String result = Common.sendPostRequest(url, content);//post请求服务器：数据content,请求路径url。
                    Log.i("result", result);

                    // 服务器返回结果
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    message.setData(bundle);
                    //message.what = R.id.textView;
                    message.what = 3;
                    handler.sendMessage(message);

                } catch (Exception e) {
                }
            }
        }.start();
    }

    Runnable runnable = new Runnable() {
        //RequestParams params = new RequestParams();

        @Override
        public void run() {
            //params.put("username", "15301146");

            String result = Common.sendPostRequest("http://47.94.146.51:8888/login","imagename="+imagename);
            // 循环调用实现定时刷新界面
            mHandler.postDelayed(this, 60000);
        }
    };



    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            //Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            //Log.e(TAG_SERVICE, "checkPermission: 已经授权！");
        }
    }

}








