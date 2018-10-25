package bulletinfo.com.bulletinfo.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.util.Constant;
import bulletinfo.com.bulletinfo.util.EditTextUtil;
import okhttp3.OkHttpClient;

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
        String url = Constant.URL+"";
    }

    private void registerPhone() {

    }

    //检查密码格式
    private boolean checkString(String s) {
        return s.matches(myerg);
    }
}
