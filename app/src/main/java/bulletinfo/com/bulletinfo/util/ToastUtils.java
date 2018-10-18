package bulletinfo.com.bulletinfo.util;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by 13218 on 2018/5/19.
 */

public class ToastUtils
{

    private ViewGroup.LayoutParams layoutParams;

    private ToastUtils()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;
    private static Toast toast = null;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message)
    {
        if (isShow){
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
            }
            toast.show();
        }

    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message)
    {
        if (isShow){
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
            }
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message)
    {
        if (isShow){
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            } else {
                toast.setText(message);
            }
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message)
    {
        if (isShow) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            } else {
                toast.setText(message);
            }
            toast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration)
    {
        if (isShow){
            if (toast == null) {
                toast = Toast.makeText(context, message, duration);
            } else {
                toast.setText(message);
            }
            toast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration)
    {
        if (isShow){
            if (toast == null) {
                toast = Toast.makeText(context, message, duration);
            } else {
                toast.setText(message);
            }
            toast.show();
        }
    }

}