package bulletinfo.com.bulletinfo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.util.Constant;
import bulletinfo.com.bulletinfo.util.EditTextUtil;
import bulletinfo.com.bulletinfo.util.SharePreUtil;
import bulletinfo.com.bulletinfo.util.ToastUtils;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button skip,complete;
    private EditText pw;
    private ImageView imageView;
    private Context context;
    private String phone;
    //验证密码的正则表达式
    String myerg = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,16}$";

    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        Intent intent = getIntent();
        phone = intent.getStringExtra("Phone");
        context = this;
        initView();
    }

    private void initView() {
        skip = (Button) findViewById(R.id.skip);
        complete = (Button) findViewById(R.id.complete);
        pw = (EditText) findViewById(R.id.pw);
        imageView = (ImageView)findViewById(R.id.pw_see);
        skip.setOnClickListener(this);
        complete.setOnClickListener(this);
        imageView.setOnClickListener(this);
        pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = pw.getText().toString();
                if (password.isEmpty()||!checkString(password)){
                    complete.setEnabled(false);
                    complete.setBackgroundResource(R.drawable.sendcode_hide);
                }else {
                    complete.setEnabled(true);
                    complete.setBackgroundResource(R.drawable.sendcode_show);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.skip:
                registerPhone();
                break;

            case R.id.complete:
                registerPw();
                break;

            case R.id.pw_see:
                EditTextUtil.pwdShow(context,pw,imageView);
                break;
        }
    }

    private void registerPw() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String password = pw.getText().toString();
                String url = Constant.URL+"/reginster";
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("phone",phone)
                        .addFormDataPart("password",password)
                        .build();
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
                            Intent intent = new Intent(context,SetPersonDataActivity.class);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtils.showShort(context,"注册失败");
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

    private void registerPhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Constant.URL+"/reginster";
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("phone",phone)
                        .build();
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

    //检查密码格式
    private boolean checkString(String s) {
        return s.matches(myerg);
    }
}
