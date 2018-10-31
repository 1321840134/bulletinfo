package bulletinfo.com.bulletinfo.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.activity.MainActivity;
import bulletinfo.com.bulletinfo.activity.SetInfoActivity;
import bulletinfo.com.bulletinfo.service.ServerSocketClient;
import bulletinfo.com.bulletinfo.util.Constant;
import bulletinfo.com.bulletinfo.util.SharePreUtil;
import bulletinfo.com.bulletinfo.util.ToastUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonalFragment extends Fragment implements View.OnClickListener {

    private CircleImageView photo;
    private TextView name;
    private RelativeLayout modifyInfo,security,setinfo;
    private String id;

    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable(){

        @Override
        public void run() {
            // TODO Auto-generated method stub

            id = SharePreUtil.getParam(getActivity(),"User","").toString();
            getInfo();
            //每两秒重启一下线程
            mHandler.postDelayed(mRunnable, 2000);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal_fragment,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getInfo();
    }

    private void getInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Constant.URL+"/selectUid/"+id;
                RequestBody requestBody = RequestBody.create(null, "");
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
                        handler.sendMessage(message);//使用Message传递消息给线程

                    }else{
                        Message message=new Message();
                        message.what=500;
                        message.obj="服务器异常";
                        handler.sendMessage(message);//使用Message传递消息给线程
                    }
                } catch (IOException e) {
                    Message message=new Message();
                    message.what=500;
                    message.obj="服务器异常";
                    handler.sendMessage(message);//使用Message传递消息给线程
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
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
                            if (!object.isNull("data")){
                                JSONObject jsonObject = object.getJSONObject("data");
                                String userName = jsonObject.getString("username");
                                String imgpath = jsonObject.getString("icourl");
                                name.setText(userName);
                                Picasso
                                        .with(getActivity())
                                        .load(Constant.URL + imgpath)//地址
                                        .placeholder(R.mipmap.ic_launcher_round)//过度图片
                                        .error(R.mipmap.ic_launcher_round)
                                        .into(photo);
                            }
                        }else {
                            ToastUtils.showShort(getActivity(),"错误");
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    break;

                case 500:
                    Log.e("ERROR=====","服务器异常！");
                    ToastUtils.showShort(getActivity(),"服务器异常");
                    break;

                default:
                    break;
            }
        }
    };

    private void initView() {
        photo = (CircleImageView) getActivity().findViewById(R.id.photo);
        name = (TextView) getActivity().findViewById(R.id.name);
        modifyInfo = (RelativeLayout) getActivity().findViewById(R.id.modifyInfo);
        security = (RelativeLayout) getActivity().findViewById(R.id.security);
        setinfo = (RelativeLayout) getActivity().findViewById(R.id.setinfo);
        modifyInfo.setOnClickListener(this);
        security.setOnClickListener(this);
        setinfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.modifyInfo:
                break;

            case R.id.security:
                break;

            case R.id.setinfo:
                startActivity(new Intent(getActivity(), SetInfoActivity.class));
                break;
        }
    }
}
