package soexample.bigfly.com.e_shop.fragmentsun;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.activitypage.DetailsActivity;
import soexample.bigfly.com.e_shop.activitypage.FluidLayoutActivity;
import soexample.bigfly.com.e_shop.activitypage.SearchResultActivity;
import soexample.bigfly.com.e_shop.adapter.MySearchAdapter;
import soexample.bigfly.com.e_shop.bean.MySeachData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGet;
import soexample.bigfly.com.e_shop.user_defined.user_defined_View;
import soexample.bigfly.com.e_shop.view.IView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements IView, View.OnClickListener {
    private String url = "http://172.17.8.100/small/commodity/v1/findCommodityByKeyword?count=500&page=";
    private XRecyclerView My_Search_XRecy;
    private int index = 1;
    private String mName;
    private String name;
    private MySearchAdapter mySearchAdapter;
    private ArrayList<MySeachData.ResultBean> datas = new ArrayList<>();
    private IPresenterImplGet iPresenterImplGet;
    private user_defined_View My_Seach_Result;
    private ImageView cannot_find_img;
    private TextView cannot_find_tv;
    private FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, null);
        initView(v);
        EventBus.getDefault().register(this);
        iPresenterImplGet = new IPresenterImplGet(this);
        iPresenterImplGet.startLogin(url + index + "&keyword=" + mName, null, 4);
        setListener();
        return v;
    }

    private void initView(View v) {
        My_Search_XRecy = (XRecyclerView) v.findViewById(R.id.My_Search_XRecy);
        My_Seach_Result = (user_defined_View) v.findViewById(R.id.My_Seach_Result);
        cannot_find_img = (ImageView) v.findViewById(R.id.cannot_find_img);
        cannot_find_tv = (TextView) v.findViewById(R.id.cannot_find_tv);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        My_Search_XRecy.setLayoutManager(manager);
        mySearchAdapter = new MySearchAdapter(datas, getActivity());
        My_Search_XRecy.setAdapter(mySearchAdapter);
        My_Search_XRecy.setLoadingMoreEnabled(true);
        My_Search_XRecy.setPullRefreshEnabled(true);


        My_Seach_Result.getEdit().setOnClickListener(this);
        name = My_Seach_Result.getSearch().trim();
        My_Seach_Result.getBack().setOnClickListener(this);
        My_Seach_Result.setOnClickListener(this);

    }


    //eventbus粘性传值
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getInfo(String Name) {
        //传值
        this.mName = Name;
        My_Seach_Result.getEdit().setText(mName);
        Toast.makeText(getActivity(), mName, Toast.LENGTH_SHORT).show();

    }

    private void setListener() {
        mySearchAdapter.setClickCallBack(new MySearchAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("zhi", i);
                startActivity(intent);
            }
        });


        My_Search_XRecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override//上拉刷新
            public void onRefresh() {
                datas.clear();
                mySearchAdapter.notifyDataSetChanged();
                index = 1;
                iPresenterImplGet.startLogin(url + index + "&keyword=" + mName, null, 4);
            }

            @Override
            public void onLoadMore() {
                index++;
                iPresenterImplGet.startLogin(url + index + "&keyword=" + mName, null, 4);
            }
        });
    }


    @Override
    public void success(Object data) {
        MySeachData mySeachData = (MySeachData) data;
        datas.addAll(mySeachData.getResult());
        if (datas.size() == 0) {
            cannot_find_img.setVisibility(View.VISIBLE);
            cannot_find_tv.setVisibility(View.VISIBLE);
        } else {
            cannot_find_img.setVisibility(View.GONE);
            cannot_find_tv.setVisibility(View.GONE);
        }
        mySearchAdapter.notifyDataSetChanged();
        My_Search_XRecy.refreshComplete();
        My_Search_XRecy.loadMoreComplete();
    }

    @Override
    public void error(Object error) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.My_Btn_Back:
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.My_Seach_Fragment, new FluidFragment()).commit();
                break;
            case R.id.my_Search:
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.My_Seach_Fragment, new FluidFragment()).commit();
                break;

        }
    }
}
