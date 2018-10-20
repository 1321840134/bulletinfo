package bulletinfo.com.bulletinfo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.util.Constant;
import bulletinfo.com.bulletinfo.util.ToastUtils;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResetPwActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SmsYanzheng";
    private Button back,sendCode;
    private EditText phone,yzm;
    /*判断验证码*/
    private boolean isShow = false;
    /*判断手机*/
    private boolean isHide = false;
    /*手机号码验证*/
    private String regx = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    private Context context;
    private EventHandler eventHandler;
    private String strPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);
        context = this;
        initView();
    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        sendCode = (Button) findViewById(R.id.sendCode);
        phone = (EditText) findViewById(R.id.phone);
        yzm = (EditText) findViewById(R.id.yzm);

        back.setOnClickListener(this);
        sendCode.setOnClickListener(this);

        eventHandler = new EventHandler() {

            /**
             * 在操作之后被触发
             *
             * @param event  参数1
             * @param result 参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
             * @param data   事件操作的结果
             */

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = myHandler.obtainMessage(0x00);
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                myHandler.sendMessage(message);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);

        /*手机号输入监听*/
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                String info = phone.getText().toString().trim();
                if (info.isEmpty() || !checkString(info)) {
                    sendCode.setEnabled(false);
                    sendCode.setBackgroundResource(R.drawable.sendcode_hide);
                    isHide = true;
                } else {
                    sendCode.setEnabled(true);
                    sendCode.setBackgroundResource(R.drawable.sendcode_show);
                    isHide = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        /*验证码输入监听*/
        yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                String code = yzm.getText().toString().trim();
                if (code.isEmpty() || code.length() != 6) {
                    sendCode.setText("重新发送手机验证码");
                    isShow = false;
                } else {
                    sendCode.setText("下一步");
                    isShow = true;
                    if (!isHide) {
                        sendCode.setClickable(true);
                        sendCode.setBackgroundResource(R.drawable.sendcode_show);
                    } else {
                        sendCode.setClickable(false);
                        sendCode.setBackgroundResource(R.drawable.sendcode_hide);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.sendCode:
                String text = sendCode.getText().toString();
                if (text.equals("下一步")) {
                    SMSSDK.submitVerificationCode("86", strPhoneNumber, yzm.getText().toString());
                } else {
                    checkUser();
                }
                break;
        }
    }

    /**
     * 检查账号是否存在
     */
    private void checkUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String info = phone.getText().toString().trim();
                String url = Constant.URL+"/login/"+info;
                RequestBody requestBody = RequestBody.create(null, "");
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                //发送请求获取响应
                try {
                    Response response=client.newCall(request).execute();
                    //判断请求是否成功
                    if(response.isSuccessful()){
                        //打印服务端返回结果
                        Message message=new Message();
                        message.what=200;
                        message.obj=response.body().string();
                        mHandler.sendMessage(message);//使用Message传递消息给线程

                    }else{
                        Message message=new Message();
                        message.what=500;
                        message.obj="服务器异常";
                        mHandler.sendMessage(message);//使用Message传递消息给线程
                    }
                } catch (IOException e) {
                    Message message=new Message();
                    message.what=500;
                    message.obj="服务器异常";
                    mHandler.sendMessage(message);//使用Message传递消息给线程
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 200:
                    String result =  msg.obj.toString();
                    Log.i("Result========",result);
                    try{
                        JSONObject object = new JSONObject(result);
                        if(object.getString("code").equals("200")){
                            strPhoneNumber = phone.getText().toString().trim();
                            SMSSDK.getVerificationCode("86", strPhoneNumber);
                            sendCode.setClickable(false);
                            sendCode.setBackgroundResource(R.drawable.sendcode_hide);
                            //开启线程去更新button的text
                            new Thread() {
                                @Override
                                public void run() {
                                    int totalTime = 60;
                                    for (int i = 0; i < totalTime; i++) {
                                        Message message = myHandler.obtainMessage(0x01);
                                        message.arg1 = totalTime - i;
                                        myHandler.sendMessage(message);
                                        try {
                                            sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    myHandler.sendEmptyMessage(0x02);
                                }
                            }.start();
                        }else{
                            ToastUtils.showShort(context,"此号码未注册");
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    break;

                case 500:
                    Log.e("ERROR=====","服务器异常！");
                    ToastUtils.showShort(context,"服务器异常");
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e(TAG, "result : " + result + ", event: " + event + ", data : " + data);
                    if (result == SMSSDK.RESULT_COMPLETE) { //回调  当返回的结果是complete
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码
                            ToastUtils.showShort(context,"发送验证码成功");
                            Log.d(TAG, "get verification code successful.");
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码
                            Log.d(TAG, "submit code successful");

                            Intent intent = new Intent(context, SetPwActivity.class);
                            intent.putExtra("Phone",strPhoneNumber);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.d(TAG, data.toString());
                        }
                    } else { //进行操作出错，通过下面的信息区分析错误原因
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            //错误代码：  http://wiki.mob.com/android-api-%E9%94%99%E8%AF%AF%E7%A0%81%E5%8F%82%E8%80%83/
                            Log.e(TAG, "status: " + status + ", detail: " + des);
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                ToastUtils.showShort(context,des);
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0x01:
                    if (!isShow){
                        sendCode.setText("重新发送手机验证码(" + msg.arg1 + ")");
                        sendCode.setBackgroundResource(R.drawable.sendcode_hide);
                    }
                    break;
                case 0x02:
                    sendCode.setText("重新发送手机验证码");
                    sendCode.setClickable(true);
                    sendCode.setBackgroundResource(R.drawable.sendcode_show);
                    break;
            }
        }
    };

    //检查手机号格式
    private boolean checkString(String s) {
        return s.matches(regx);
    }
}
