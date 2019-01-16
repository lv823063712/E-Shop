package soexample.bigfly.com.e_shop.fragmentsun;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.WaitMoneryAdapter_F;
import soexample.bigfly.com.e_shop.adapter.WaitReceiveAdapter_F;
import soexample.bigfly.com.e_shop.bean.WaitMoneyData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest;
import soexample.bigfly.com.e_shop.view.IView3;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitReceiveFragment extends Fragment implements IView3 {
    private String url = "http://172.17.8.100/small/order/verify/v1/findOrderListByStatus?page=1&count=5&status=2";
    private RecyclerView My_Wait_Receive_Recy;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private HashMap<String, String> map = new HashMap<>();
    private IPresenterImplGetRequest iPresenterImplGetRequest;
    private List<WaitMoneyData.OrderListBean> lists = new ArrayList<>();
    private WaitReceiveAdapter_F waitReceiveAdapter_f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_wait_receive, null);
        initView(inflate);

        shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);

        iPresenterImplGetRequest = new IPresenterImplGetRequest(this);
        iPresenterImplGetRequest.startLogin(url, map, 3);
        return inflate;
    }

    private void initView(View inflate) {
        My_Wait_Receive_Recy = (RecyclerView) inflate.findViewById(R.id.My_Wait_Receive_Recy);
    }

    @Override
    public void success3(Object data) {
        WaitMoneyData waitMoneyData = (WaitMoneyData) data;
        lists.addAll(waitMoneyData.getOrderList());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        My_Wait_Receive_Recy.setLayoutManager(manager);
        waitReceiveAdapter_f = new WaitReceiveAdapter_F(lists, getActivity());
        My_Wait_Receive_Recy.setAdapter(waitReceiveAdapter_f);
        waitReceiveAdapter_f.notifyDataSetChanged();
    }

    @Override
    public void error3(Object error) {

    }
}
