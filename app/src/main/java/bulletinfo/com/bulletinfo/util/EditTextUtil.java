package bulletinfo.com.bulletinfo.util;

import android.content.Context;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import bulletinfo.com.bulletinfo.R;

public class EditTextUtil {
    //查看密码
    public static void pwdShow(Context context, EditText editText, ImageView imageView){

        int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        if(editText.getInputType() == type){//密码可见
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.yanjing2));
            editText.setSelection(editText.getText().length());     //把光标设置到当前文本末尾

        }else{
            editText.setInputType(type);
            imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.yanjing1));
            editText.setSelection(editText.getText().length());
        }

    }
}
