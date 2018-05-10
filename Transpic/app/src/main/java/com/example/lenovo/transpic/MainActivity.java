package com.example.lenovo.transpic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ImgBtnAlbum;
    private ImageButton ImgBtnCamera;
    private int funs = 0;

    private static int RESULT_LOAD_IMAGE = 1;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 1;

    /*    private ImageView iv_recycle;
    AnimationDrawable ivRecycleAnimation;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImgBtnAlbum = (ImageButton) findViewById(R.id.img_btn_album);
        ImgBtnCamera = (ImageButton) findViewById(R.id.img_btn_camera);

        ImgBtnAlbum.setOnClickListener(this);
        ImgBtnCamera.setOnClickListener(this);

        /*iv_recycle = (ImageView)findViewById(R.id.iv_recycle);
        iv_recycle.setBackgroundResource(R.drawable.img_anim);
        ivRecycleAnimation = (AnimationDrawable)iv_recycle.getBackground();
        ivRecycleAnimation.start();*/

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("申请相机权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        } else {
            //Toast.makeText(this, "相机权限-GET", Toast.LENGTH_SHORT).show();
        }

        Handler handler = new Handler() {
        };
        checkPermission(handler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_album:
                //选择相册
                funs = 1;
                Intent album = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(album, RESULT_LOAD_IMAGE);
                break;

            case R.id.img_btn_camera:
                //相机拍照
                funs = 2;
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, RESULT_LOAD_IMAGE);
                break;

            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (funs == 1) {
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

                //  传送图片路径至SelFunActivity
                Intent i = new Intent(MainActivity.this, SelFunActivity.class);
                i.putExtra("ImagePath", picturePath);
                startActivity(i);

                //ImageView imageView = (ImageView) findViewById(R.id.CH_imageView);
                //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                //Toast.makeText(this, picturePath, Toast.LENGTH_SHORT).show();
            }
        } else if (funs == 2) {
            int i = 0;
            if (resultCode == Activity.RESULT_OK) {

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

                    //  传送图片路径至SelFunActivity
                    Intent j = new Intent(MainActivity.this, SelFunActivity.class);
                    j.putExtra("ImagePath", fileName);
                    startActivity(j);

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
        }
    }

    public void checkPermission(final Handler handler) {
        new Thread() {
            @Override
            public void run() {

                try {
                    wait(2000);
                    //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission
                                .WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(MainActivity.this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
                        }
                        //申请权限
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

                    } else {
                        //Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                        //Log.e(TAG_SERVICE, "checkPermission: 已经授权！");
                    }
                } catch (Exception e) {
                }
            }
        }.start();
    }
}
