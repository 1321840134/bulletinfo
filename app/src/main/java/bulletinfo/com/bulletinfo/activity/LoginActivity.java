package bulletinfo.com.bulletinfo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bulletinfo.com.bulletinfo.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
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
                break;

            case R.id.login:
                startActivity(new Intent(LoginActivity.this,LoginInfoActivity.class));
                break;
        }
    }
}
