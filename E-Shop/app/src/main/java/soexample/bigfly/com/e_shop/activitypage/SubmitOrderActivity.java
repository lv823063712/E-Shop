package soexample.bigfly.com.e_shop.activitypage;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.AddressAdapter2;
import soexample.bigfly.com.e_shop.adapter.ShoppingCarAdapter2;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.bean.AddressShowData;
import soexample.bigfly.com.e_shop.bean.CreateOrderData;
import soexample.bigfly.com.e_shop.bean.ShoppingCarData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest2;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplPut;
import soexample.bigfly.com.e_shop.user_defined.UserPopwidow;
import soexample.bigfly.com.e_shop.view.IView;
import soexample.bigfly.com.e_shop.view.IView2;
import soexample.bigfly.com.e_shop.view.IView3;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener, IView3, IView2, IView {

    private TextView My_Insert_Address;
    private RecyclerView My_Shopping_Car;
    private String url = "http://172.17.8.100/small/order/verify/v1/findShoppingCart";
    private String addressUrl = "http://172.17.8.100/small/user/verify/v1/receiveAddressList";
    private String syncUrl = "http://172.17.8.100/small/order/verify/v1/createOrder";
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private HashMap<String, String> map = new HashMap<>();
    private IPresenterImplGetRequest iPresenterImplGet;
    private ArrayList<ShoppingCarData.ResultBean> datas = new ArrayList<>();
    private ArrayList<AddressShowData.ResultBean> addressDatas = new ArrayList();
    private ShoppingCarAdapter2 adapter;
    private ArrayList<ShoppingCarData.ResultBean> lists = new ArrayList<>();
    private TextView Total_Prices;
    private TextView Submit_Order;
    private PopupWindow mPopupWindow;
    private UserPopwidow userPopwidow;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;
    private RecyclerView My_Pop_Insert;
    private AddressAdapter2 addressAdapter;
    private TextView address_UserName;
    private TextView address_UserPhone;
    private TextView address_UserLocal;
    private RelativeLayout My_Insert_add_Address;
    private IPresenterImplPut iPresenterImplPut;

    @Override
    protected int getLayout() {
        return R.layout.activity_submit_order;
    }

    protected void initView() {
        My_Insert_Address = findViewById(R.id.My_Insert_Address);
        address_UserName = findViewById(R.id.address_UserName);
        address_UserPhone = findViewById(R.id.address_UserPhone);
        address_UserLocal = findViewById(R.id.address_UserLocal);
        My_Insert_add_Address = findViewById(R.id.My_Insert_add_Address);
        My_Shopping_Car = (RecyclerView) findViewById(R.id.My_Shopping_Car);
        Total_Prices = findViewById(R.id.Total_Prices);
        Submit_Order = findViewById(R.id.Submit_Order);
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");

        map.put("userId", userId + "");
        map.put("sessionId", sessionId);


        iPresenterImplGet = new IPresenterImplGetRequest(this);
        iPresenterImplGet.startLogin(url, map, 8);

        mPopupWindow = new PopupWindow(this);
        userPopwidow = new UserPopwidow();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setOnClick() {
        Submit_Order.setOnClickListener(this);
        My_Insert_Address.setOnClickListener(this);
    }

    @Override
    protected void proLogic() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Insert_Address:
                showPop();
                toggleBright();
                break;
            case R.id.Submit_Order:
                iPresenterImplPut = new IPresenterImplPut(this);
                iPresenterImplPut.startLogin(syncUrl, map, 12);
                Intent intent = new Intent();
                break;
        }
    }

    private void showPop() {
        // 设置布局文件
        View v = LayoutInflater.from(this).inflate(R.layout.pop_add, null);
        mPopupWindow.setContentView(v);
        My_Pop_Insert = v.findViewById(R.id.My_Pop_Insert);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        My_Pop_Insert.setLayoutManager(manager);
        IPresenterImplGetRequest2 iPresenterImplGetRequest2 = new IPresenterImplGetRequest2(this);

        iPresenterImplGetRequest2.startLogin(addressUrl, map, 1);

        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAsDropDown(My_Insert_Address, -100, 0);
        // 设置pop关闭监听，用于改变背景透明度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });
    }

    private void toggleBright() {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        userPopwidow.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        userPopwidow.addUpdateListener(new UserPopwidow.UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        userPopwidow.addEndListner(new UserPopwidow.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });

    }

    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // 此方法用来设置浮动层，防止部分手机变暗无效
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    @Override
    public void success3(Object data) {
        ShoppingCarData shoppingCarData = (ShoppingCarData) data;
        datas.addAll(shoppingCarData.getResult());
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String[] split = id.split(",");
        float price = 0;
        //此处循环需要大循环套小循环
        for (int i = 0; i < datas.size(); i++) {
            int i1 = datas.get(i).getCommodityId();
            for (int j = 0; j < split.length; j++) {
                int i2 = Integer.parseInt(split[j]);
                if (i1 == i2) {
                    lists.add(datas.get(i));
                    Double price1 = Double.valueOf(lists.get(j).getPrice());
                    map.put("totalPrice", price1 + "");
                    price += datas.get(i).getCount() * datas.get(i).getPrice();
                    int zhi = lists.get(j).getCommodityId();
                    if (lists != null) {
                        JSONArray jsonArray = new JSONArray();


                        JSONObject object = null;
                        object = new JSONObject();
                        try {
                            object.put("commodityId", zhi);
                            object.put("amount", 1);
                            jsonArray.put(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        map.put("orderInfo", jsonArray.toString());
                        jsonArray = null;
                    }


                }
            }
        }
        Total_Prices.setText("共" + split.length + "件商品,需付款" + price + ".00元");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        My_Shopping_Car.setLayoutManager(manager);
        adapter = new ShoppingCarAdapter2(lists, this);
        My_Shopping_Car.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void error3(Object error) {

    }

    @Override
    public void success2(Object data) {
        CreateOrderData createOrderData = (CreateOrderData) data;
        Toast.makeText(this, createOrderData.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error2(Object error) {

    }

    @Override
    public void success(Object data) {
        AddressShowData addressShowData = (AddressShowData) data;
        addressDatas.addAll(addressShowData.getResult());
        addressAdapter = new AddressAdapter2(addressDatas, this);
        My_Pop_Insert.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();

        if (addressShowData.getMessage().equals("查询成功")) {
            My_Insert_Address.setVisibility(View.VISIBLE);
            My_Insert_add_Address.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "还没有地址哦", Toast.LENGTH_SHORT).show();
            return;
        }
        addressAdapter.setClickCallBack(new AddressAdapter2.ClickCallBack() {

            private int id;

            @Override
            public void callBack(int i) {
                address_UserName.setText(addressDatas.get(i).getRealName());
                address_UserPhone.setText(addressDatas.get(i).getPhone());
                address_UserLocal.setText(addressDatas.get(i).getAddress());
                id = addressDatas.get(i).getId();
                map.put("addressId", id + "");
                My_Insert_add_Address.setVisibility(View.VISIBLE);
                My_Insert_Address.setVisibility(View.GONE);
                addressAdapter.notifyDataSetChanged();
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    public void error(Object error) {

    }
}
