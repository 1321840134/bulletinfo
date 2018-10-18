package bulletinfo.com.bulletinfo.util;


import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by foxcold on 2018/9/26.
 */

public class HttpUtils {
    //单利模式
    private static HttpUtils htp = null;
    private HttpUtils(){};
    private HttpUtils getInstance(){
        if (htp == null){
            synchronized (HttpUtils.class){
                if (htp == null){
                    htp = new HttpUtils();
                }
            }
        }
        return htp;
    }

    public String OpenTryPost(String url,JSONObject obj){
        String str = "";
        try {
            URL httpurl = new URL(url);
            HttpURLConnection connection =(HttpURLConnection) httpurl.openConnection();
            //set date
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setInstanceFollowRedirects(true);
            //start connect
            connection.connect();
            //写入
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            String json = JSONObject.toJSONString(obj);
            out.writeChars(json);
            out.flush();
            out.close();
            //响应
            BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String data = null;
            while ((data = buf.readLine()) != null){
                str += data;
            }
            buf.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  str;
    }


}
