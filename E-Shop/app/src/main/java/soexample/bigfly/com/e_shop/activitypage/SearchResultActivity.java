package soexample.bigfly.com.e_shop.activitypage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.MySearchAdapter;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.bean.MySeachData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGet;
import soexample.bigfly.com.e_shop.user_defined.user_defined_View;
import soexample.bigfly.com.e_shop.view.IView;

public class SearchResultActivity extends BaseActivity implements IView, XRecyclerView.LoadingListener {

    private int index = 1;
    private user_defined_View mMySeachResult;
    private XRecyclerView mMySearchXRecy;
    private String url = "http://172.17.8.100/small/commodity/v1/findCommodityByKeyword?count=500&page=";
    private MySearchAdapter mySearchAdapter;
    private ArrayList<MySeachData.ResultBean> datas = new ArrayList<>();
    private IPresenterImplGet iPresenterImplGet;
    private String mName;
    private String name;
    private ImageView cannot_find_img;
    private TextView cannot_find_tv;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initView() {
        mMySeachResult = findViewById(R.id.My_Seach_Result);
        mMySearchXRecy = findViewById(R.id.My_Search_XRecy);
        cannot_find_img = findViewById(R.id.cannot_find_img);
        cannot_find_tv = findViewById(R.id.cannot_find_tv);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mMySeachResult.getEdit().setText(mName);
    }

    @Override
    protected void setOnClick() {
        mMySeachResult.getEdit().setOnClickListener(this);
        name = mMySeachResult.getSearch().trim();
        mMySeachResult.getBack().setOnClickListener(this);
        mMySearchXRecy.setLoadingListener(this);

        GridLayoutManager manager = new GridLayoutManager(SearchResultActivity.this, 2);
        mMySearchXRecy.setLayoutManager(manager);
        mySearchAdapter = new MySearchAdapter(datas, SearchResultActivity.this);
        mMySearchXRecy.setAdapter(mySearchAdapter);
        mMySearchXRecy.setLoadingMoreEnabled(true);
        mMySearchXRecy.setPullRefreshEnabled(true);
    }

    @Override
    protected void proLogic() {
        iPresenterImplGet = new IPresenterImplGet(this);
        iPresenterImplGet.startLogin(url + index + "&keyword=" + mName, null, 4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Btn_Back:
                finish();
                break;
            case R.id.my_Search:

                Intent intent = new Intent(SearchResultActivity.this, FluidLayoutActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
        }
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
        mMySearchXRecy.refreshComplete();
        mMySearchXRecy.loadMoreComplete();

    }

    @Override
    public void error(Object error) {

    }

    @Override
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
}
