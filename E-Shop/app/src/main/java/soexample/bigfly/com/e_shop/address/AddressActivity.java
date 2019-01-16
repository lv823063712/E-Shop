package soexample.bigfly.com.e_shop.address;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.AddressAdapter;
import soexample.bigfly.com.e_shop.adapter.MyAddressAdapter;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.bean.AddressShowData;
import soexample.bigfly.com.e_shop.bean.DefaultAddressData;
import soexample.bigfly.com.e_shop.bean.NewAddress;
import soexample.bigfly.com.e_shop.ipresenter.IPresenter;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGet;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest2;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplPostPut;
import soexample.bigfly.com.e_shop.view.IView;
import soexample.bigfly.com.e_shop.view.IView2;
import soexample.bigfly.com.e_shop.view.IView3;

public class AddressActivity extends BaseActivity implements View.OnClickListener, IView, IView3, IView2 {

    private TextView mMyAddressComplete;
    private RecyclerView mMyAddressRecy;
    private Button mAddAddress;
    private String addressUrl = "http://172.17.8.100/small/user/verify/v1/receiveAddressList";
    private String setDefaultUrl = "http://172.17.8.100/small/user/verify/v1/setDefaultReceiveAddress";
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private IPresenterImplGetRequest iPresenterImplGetRequest;
    private HashMap<String, String> map = new HashMap<>();
    private ArrayList<AddressShowData.ResultBean> datas = new ArrayList();
    private AddressAdapter addressAdapter;
    private ArrayList<NewAddress> lists = new ArrayList();
    private MyAddressAdapter myAddressAdapter;
    private IPresenterImplPostPut iPresenterImplPostPut;
    private IPresenterImplGetRequest2 iPresenterImplGetRequest2;

    @Override
    protected int getLayout() {
        return R.layout.activity_address;
    }

    protected void initView() {
        mMyAddressComplete = (TextView) findViewById(R.id.My_Address_Complete);
        mMyAddressRecy = (RecyclerView) findViewById(R.id.My_Address_Recy);
        mAddAddress = (Button) findViewById(R.id.Add_Address);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mMyAddressRecy.setLayoutManager(manager);


    }

    @Override
    protected void initData() {
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);

    }

    @Override
    protected void setOnClick() {
        mMyAddressComplete.setOnClickListener(this);
        mAddAddress.setOnClickListener(this);
    }

    @Override
    protected void proLogic() {
        iPresenterImplGetRequest = new IPresenterImplGetRequest(this);
        iPresenterImplPostPut = new IPresenterImplPostPut(this);
        iPresenterImplGetRequest2 = new IPresenterImplGetRequest2(this);
        iPresenterImplGetRequest.startLogin(addressUrl, map, 9);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Address_Complete:
                finish();
                break;
            case R.id.Add_Address:
                Intent intent = new Intent(AddressActivity.this, InsertAddressActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void success3(Object data) {
        NewAddress newAddress = new NewAddress();
        lists.add(newAddress);
        if (lists == null) {
            myAddressAdapter = new MyAddressAdapter(lists, this);
            mMyAddressRecy.setAdapter(myAddressAdapter);
            myAddressAdapter.notifyDataSetChanged();
        } else {
            iPresenterImplGetRequest2.startLogin(addressUrl, map, 1);
        }
    }

    @Override
    public void error3(Object error) {

    }

    @Override
    public void success2(Object data) {
        AddressShowData addressShowData = (AddressShowData) data;
        datas.addAll(addressShowData.getResult());
        SharedPreferences.Editor edit = shared.edit();
        edit.putString("id", addressShowData.getResult().get(0).getId() + "");
        addressAdapter = new AddressAdapter(datas, this);
        mMyAddressRecy.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();

        addressAdapter.setClickCallBack(new AddressAdapter.ClickCallBack() {
            @Override
            public void callBack(View view, int i) {
                boolean childChecked = addressAdapter.setChildCheck();
                addressAdapter.isChildChecked(i, childChecked);
                addressAdapter.notifyDataSetChanged();
                int id = datas.get(i).getId();
                map.put("id", id + "");
                iPresenterImplPostPut.startLogin(setDefaultUrl, map, 6);
            }
        });
    }

    @Override
    public void error2(Object error) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void success(Object data) {
        /*DefaultAddressData defaultAddressData = (DefaultAddressData) data;
        String message = defaultAddressData.getMessage();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void error(Object error) {

    }
}
