package soexample.bigfly.com.e_shop.fragmentsun;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.bean.WaitMoneyData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitCommentFragment extends Fragment {

    private String url = "http://172.17.8.100/small/order/verify/v1/findOrderListByStatus?page=1&count=5&status=3";
    private RecyclerView My_Wait_Receive_Recy;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private HashMap<String, String> map = new HashMap<>();
    private IPresenterImplGetRequest iPresenterImplGetRequest;
    private List<WaitMoneyData.OrderListBean> lists = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_wait_comment, null);



        return inflate;
    }

}
