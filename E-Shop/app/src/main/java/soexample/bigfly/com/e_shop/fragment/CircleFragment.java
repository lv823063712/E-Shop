package soexample.bigfly.com.e_shop.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.MyCircleAdapter;
import soexample.bigfly.com.e_shop.bean.MyCircleData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGet;
import soexample.bigfly.com.e_shop.view.IView;

/**
 * A simple {@link Fragment} subclass.
 */

public class CircleFragment extends Fragment implements IView {
    private String url = "http://172.17.8.100/small/circle/v1/findCircleList";
    private String mUrl = "http://172.17.8.100/small/circle/v1/addCircleGreat";
    private String qxUrl="http://172.17.8.100/small/circle/v1/cancelCircleGreat";
    private int index = 1;
    private MyCircleAdapter myCircleAdapter;
    private List<MyCircleData.ResultBean> datas = new ArrayList<>();
    private IPresenterImplGet iPresenterImplGet;
    private View view;
    private XRecyclerView My_Circle_Xrecy;
    private int userId;
    private String sessionId;
    private Map<String, String> map = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circle, null);
        initView(view);
        SharedPreferences user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = user.getInt("userId", 0);
        sessionId = user.getString("sessionId", "");

        iPresenterImplGet = new IPresenterImplGet(this);
        iPresenterImplGet.startLogin(url + "?count=50" + "&userId=" + userId + "&sessionId=" + sessionId + "&page=" + index, null, 5);
        setListener();
        return view;
    }

    private void setListener() {
        My_Circle_Xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                datas.clear();
                myCircleAdapter.notifyDataSetChanged();
                index = 1;
                iPresenterImplGet.startLogin(url + "?count=50" + "&userId=" + userId + "&sessionId=" + sessionId + "&page=" + index, null, 5);

            }

            @Override
            public void onLoadMore() {
                index++;
                iPresenterImplGet.startLogin(url + "?count=50" + "&userId=" + userId + "&sessionId=" + sessionId + "&page=" + index, null, 5);

            }
        });
    }

    @Override
    public void success(Object data) {
        MyCircleData myCircleData = (MyCircleData) data;
        datas.addAll(myCircleData.getResult());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        My_Circle_Xrecy.setLayoutManager(manager);
        myCircleAdapter = new MyCircleAdapter(datas, getContext());
        My_Circle_Xrecy.setAdapter(myCircleAdapter);
        myCircleAdapter.notifyDataSetChanged();
        My_Circle_Xrecy.refreshComplete();
        My_Circle_Xrecy.loadMoreComplete();
    }

    @Override
    public void error(Object error) {

    }

    private void initView(View view) {
        My_Circle_Xrecy = (XRecyclerView) view.findViewById(R.id.My_Circle_Xrecy);
    }
}
