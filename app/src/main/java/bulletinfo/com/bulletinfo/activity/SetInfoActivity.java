package bulletinfo.com.bulletinfo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.util.SharePreUtil;

public class SetInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back,loginout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_info);
        initView();
    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        loginout = (Button) findViewById(R.id.loginout);
        back.setOnClickListener(this);
        loginout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.loginout:
                AlertDialog.Builder build = new AlertDialog.Builder(SetInfoActivity.this);
                build.setTitle("账号设置")
                        .setMessage("退出后将返回登录界面,一定要退出吗？")
                        .setNegativeButton("继续退出", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                SharePreUtil.setParam(SetInfoActivity.this,"Login",false);
                                //清除用户
                                SharePreUtil.removeParam(SetInfoActivity.this,"User");
                                startActivity(new Intent(SetInfoActivity.this,LoginActivity.class));
                                finish();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        })
                        .show();
                break;
        }
    }
}
