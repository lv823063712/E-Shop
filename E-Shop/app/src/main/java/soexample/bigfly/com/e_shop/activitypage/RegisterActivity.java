package soexample.bigfly.com.e_shop.activitypage;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.XEditText;

import java.util.HashMap;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.bean.MyRegister;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImpl;
import soexample.bigfly.com.e_shop.view.IView;

public class RegisterActivity extends BaseActivity implements IView {

    private XEditText MyPhone;
    private XEditText MyPwd;
    private TextView MyLogin;
    private Button mRegister;
    private String mUrl = "http://172.17.8.100/small/user/v1/register";
    private IPresenterImpl iPresenter;
    private HashMap<String, String> map = new HashMap<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        MyPhone = findViewById(R.id.MyPhone);
        MyPwd = findViewById(R.id.MyPwd);
        MyLogin = findViewById(R.id.MyLogin);
        mRegister = findViewById(R.id.MyRegister);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setOnClick() {
        MyLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);

    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImpl(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MyLogin:
                finish();
                break;
            case R.id.MyRegister:
                String phone = MyPhone.getText().toString().trim();
                String pwd = MyPwd.getText().toString().trim();
                map.put("phone", phone);
                map.put("pwd", pwd);
                iPresenter.startLogin(mUrl, map, 2);
                break;
        }
    }

    @Override
    public void success(Object data) {
        MyRegister myRegister = (MyRegister) data;
        if (myRegister.getMessage().equals("注册成功")) {
            Toast.makeText(this, myRegister.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, myRegister.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void error(Object error) {

    }
}
