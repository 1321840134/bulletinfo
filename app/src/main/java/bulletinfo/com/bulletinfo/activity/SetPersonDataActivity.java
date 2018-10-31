package bulletinfo.com.bulletinfo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import bulletinfo.com.bulletinfo.R;
import bulletinfo.com.bulletinfo.service.ServerSocketClient;
import bulletinfo.com.bulletinfo.util.Constant;
import bulletinfo.com.bulletinfo.util.PhotoUtils;
import bulletinfo.com.bulletinfo.util.PicturePickerDialog;
import bulletinfo.com.bulletinfo.util.SharePreUtil;
import bulletinfo.com.bulletinfo.util.ToastUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SetPersonDataActivity extends AppCompatActivity implements View.OnClickListener {

    private String phone;
    private String imagePath;
    private CircleImageView photo;
    private RelativeLayout selectHd;
    private EditText name;
    private Button complete;
    private Context context;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

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
        setContentView(R.layout.activity_set_person_data);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        context = this;
        initView();
    }

    private void initView() {
        photo = (CircleImageView) findViewById(R.id.photo);
        selectHd = (RelativeLayout) findViewById(R.id.selectHd);
        name = (EditText) findViewById(R.id.name);
        complete = (Button) findViewById(R.id.complete);
        selectHd.setOnClickListener(this);
        complete.setOnClickListener(this);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt = name.getText().toString();
                if (txt.isEmpty()||txt.length()<1){
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
            case R.id.selectHd:
                showPicturePicker();
                break;

            case R.id.complete:
                upload();
                break;
        }
    }

    public void showPicturePicker(){
        PicturePickerDialog picturePickerDialog = new PicturePickerDialog(context);
        picturePickerDialog.show(new PicturePickerDialog.PicturePickerCallBack() {
            @Override
            public void onPhotoClick() {
                autoObtainCameraPermission();
            }

            @Override
            public void onAlbumClick() {
                autoObtainStoragePermission();
            }
        });
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SetPersonDataActivity.this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(context, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(SetPersonDataActivity.this,new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(context, "com.zz.fileprovider", fileUri);
                }
                PhotoUtils.takePicture(SetPersonDataActivity.this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(context, "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(SetPersonDataActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(SetPersonDataActivity.this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(SetPersonDataActivity.this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(SetPersonDataActivity.this, "请允许打开相机！！");
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(SetPersonDataActivity.this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(SetPersonDataActivity.this, "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(SetPersonDataActivity.this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);

                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(SetPersonDataActivity.this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(SetPersonDataActivity.this, "com.zz.fileprovider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(SetPersonDataActivity.this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtils.showShort(SetPersonDataActivity.this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, SetPersonDataActivity.this);
                    if (bitmap != null) {
                        photo.setImageBitmap(bitmap);
                        String filePath = cropImageUri.getEncodedPath();
                        imagePath = Uri.decode(filePath);
                    }
                    break;
                default:
            }
        }
    }

    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(SetPersonDataActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SetPersonDataActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(SetPersonDataActivity.this, CODE_GALLERY_REQUEST);
        }

    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    private void upload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (imagePath!=null){
                    String userName = name.getText().toString();
                    String url = Constant.URL+"/updateIcourl";
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file",
                                    imagePath,RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath)))
                            .addFormDataPart("phone",phone)
                            .addFormDataPart("userName",userName)
                            .build();
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
                            mHandler.sendMessage(message);//使用Message传递消息给线程

                        }else{
                            Message message=new Message();
                            message.what=500;
                            message.obj="服务器异常";
                            mHandler.sendMessage(message);//使用Message传递消息给线程
                        }
                    } catch (IOException e) {
                        Message message=new Message();
                        message.what=500;
                        message.obj="服务器异常";
                        mHandler.sendMessage(message);//使用Message传递消息给线程
                    }
                }else {
                    String username = name.getText().toString();
                    String url = Constant.URL+"/updateUserName/"+username+"/"+phone;
                    RequestBody requestBody = RequestBody.create(null,"");
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()){
                            //打印服务端返回结果
                            Message message=new Message();
                            message.what=200;
                            message.obj=response.body().string();
                            mHandler.sendMessage(message);//使用Message传递消息给线程
                        }else {
                            Message message=new Message();
                            message.what=500;
                            message.obj="服务器异常";
                            mHandler.sendMessage(message);//使用Message传递消息给线程
                        }
                    }catch (Exception e){
                        Message message=new Message();
                        message.what=500;
                        message.obj="服务器异常";
                        mHandler.sendMessage(message);//使用Message传递消息给线程
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void dispatchMessage(Message msg){
            switch (msg.what){
                case 200:
                    String result =  msg.obj.toString();
                    try{
                        JSONObject object = new JSONObject(result);
                        if(object.getString("code").equals("200")){
                            selectPhone();
                        }else {
                            ToastUtils.showShort(context,"失败");
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    break;

                case 500:
                    Log.e("ERROR=====","服务器异常！");
                    ToastUtils.showShort(context,"服务器异常");
                    break;

                default:
                    break;
            }
        }
    };

    private void selectPhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Constant.URL+"/selectPhone/"+phone;
                RequestBody requestBody = RequestBody.create(null,"");
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        //打印服务端返回结果
                        Message message=new Message();
                        message.what=200;
                        message.obj=response.body().string();
                        handler.sendMessage(message);//使用Message传递消息给线程
                    }else {
                        Message message=new Message();
                        message.what=500;
                        message.obj="服务器异常";
                        handler.sendMessage(message);//使用Message传递消息给线程
                    }
                }catch (Exception e){
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
        public void dispatchMessage(Message msg){
            switch (msg.what){
                case 200:
                    String result =  msg.obj.toString();
                    try{
                        JSONObject object = new JSONObject(result);
                        if(object.getString("code").equals("200")){
                            if (!object.isNull("data")){
                                JSONObject jsonObject = object.getJSONObject("data");
                                final String uid = jsonObject.getString("uid");
                                //保存登录信息
                                SharePreUtil.setParam(context,"Login",true);
                                //保存用户
                                SharePreUtil.setParam(context,"User",uid);
                                //连接socket服务器
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ServerSocketClient.main(uid);
                                    }
                                }).start();
                                Intent intent = new Intent(context,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                            ToastUtils.showShort(context,"登录失败");
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    break;

                case 500:
                    Log.e("ERROR=====","服务器异常！");
                    ToastUtils.showShort(context,"服务器异常");
                    break;

                default:
                    break;
            }
        }
    };
}
