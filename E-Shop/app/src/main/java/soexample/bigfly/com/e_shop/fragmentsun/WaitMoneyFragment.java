package soexample.bigfly.com.e_shop.fragmentsun;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.MyNestAdapter;
import soexample.bigfly.com.e_shop.adapter.WaitMoneryAdapter_F;
import soexample.bigfly.com.e_shop.bean.OrderAllListData;
import soexample.bigfly.com.e_shop.bean.PaySuccessData;
import soexample.bigfly.com.e_shop.bean.WaitMoneyData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplPostPut;
import soexample.bigfly.com.e_shop.view.IView;
import soexample.bigfly.com.e_shop.view.IView3;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitMoneyFragment extends Fragment implements IView3, IView {
    private String url = "http://172.17.8.100/small/order/verify/v1/findOrderListByStatus?page=1&count=5&status=1";
    private RecyclerView My_Wait_Money_Recy;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private HashMap<String, String> map = new HashMap<>();
    private IPresenterImplGetRequest iPresenterImplGetRequest;
    private List<WaitMoneyData.OrderListBean> lists = new ArrayList<>();
    private WaitMoneryAdapter_F waitMoneryAdapter_f;
    private IPresenterImplPostPut iPresenterImplPostPut;
    private String payUrl = "http://172.17.8.100/small/order/verify/v1/pay";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_wait_money, null);
        initView(inflate);
        shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        Log.e("zzz", "onCreateView: " + sessionId);
        iPresenterImplGetRequest = new IPresenterImplGetRequest(this);
        iPresenterImplGetRequest.startLogin(url, map, 3);
        iPresenterImplPostPut = new IPresenterImplPostPut(this);
        return inflate;
    }

    private void initView(View inflate) {
        My_Wait_Money_Recy = (RecyclerView) inflate.findViewById(R.id.My_Wait_Money_Recy);
    }

    @Override
    public void success3(Object data) {
        WaitMoneyData waitMoneyData = (WaitMoneyData) data;
        lists.addAll(waitMoneyData.getOrderList());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        My_Wait_Money_Recy.setLayoutManager(manager);
        waitMoneryAdapter_f = new WaitMoneryAdapter_F(lists, getActivity());
        My_Wait_Money_Recy.setAdapter(waitMoneryAdapter_f);
        waitMoneryAdapter_f.notifyDataSetChanged();


        waitMoneryAdapter_f.setCallBack(new WaitMoneryAdapter_F.WaitMoneryCallBack() {
            @Override
            public void QuZhiFu(String orderId) {
                map.put("orderId", orderId);
                map.put("payType", 1 + "");
                iPresenterImplPostPut.startLogin(payUrl, map, 7);
            }
        });


    }

    @Override
    public void error3(Object error) {

    }

    @Override
    public void success(Object data) {
        PaySuccessData paySuccessData = (PaySuccessData) data;
        Toast.makeText(getActivity(), paySuccessData.getMessage(), Toast.LENGTH_SHORT).show();
        waitMoneryAdapter_f.notifyDataSetChanged();
    }

    @Override
    public void error(Object error) {

    }
}
