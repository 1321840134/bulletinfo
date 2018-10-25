package bulletinfo.com.bulletinfo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.util.SharePreUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register,login;
    private List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s = SharePreUtil.getParam(LoginActivity.this,"Login",false).toString();
        if (s.equals("true")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
            list.add(Manifest.permission.READ_CALL_LOG);
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            list.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.WRITE_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            list.add(Manifest.permission.WRITE_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.GET_ACCOUNTS)!=PackageManager.PERMISSION_GRANTED){
            list.add(Manifest.permission.GET_ACCOUNTS);
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            list.add(Manifest.permission.READ_CONTACTS);
        }
        if (!list.isEmpty()){
            String[] permission = list.toArray(new String[list.size()]);
            ActivityCompat.requestPermissions(LoginActivity.this,permission,1002);
        }else {
            initView();
        }

    }

    private void initView(){
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
                break;

            case R.id.login:
                startActivity(new Intent(LoginActivity.this,LoginInfoActivity.class));
                finish();
                break;
        }
    }
}
