package soexample.bigfly.com.e_shop.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.activitypage.SubmitOrderActivity;
import soexample.bigfly.com.e_shop.adapter.ShoppingCarAdapter;
import soexample.bigfly.com.e_shop.bean.ShoppingCarData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplPut;
import soexample.bigfly.com.e_shop.view.IView2;
import soexample.bigfly.com.e_shop.view.IView3;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingTrolleyFragment extends Fragment implements IView3, View.OnClickListener, IView2 {
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private View inflate;
    private String syncUrl = "http://172.17.8.100/small/order/verify/v1/syncShoppingCart";
    private String url = "http://172.17.8.100/small/order/verify/v1/findShoppingCart";
    private IPresenterImplGetRequest iPresenterImplGet;
    private RecyclerView mMyShoppingCar;
    private ArrayList<ShoppingCarData.ResultBean> datas = new ArrayList<>();
    private ShoppingCarAdapter adapter;
    private HashMap<String, String> map = new HashMap<>();
    private IPresenterImplPut iPresenterImplPut;
    private CheckBox My_Select_All;
    private TextView My_All_Price;
    private TextView Settle_Accounts;
    private boolean childChecked;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_shopping_trolley, null);
        shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        initView(inflate);
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        iPresenterImplGet = new IPresenterImplGetRequest(this);
        iPresenterImplGet.startLogin(url, map, 8);
        iPresenterImplPut = new IPresenterImplPut(this);

        return inflate;
    }

    private void initView(View inflate) {
        mMyShoppingCar = (RecyclerView) inflate.findViewById(R.id.My_Shopping_Car);
        mMyShoppingCar.setOnClickListener(this);
        My_Select_All = (CheckBox) inflate.findViewById(R.id.My_Select_All);
        My_Select_All.setOnClickListener(this);
        My_All_Price = (TextView) inflate.findViewById(R.id.My_All_Price);
        My_All_Price.setOnClickListener(this);
        Settle_Accounts = (TextView) inflate.findViewById(R.id.Settle_Accounts);
        Settle_Accounts.setOnClickListener(this);
        adapter = new ShoppingCarAdapter(datas, getActivity());
        mMyShoppingCar.setAdapter(adapter);
    }

    @Override
    public void success3(Object data) {
        ShoppingCarData shoppingCarData = (ShoppingCarData) data;
        datas.clear();
        datas.addAll(shoppingCarData.getResult());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mMyShoppingCar.setLayoutManager(manager);
        adapter.notifyDataSetChanged();

        adapter.setClickCallBack(new ShoppingCarAdapter.ClickCallBack() {
            @Override
            public void callBack(int i,int k) {
                //EventBus.getDefault().postSticky(i);
               if(datas.size()!=0)
               {
                   Log.d("zzz",i+"");
                   datas.remove(i);
               }
                int zhi =k;
                if (datas != null) {
                    JSONArray jsonArray = new JSONArray();
                    for (int j = 0; j < datas.size(); j++) {

                        JSONObject object = null;
                        object = new JSONObject();
                        try {
                            object.put("commodityId", datas.get(j).getCommodityId());
                            object.put("count", 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(object);
                    }
                    map.put("data", jsonArray.toString());
                    iPresenterImplPut.startLogin(syncUrl, map, 11);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        adapter.setCallBack(new ShoppingCarAdapter.ShoppingCallBack() {
            @Override
            public void setChildCheck(int childPosition) {
                childChecked = adapter.isChildChecked(childPosition);
                adapter.setChildCheck(childPosition, !childChecked);
                CalculateTheTotalPrice();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void setNumber(int num) {

            }
        });
    }

    @Override
    public void error3(Object error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Select_All:
                boolean b = adapter.checkedNoAll();
                adapter.checkedAll(!b);
                CalculateTheTotalPrice();
                adapter.notifyDataSetChanged();
                break;
            case R.id.Settle_Accounts:
                if (!childChecked) {
                    String goodsID = adapter.getGoodsID();
                    Intent intent = new Intent(getContext(), SubmitOrderActivity.class);
                    intent.putExtra("id", goodsID);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "还没有选中任何商品哦", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void CalculateTheTotalPrice() {
        boolean b = adapter.checkedNoAll();
        My_Select_All.setChecked(b);
        float v = adapter.setShoppingNumber();
        My_All_Price.setText(v + "0");
    }

    @Override
    public void success2(Object data) {
        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error2(Object error) {

    }
}
