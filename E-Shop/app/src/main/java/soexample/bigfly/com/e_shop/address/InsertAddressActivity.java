package soexample.bigfly.com.e_shop.address;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPickerView;

import java.util.HashMap;

import soexample.bigfly.com.e_shop.MainActivity;
import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.bean.InsertAddressData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImpl;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplPostPut;
import soexample.bigfly.com.e_shop.view.IView;

public class InsertAddressActivity extends BaseActivity implements View.OnClickListener, IView {

    private EditText add_person;
    private EditText add_phone;
    private TextView add_local;
    private ImageView add_image;
    private EditText add_content;
    private TextView add_email;
    private Button add_save;
    private String url = "http://172.17.8.100/small/user/verify/v1/addReceiveAddress";
    private IPresenterImplPostPut iPresenter;
    private HashMap<String, String> map = new HashMap<>();
    private SharedPreferences shared;
    private int userId;
    private String sessionId;

    @Override
    protected int getLayout() {
        return R.layout.activity_insert_address;
    }

    protected void initView() {
        add_person = (EditText) findViewById(R.id.add_person);
        add_phone = (EditText) findViewById(R.id.add_phone);
        add_local = (TextView) findViewById(R.id.add_local);
        add_image = (ImageView) findViewById(R.id.add_image);
        add_content = (EditText) findViewById(R.id.add_content);
        add_email = (TextView) findViewById(R.id.add_email);
        add_save = (Button) findViewById(R.id.add_save);
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setOnClick() {
        add_image.setOnClickListener(this);
        add_save.setOnClickListener(this);
    }

    @Override
    protected void proLogic() {
        iPresenter = new IPresenterImplPostPut(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_save:
                String realName = add_person.getText().toString();
                String phone = add_phone.getText().toString();
                String local = add_local.getText().toString();
                String address = add_content.getText().toString();
                String zipCode = add_email.getText().toString();
                map.put("userId", userId + "");
                map.put("sessionId", sessionId);
                map.put("realName", realName);
                map.put("phone", phone);
                map.put("address", local + " " + address);
                map.put("zipCode", zipCode);
                iPresenter.startLogin(url, map, 5);
                Intent intent = new Intent(InsertAddressActivity.this, AddressActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.add_image:
                CityPickerView cityPickerView = new CityPickerView(InsertAddressActivity.this);
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县
                        String district = citySelected[2];
                        //邮编
                        String code = citySelected[3];
                        add_local.setText(province + " " + city + " " + district);
                    }
                });
                cityPickerView.show();
                break;
        }
    }

    @Override
    public void success(Object data) {
        InsertAddressData insertAddressData = (InsertAddressData) data;
        Toast.makeText(this, insertAddressData.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(Object error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDatacth();
    }
}
