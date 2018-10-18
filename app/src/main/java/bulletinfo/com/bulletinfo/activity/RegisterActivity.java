package bulletinfo.com.bulletinfo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bulletinfo.com.bulletinfo.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back,sendCode;
    private EditText phone,yzm;
    private TextView agreement;
    /*手机号码验证*/
    private String regx = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    public void initView(){
        back = (Button) findViewById(R.id.back);
        sendCode = (Button) findViewById(R.id.sendCode);
        phone = (EditText) findViewById(R.id.phone);
        yzm = (EditText) findViewById(R.id.yzm);
        agreement = (TextView) findViewById(R.id.agreement);
        back.setOnClickListener(this);
        sendCode.setOnClickListener(this);
        agreement.setOnClickListener(this);
        /*输入监听*/
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                String info = phone.getText().toString().trim();
                if (info.isEmpty()||!checkString(info)){
                    sendCode.setEnabled(false);
                    sendCode.setBackgroundResource(R.drawable.sendcode_hide);
                }else {
                    sendCode.setEnabled(true);
                    sendCode.setBackgroundResource(R.drawable.sendcode_show);
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
        }
    }

    //检查手机号格式
    private boolean checkString(String s) {
        return s.matches(regx);
    }
}
