package bulletinfo.com.bulletinfo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.util.ToastUtils;
import okhttp3.OkHttpClient;

public class LoginByPwActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText account,password;
    private Button back,login;
    /*判断账号*/
    private boolean isHide = false;
    /*判断密码*/
    private boolean isShow = false;

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
        setContentView(R.layout.activity_login_by_pw);
        initView();
    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        login = (Button) findViewById(R.id.loginin);
        account = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.pw);
        back.setOnClickListener(this);
        login.setOnClickListener(this);
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = account.getText().toString();
               if (phone.length() == 0){
                   isHide = true;
                   login.setEnabled(false);
                   login.setBackgroundResource(R.drawable.sendcode_hide);
               }else {
                   isHide = false;
                   if (isShow == false){
                       login.setEnabled(false);
                       login.setBackgroundResource(R.drawable.sendcode_hide);
                   }else{
                       login.setEnabled(true);
                       login.setBackgroundResource(R.drawable.sendcode_show);
                   }
               }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pw = password.getText().toString();
                Log.e("sdsfds",String.valueOf(isHide));
                if (pw.isEmpty()||pw.length()<6){
                    isShow = false;
                    login.setEnabled(false);
                    login.setBackgroundResource(R.drawable.sendcode_hide);
                }else {
                    isShow = true;
                    if (isHide == true){
                        login.setEnabled(false);
                        login.setBackgroundResource(R.drawable.sendcode_hide);
                    }else{
                        login.setEnabled(true);
                        login.setBackgroundResource(R.drawable.sendcode_show);
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
        switch (view.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.loginin:
                CheckLogin();
                break;
        }
    }

    public void CheckLogin(){

    }
}
