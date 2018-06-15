package com.example.lenovo.transpic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by lnnn on 2018/6/15.
 */

public class Progress extends Activity {
    public final static int TASK_LOOP_COMPLETE = 0;
    ProgressDialog progressDialog;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_fun);
        progressDialog = ProgressDialog.show(this, "提示", "正在加载中，请稍等...", true, true);

        new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally{
                    messageListener.sendEmptyMessage(TASK_LOOP_COMPLETE);
                }
            }
        }.start();
    }

    //进度监听
    private Handler messageListener = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.arg1){
                case TASK_LOOP_COMPLETE:
                    progressDialog.dismiss();
                    break;

            }
        }
    };

}
