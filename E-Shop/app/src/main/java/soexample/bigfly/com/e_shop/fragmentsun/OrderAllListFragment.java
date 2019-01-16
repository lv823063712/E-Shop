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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.MyNestAdapter;
import soexample.bigfly.com.e_shop.adapter.OrderAllListAdapter;
import soexample.bigfly.com.e_shop.bean.OrderAllListData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest;
import soexample.bigfly.com.e_shop.view.IView3;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderAllListFragment extends Fragment implements IView3 {
    private String url = "http://172.17.8.100/small/order/verify/v1/findOrderListByStatus?page=1&count=5&status=0";
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private HashMap<String, String> map = new HashMap<>();
    private IPresenterImplGetRequest iPresenterImplGetRequest;
    private List<OrderAllListData.OrderListBean> lists = new ArrayList<>();
    private RecyclerView My_All_List_Recy;
    private MyNestAdapter myNestAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_order_all_list, null);
        initView(inflate);

        shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        iPresenterImplGetRequest = new IPresenterImplGetRequest(this);

        iPresenterImplGetRequest.startLogin(url, map, 2);
        return inflate;
    }

    private void initData() {

    }

    @Override
    public void success3(Object data) {
        OrderAllListData orderAllListData = (OrderAllListData) data;
        lists.addAll(orderAllListData.getOrderList());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        My_All_List_Recy.setLayoutManager(manager);
        myNestAdapter = new MyNestAdapter(lists, getActivity());
        My_All_List_Recy.setAdapter(myNestAdapter);
        myNestAdapter.notifyDataSetChanged();
    }
    @Override
    public void error3(Object error) {

    }

    private void initView(View inflate) {
        My_All_List_Recy = (RecyclerView) inflate.findViewById(R.id.My_All_List_Recy);
    }
}
