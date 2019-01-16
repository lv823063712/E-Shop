package soexample.bigfly.com.e_shop.activitypage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.bean.HeadPortraitData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImpl;
import soexample.bigfly.com.e_shop.utils.ProviderUtil;
import soexample.bigfly.com.e_shop.view.IView;

public class MyPersonalActivity extends BaseActivity implements IView {

    private RelativeLayout MyHeadImg;
    private RelativeLayout MyNickName;
    private RelativeLayout MyPassWord;
    private TextView my_map_depot;
    private TextView my_camera;
    private TextView my_head_cancle;
    private LinearLayout lin;
    private PopupWindow popupWindow;
    private ImageView my_head;
    private Uri imageUri;
    private File file;
    private TextView my_niCheng;
    private TextView my_pwd;
    private IPresenterImpl iPresenter;
    private String url = "http://172.17.8.100/small/user/verify/v1/modifyHeadPic";
    private HashMap<String, String> map = new HashMap<>();
    private SharedPreferences user;
    private Bitmap bitmap;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_personal;
    }

    protected void initView() {
        lin = findViewById(R.id.lin);
        MyHeadImg = (RelativeLayout) findViewById(R.id.MyHeadImg);
        MyNickName = (RelativeLayout) findViewById(R.id.MyNickName);
        MyPassWord = (RelativeLayout) findViewById(R.id.MyPassWord);
        my_head = findViewById(R.id.My_Head);
        my_niCheng = findViewById(R.id.My_NiCheng);
        my_pwd = findViewById(R.id.My_Pwd);


        user = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = user.getInt("userId", 0);
        String sessionId = user.getString("sessionId", "");
        String headPic = user.getString("headPic", "");
        Uri uri = Uri.parse(headPic);
        String nickName = user.getString("nickName", "");
        String pwd = user.getString("pwd", "");
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        my_head.setImageURI(uri);
        my_niCheng.setText(nickName);
        my_pwd.setText(pwd);

        iPresenter = new IPresenterImpl(this);

        iPresenter.startLogin(url, map, 6);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setOnClick() {
        MyHeadImg.setOnClickListener(this);
        MyNickName.setOnClickListener(this);
        MyPassWord.setOnClickListener(this);
    }

    @Override
    protected void proLogic() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MyHeadImg:
                View contentView = View.inflate(this, R.layout.pop, null);
                popupWindow = new PopupWindow(contentView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //设置背景
                popupWindow.setOutsideTouchable(true);

                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.head_pop_shape));
                //查找pop里面的控件
                my_map_depot = contentView.findViewById(R.id.My_map_depot);
                my_camera = contentView.findViewById(R.id.My_camera);
                my_head_cancle = contentView.findViewById(R.id.My_head_cancle);
                //pop的监听事件
                popListener();
                //设置pop出现的位置
                popupWindow.showAtLocation(lin, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.MyNickName:
                Intent intent = new Intent(MyPersonalActivity.this, AmendActivity.class);
                startActivity(intent);
                break;
            case R.id.MyPassWord:
                Intent intent1 = new Intent(MyPersonalActivity.this, AmendActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void popListener() {


        my_camera.setOnClickListener(new View.OnClickListener() {
            private Uri imageUri;
            private String fileName;

            @Override
            public void onClick(View v) {

                //隐式调取照相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //启动照相机
                startActivityForResult(intent, 101);
                //隐藏pop
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    fileName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    file = new File(Environment.
                            getExternalStorageDirectory().getAbsoluteFile() + "/image", fileName);
                    imageUri = Uri.fromFile(file);
                } else {
                    imageUri = FileProvider.getUriForFile(MyPersonalActivity.this, ProviderUtil.getFileProviderName(MyPersonalActivity.this), file);
                    Log.e("aaaaaa", imageUri + "");
                }
                intent.addCategory("android.intent.category.DEFAULT");
                // 设置图片的路径
                intent.putExtra(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, imageUri);


                popupWindow.dismiss();
            }
        });
        my_map_depot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);

                popupWindow.dismiss();
            }
        });
        my_head_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == MyPersonalActivity.RESULT_OK) {
            Uri uri = Uri.fromFile(file);
            crop(uri);
        } else if (requestCode == 100 && resultCode == MyPersonalActivity.RESULT_OK) {
            Uri uri = data.getData();
            crop(uri);
        }
        if (requestCode == 9090) {
            bitmap = data.getParcelableExtra("data");
            my_head.setImageBitmap(bitmap);
        }
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //
        intent.putExtra("crop", "true");

        // 裁剪框的比例1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        // 设定图片的格式
        intent.putExtra("outputFormat", "JPEG");
        // 取消人脸识别
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);

        // 开启一个带有返回值的activity,请求码为photo_request_cut
        startActivityForResult(intent, 9090);
    }

    @Override
    public void success(Object data) {
        HeadPortraitData headPortraitData = (HeadPortraitData) data;
        Log.e("测试", my_head.toString());
        user.getString("headPic", my_head.toString());

    }

    @Override
    public void error(Object error) {

    }
}
