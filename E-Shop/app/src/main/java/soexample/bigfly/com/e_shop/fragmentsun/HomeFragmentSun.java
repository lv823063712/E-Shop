package soexample.bigfly.com.e_shop.fragmentsun;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.activitypage.DetailsActivity;
import soexample.bigfly.com.e_shop.adapter.MyHotAdapter;
import soexample.bigfly.com.e_shop.adapter.MyMLSSAdapter;
import soexample.bigfly.com.e_shop.adapter.MyPZSHAdapter;
import soexample.bigfly.com.e_shop.bean.MyBanner;
import soexample.bigfly.com.e_shop.bean.MyHotData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGet;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetGoods;
import soexample.bigfly.com.e_shop.view.IView;
import soexample.bigfly.com.e_shop.view.IView2;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentSun<T> extends Fragment implements IView2<T>, IView<T> {
    private MyHotAdapter myHotAdapter;
    private MyMLSSAdapter myMLSSAdapter;
    private MyPZSHAdapter myPZSHAdapter;
    private TextView mTitle_r;
    private RecyclerView mMy_hot_sale;
    private TextView mTitle_m;
    private RecyclerView mMy_magic_vogue;
    private TextView mTitle_p;
    private RecyclerView mMy_qualty_life;
    private IPresenterImplGetGoods iPresenter2;
    private ArrayList<String> titles = new ArrayList<>();
    private List<MyHotData.ResultBean.RxxpBean.CommodityListBean> lists_r = new ArrayList<>();
    private List<MyHotData.ResultBean.MlssBean.CommodityListBeanXX> lists_m = new ArrayList<>();
    private List<MyHotData.ResultBean.PzshBean.CommodityListBeanX> lists_p = new ArrayList<>();
    private String mUrl = "http://172.17.8.100/small/commodity/v1/commodityList";
    private String url = "http://172.17.8.100/small/commodity/v1/bannerShow";
    private List<String> test = new ArrayList<>();
    private XBanner xbanner;
    private IPresenterImplGet iPresenter;
    private ArrayList<String> datas = new ArrayList<>();

    private SharedPreferences shared;
    private int userId;
    private String sessionId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home_fragment_sun, null);
        shared = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        initView(inflate);
        iPresenter = new IPresenterImplGet(this);
        iPresenter.startLogin(url, null, 3);
        iPresenter2 = new IPresenterImplGetGoods(this);
        iPresenter2.startLogin(mUrl, null, 2);
        setListener();
        return inflate;
    }

    private void setListener() {
        myHotAdapter = new MyHotAdapter(lists_r, getActivity());
        mMy_hot_sale.setAdapter(myHotAdapter);
        myMLSSAdapter = new MyMLSSAdapter(lists_m, getActivity());
        mMy_magic_vogue.setAdapter(myMLSSAdapter);
        myPZSHAdapter = new MyPZSHAdapter(lists_p, getActivity());
        mMy_qualty_life.setAdapter(myPZSHAdapter);
        //热销商品的点击事件
        myHotAdapter.setClickCallBack(new MyHotAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {

                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("zhi", i);
                startActivity(intent);
            }
        });
        //魔丽时尚的点击事件
        myMLSSAdapter.setClickCallBack(new MyMLSSAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("zhi", i);
                startActivity(intent);
            }
        });
        //品质生活的点击事件
        myPZSHAdapter.setClickCallBack(new MyPZSHAdapter.ClickCallBack() {
            @Override
            public void callBack(int i) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("zhi", i);

                startActivity(intent);
            }
        });
    }

    private void initView(View inflate) {
        mTitle_r = (TextView) inflate.findViewById(R.id.Title_r);
        mMy_hot_sale = inflate.findViewById(R.id.My_hot_sale);
        mTitle_m = (TextView) inflate.findViewById(R.id.Title_m);
        mMy_magic_vogue = inflate.findViewById(R.id.My_magic_vogue);
        mTitle_p = (TextView) inflate.findViewById(R.id.Title_p);
        mMy_qualty_life = inflate.findViewById(R.id.My_qualty_life);
        xbanner = (XBanner) inflate.findViewById(R.id.xbanner);
    }

    @Override
    public void success(Object data) {
        final MyBanner myBanner = (MyBanner) data;
        //此处需要加判空,否则轮播图不出来
        List<MyBanner.ResultBean> result = myBanner.getResult();
        for (int i = 0; i < myBanner.getResult().size(); i++) {
            datas.add(myBanner.getResult().get(i).getImageUrl());
        }
        if (!datas.isEmpty()) {
            xbanner.setData(myBanner.getResult(), null);
            xbanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getActivity()).load(myBanner.getResult().get(position).getImageUrl()).into((ImageView) view);
                }
            });
            //横向移动
            xbanner.setPageTransformer(Transformer.Default);
        }
    }

    @Override
    public void error(Object error) {
        Toast.makeText(getActivity(), "失败了,获取不到图片", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void success2(Object data) {
        MyHotData myHotData = (MyHotData) data;
        //添加表头的名字
        titles.add(myHotData.getResult().getRxxp().get(0).getName());
        titles.add(myHotData.getResult().getMlss().get(0).getName());
        titles.add(myHotData.getResult().getPzsh().get(0).getName());
        //表头的名字
        mTitle_r.setText(titles.get(0));
        mTitle_m.setText(titles.get(1));
        mTitle_p.setText(titles.get(2));
        //添加数据内容
        lists_r.addAll(myHotData.getResult().getRxxp().get(0).getCommodityList());
        lists_m.addAll(myHotData.getResult().getMlss().get(0).getCommodityList());
        lists_p.addAll(myHotData.getResult().getPzsh().get(0).getCommodityList());
        //数据管理显示
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        mMy_hot_sale.setLayoutManager(manager);

        LinearLayoutManager manager_m = new LinearLayoutManager(getActivity());
        mMy_magic_vogue.setLayoutManager(manager_m);

        GridLayoutManager manager_p = new GridLayoutManager(getActivity(), 2);
        mMy_qualty_life.setLayoutManager(manager_p);
        myHotAdapter.notifyDataSetChanged();
        myMLSSAdapter.notifyDataSetChanged();
        myPZSHAdapter.notifyDataSetChanged();

    }

    @Override
    public void error2(Object error) {

    }

}
