package com.example.lenovo.transpic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.bitmap;

/**
 * Created by lenovo on 2018/4/18.
 * 工具类
 */

public class Common {


    /**
     * 图片转成string *  * @param bitmap * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        String str = Base64.encodeToString(appicon, Base64.DEFAULT);
        return str;
    }


    /**
     * string转成bitmap *  * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }


    // replace(oldChar, newChar)方法	参数1:要被替换的字符,参数2:替换进去的字符
    public static String AreplaceB(String str1) {
        String str = null;
        //str = str1;
        str = str1.replace(" ", "+");

        return str;
    }


    /**
     * 图像转字节
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 图像转String
     *
     * @param bitmap
     * @return
     */
    public static String Bitmap2String(Bitmap bitmap) {
        return Base64.encodeToString(Bitmap2Bytes(bitmap), Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap String2Bitmap(String st) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            Log.v("-   -",
                    "bitmap   bitmapbitmapbitmapbitmap    bitmap" + bitmap);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 2      * 发送post请求，参数单独发送到服务器端
     * 3
     */


    public static String sendPostRequest(String url, String params) {
        StringBuilder result = new StringBuilder();
        String realUrl = url;
        InputStream in = null;
        BufferedReader br = null;
        try {
            // 与服务器建立连接
            URL u = new URL(realUrl);
            URLConnection conn = u.openConnection();

            //conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "keep-alive");

            // post请求必须设置请求头
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.connect();


            // 发送参数到服务器
            OutputStream out = conn.getOutputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,
                    "utf-8"));
            pw.print(params);

            pw.flush();
            pw.close();


            // 获取响应头
            Map<String, List<String>> map = conn.getHeaderFields();
            Set<Map.Entry<String, List<String>>> entry = map.entrySet();
            Iterator<Map.Entry<String, List<String>>> it = entry.iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<String>> en = it.next();
                System.out.println(en.getKey() + ":::" + en.getValue());
            }

            // 获取响应数据
            in = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


    public static String SaveImage(Bitmap bit) {

        if (bit != null) {
            //Bitmap bitmap = ((BitmapDrawable) ((ImageView) image).getDrawable()).getBitmap(); // 获取Bitmap
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
                bit.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                //Toast.makeText(this, "图片保存成功:(DCIM/TranspicImage/" + str + ".jpg)", Toast.LENGTH_SHORT).show();

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
            return fileName;
        } else {
            return "Error";
        }
    }


}






