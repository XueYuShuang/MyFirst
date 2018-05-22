package com.example.lenovo.transpic;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.io.InputStream;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class SelFunActivity extends AppCompatActivity implements View.OnClickListener {
    private static TextView txtColor;
    private static TextView txtSketch;
    private static TextView txtRepair;
    private static int CAMERA_REQUEST_CODE = 1;
    private int permissions_state = 0;
    //private ImageView image;
    private String str = "fun";
    private String ImagePath = null;
    private static String imagename = "default" ;
    private static String stop = "default" ;
    private PhotoView iv_photo;
    private PhotoViewAttacher attacher;
    long delay = 5000;
    long period = 3000;
    private int CS_state = 0;// 发送状态码
    private String CS_str = null;
    private int T_state = 0;// 定时器状态码
    private String T_str = null;
    private static String info = null;
    //Timer timer = new Timer();

    int isChange = 0;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_fun);
        MyApplication.addDestoryActivity(this,"SelFunActivity");

        if(!MyApplication.destoryMap.isEmpty()){
            MyApplication.destoryActivity("MainActivity");
        }

        TitleBuilder titleBuilder = new TitleBuilder(this);
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

        Intent i = getIntent();
        ImagePath = i.getStringExtra("ImagePath");
        iv_photo.setImageBitmap(BitmapFactory.decodeFile(ImagePath));
    }

    //组件初始化与事件绑定
    private void bindView() {
        txtColor = (TextView) findViewById(R.id.txt_color);
        txtSketch = (TextView) findViewById(R.id.txt_sketch);
        txtRepair = (TextView) findViewById(R.id.txt_repair);
        iv_photo = (PhotoView) findViewById(R.id.iv_photo);

        txtColor.setOnClickListener(this);
        txtSketch.setOnClickListener(this);
        txtRepair.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    public static void resetPreState() {
        txtColor.setPressed(false);
        txtSketch.setPressed(false);
        txtRepair.setPressed(false);
    }

    private View.OnClickListener leftBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(SelFunActivity.this, MainActivity.class);
            startActivity(i);
            //返回主界面
        }
    };

    private View.OnClickListener rightDownloadListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String state = "OK";
            //state = SendImage("g");
            if (state == "OK") {

                //下载并保存
                Bitmap bitmap = ((BitmapDrawable) ((PhotoView) iv_photo).getDrawable()).getBitmap(); // 获取Bitmap
                state = Common.SaveImage(bitmap);

                if (state == "Error" || state == "OK") {
                    Toast.makeText(SelFunActivity.this, state, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SelFunActivity.this, ShareActivity.class);
                    intent.putExtra("ImagePath", state);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(SelFunActivity.this, "服务器尚未处理完毕", Toast.LENGTH_SHORT).show();
            }


        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_color:
                //黑白照片变彩色功能
                try {
                    Control_send("color");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //resetPreState();
                Toast.makeText(SelFunActivity.this, "color", Toast.LENGTH_LONG).show();
                break;

            case R.id.txt_sketch:
                //漫画线稿上色功能
                try {
                    Control_send("sketch");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //resetPreState();
                Toast.makeText(SelFunActivity.this, "sketch", Toast.LENGTH_LONG).show();
                break;

            case R.id.txt_repair:
                //破损图片修复功能
                try {
                    Control_send("repair");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //resetPreState();
                Toast.makeText(SelFunActivity.this, "repair", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    public void SendImage(String fun) {


        iv_photo = (PhotoView) findViewById(R.id.iv_photo);

        String userId = "15301146";
        Bitmap bitmap = ((BitmapDrawable) ((ImageView) iv_photo).getDrawable()).getBitmap();


        //Looper.prepare();
        //根据返回的信息  执行不同更新操作
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                Log.i("Wait  - - ",  " check  ");
                switch (msg.what) {
                    case 1:
                        //bt.setText("正在下载...");
                        //textview.setText("123321321");
                        break;
                    case 2:
                        Bitmap bm = (Bitmap) msg.obj;
                        //iv.setImageBitmap(bm);

                        break;
                    case 3:
                        Bundle bundle = msg.getData();
                        String data = bundle.getString("result");
                        String str1 = "";
                        String str2 = "";

                        JSONObject json = JSONObject.parseObject(data);

                        str2 = json.getString("info");
                        str1 = json.getString("state");
                        Log.i("result  result ",  str1+"   "+str2);
                        Log.i("result  - - ", "  handle   handle  handle");
                        Toast.makeText(SelFunActivity.this, str1+"   "+str2, Toast.LENGTH_LONG).show();
                        if (str1 != null) {

                            if(str1 =="2"){
                                imagename = str2;
                                info = str1;
                            }else if(str1.equals("3")){
                                CS_state =0;
                                T_state = 0;

                                info = str1;
                                Bitmap bitmap = ((BitmapDrawable) ((ImageView) iv_photo).getDrawable()).getBitmap();
                                str = Common.convertIconToString(bitmap);

                                ImageView imageView = (PhotoView) findViewById(R.id.iv_photo);
                                imageView.setImageBitmap(Common.convertStringToIcon(str2));

                            }
                        }
                        break;
                }
            }
        };
        updateHeadImage(userId, bitmap, fun, handler);
    }

    public static void updateHeadImage(final String userId, final Bitmap image, final String fun, final Handler handler) {
        new Thread() {
            @Override
            public void run() {


                //Looper.prepare();

                //JSONObject jsonObject = new JSONObject();
                //jsonObject.put("userId", userId);
                //将Bitmap转成String
                //jsonObject.put("userImageContent", Common.Bitmap2String(image));

                RequestParams params = new RequestParams();
                try {
                    String url = null;
                    if(fun!="sketch"&&fun!="color"&&fun!="repair"){
                        params.put("pic_name", fun);
                        url = "http://192.168.142.139:8888/check_pic";
                    }else{
                        /*  47.94.146.51
                        params.put("username", userId);
                        params.put("userImageContent", Common.convertIconToString(image));
                        params.put("function", fun);
                        */

                        params.put("cli_name", userId);
                        params.put("pic", Common.convertIconToString(image));
                        params.put("fun", fun);

                        url = "http://192.168.142.139:8888/send_pic";

                    }

                    String content = String.valueOf(params);
                    Log.i("content  - - ", content);

                    //请求地址
                    //String url = "http://47.94.146.51:8888/send_pic";
                    String result = Common.sendPostRequest(url, content);//post请求服务器：数据content,请求路径url。
                    Log.i("result  - - ", result);

                    // 服务器返回结果
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    message.setData(bundle);
                    //message.what = R.id.textView;
                    message.what = 3;


                    String str1 = "";
                    String str2 = "";

                    JSONObject json = JSONObject.parseObject(result);
                    str1 = json.getString("state");
                    str2 = json.getString("info");

                    if(str1.equals("2")){
                        imagename = str2;
                        stop = "get";
                        Log.i("result  - - ", imagename+"22222 ");
                        info = str1;

                    }else if(str1.equals("3")) {
                        resetPreState();
                        stop = "get";
                        info = str1;
                        sleep(5000);
                        Log.i("result  - - ", str2 + "  33333333333333333 ");
                        handler.sendMessage(message);

                    }else if(str1.equals("1")){
                        stop = "get";
                        info = str1;
                        Log.i("result  - - ", str2 + "  1111111111111 ");
                    }else if(str1.equals("4")){
                        resetPreState();
                        stop = "get";
                        info = str1;
                        Log.i("result  - - ", str2 + "  44444444444 ");
                    }




                } catch (Exception e) {
                }
                //Looper.loop();
            }
        }.start();
    }
/*
    public TimerTask Ttask(final String ImageName) {


        try {
            TimerTask task = new TimerTask() {
                int count = 0;

                @Override
                public void run() {
                    count++;
                    Log.i("count  - - 定时器运行次数", "   ");
                    SendImage(ImageName);
                }
            };
            return task;

        } catch (Exception e) {

            TimerTask task = new TimerTask() {
                int count = 0;

                @Override
                public void run() {
                    count++;
                    Log.i("count  - - ",   "   图片名为空 --------");
                    SendImage("Transpic");
                }
            };

            return task;

        }
    }
*/
    public void Control_send(String fun) throws InterruptedException {

        if (CS_state == 0) {
            CS_state = 1;
            SendImage(fun);

            //sleep(40000);
            while (stop == "default"){
                sleep(3000);
                Log.i("Wait  - - ",  "WAIT   ");
                Log.i("result while  - - ", imagename);
            }
            stop = "default";

            Log.i("Wait  - - ",  CS_str+ "     "+info);

            CS_str =info;

            if(CS_str == "3"){
                Log.i("result over  - - ", imagename);
            }

            if (CS_str != null) {
                CS_state = 0;
            }



        } else if (CS_state == 1) {

            Log.i("result  CS_state == 1 ",  "-------");
           // Toast.makeText(SelFunActivity.this, "Waiting for the server return,Don't worry", Toast.LENGTH_LONG).show();
        }

        if (CS_state == 0 || CS_str != null) {

            switch (CS_str) {
                case "1":

                    break;
                case "2":
                    //CS_state = 1;

                    //timer.schedule(task, delay, period);
                    //Handler mhandler = new Handler();
                    mhandler.sendEmptyMessageDelayed(0, 4000);//启动handler，实现4秒定时循环执行

                    break;
                case "3":
                    /*
                    if(timer!=null){
                        timer.cancel();
                        timer=null;
                    }
                    */
                    break;
                case "4":

                    /*
                    if(timer!=null){
                        timer.cancel();
                        timer=null;
                    }
                    */
                    Log.i("result   - - ", "444444444444444444.");
                    break;
            }

        }

    }


    private Handler mhandler = new Handler(){

        @Override
        public void handleMessage(android.os.Message msg) {

            if(isChange == 0){
                //逻辑处理
                count++;
                if (T_state == 0) {
                    T_state = 1;

                    try {
                        Control_send(imagename);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else if (T_state == 1) {
                    Toast.makeText(SelFunActivity.this, "Waiting for the server return,Don't worry", Toast.LENGTH_LONG).show();
                }

                T_str = info;
                if (T_str != null) {
                    T_state = 0;
                }
                Log.i("MainActivity  ",count+"   - -- - ");
                if(!CS_str.equals("3")){
                    mhandler.sendEmptyMessageDelayed(0,4000);//4秒后再次执行
                }
            }

        }
    };
    /*
    TimerTask task = new TimerTask() {

        int count = 0;
        @Override
        public void run() {
            count++;
            //Looper.prepare();

            if (T_state == 0) {
                T_state = 1;

                try {
                    Control_send(imagename);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else if (T_state == 1) {
                Toast.makeText(SelFunActivity.this, "Waiting for the server return,Don't worry", Toast.LENGTH_LONG).show();
            }

            T_str = info;
            if (T_str != null) {
                T_state = 0;
            }
            //Toast.makeText(MainActivity.this, count, Toast.LENGTH_SHORT).show();
            Log.i("MainActivity  - - ", count + "    --------");
           //Looper.loop();
        }
    };

*/
}
