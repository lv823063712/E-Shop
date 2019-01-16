package soexample.bigfly.com.e_shop.activitypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.DetailsAdapter;
import soexample.bigfly.com.e_shop.adapter.LunBoTuAdapter;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.bean.GoodsParticularsData;
import soexample.bigfly.com.e_shop.bean.ShoppingCarData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImpl;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGet;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGetRequest;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplPut;
import soexample.bigfly.com.e_shop.view.IView;
import soexample.bigfly.com.e_shop.view.IView2;
import soexample.bigfly.com.e_shop.view.IView3;

public class DetailsActivity extends BaseActivity implements IView, IView3, IView2 {
    private String particularsUrl = "http://172.17.8.100/small/commodity/v1/findCommodityDetailsById";
    private String GoodsCommentUrl = "http://172.17.8.100/small/commodity/verify/v1/addCommodityComment";
    private String url = "http://172.17.8.100/small/order/verify/v1/syncShoppingCart";
    private ViewPager My_Dteails_Banner;
    private RecyclerView My_Details_Show;
    private RecyclerView My_Details_Comment;
    private SharedPreferences shared;
    private int userId;
    private String sessionId;
    private List<String> lists = new ArrayList<>();
    private IPresenterImplGet iPresenterImplGet;
    private int zhi;
    private GoodsParticularsData goodsParticularsData;
    private ArrayList<GoodsParticularsData.ResultBean> datas = new ArrayList<>();
    private DetailsAdapter adapter;
    private LunBoTuAdapter lunBoTuAdapter;
    private WebView My_Web;
    private ImageView My_Details_Back;
    private Button AddShoppingCar;
    private IPresenterImplGetRequest iPresenterImplGetRequest;
    private String selectUrl = "http://172.17.8.100/small/order/verify/v1/findShoppingCart";
    /*private Button AddBuyBuyBuy;*/
    //评论接口
    private HashMap<String, String> map = new HashMap<>();
    private ArrayList<ShoppingCarData.ResultBean> myLists = new ArrayList<>();
    private JSONArray jsonArray;

    @Override
    protected int getLayout() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        My_Details_Back = findViewById(R.id.My_Details_Back);
        My_Details_Back.setOnClickListener(this);
        AddShoppingCar = findViewById(R.id.AddShoppingCar);
        AddShoppingCar.setOnClickListener(this);

        My_Dteails_Banner = findViewById(R.id.My_Dteails_Banner);

        My_Details_Show = findViewById(R.id.My_Details_Show);
        My_Details_Comment = findViewById(R.id.My_Details_Comment);
        My_Web = findViewById(R.id.My_Web);
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = shared.getInt("userId", 0);
        sessionId = shared.getString("sessionId", "");
        Intent intent = getIntent();
        zhi = intent.getIntExtra("zhi", 0);

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setOnClick() {

    }

    @Override
    protected void proLogic() {
        iPresenterImplGet = new IPresenterImplGet(this);

        //iPresenter = new IPresenterImpl(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        My_Details_Show.setLayoutManager(manager);
        adapter = new DetailsAdapter(datas, DetailsActivity.this);
        My_Details_Show.setAdapter(adapter);

        //iPresenter.startLogin(GoodsCommentUrl, );
        iPresenterImplGet.startLogin(particularsUrl + "?commodityId=" + zhi + "&userId" + userId + "&sessionId" + sessionId, null, 7);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Details_Back:
                finish();
                break;
            case R.id.AddShoppingCar:
                map.put("userId", userId + "");
                map.put("sessionId", sessionId);
                iPresenterImplGetRequest = new IPresenterImplGetRequest(this);
                iPresenterImplGetRequest.startLogin(selectUrl, map, 8);

                break;
        }
    }

    @Override
    public void success(Object data) {
        goodsParticularsData = (GoodsParticularsData) data;
        datas.add(goodsParticularsData.getResult());
        String picture = goodsParticularsData.getResult().getPicture();
        String[] split = picture.split("\\,");
        for (int i = 0; i < split.length; i++) {
            lists.add(split[i]);
        }
        lunBoTuAdapter = new LunBoTuAdapter(lists, this);
        My_Dteails_Banner.setAdapter(lunBoTuAdapter);

        WebSettings settings = My_Web.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        String js = "<script type=\"text/javascript\">" +
                "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                "imgs[i].style.width = '100%';" +  // 宽度改为100%
                "imgs[i].style.height = 'auto';" +
                "}" +
                "</script>";
        My_Web.loadDataWithBaseURL(null, goodsParticularsData.getResult().getDetails() + js, "text/html", "utf-8", null);
    }

    @Override
    public void error(Object error) {

    }

    @Override
    public void success3(Object data) {
        ShoppingCarData carData = (ShoppingCarData) data;
        myLists.addAll(carData.getResult());
        if (myLists != null) {
            jsonArray = new JSONArray();
            for (int i = 0; i < myLists.size(); i++) {
                JSONObject object = null;
                object = new JSONObject();
                try {
                    //查出来的值
                    object.put("commodityId", myLists.get(i).getCommodityId());
                    object.put("count", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(object);
            }
            JSONObject object = null;
            object = new JSONObject();
            try {
                //将要添加的值
                object.put("commodityId", zhi);
                object.put("count", 1);
                jsonArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            IPresenterImplPut iPresenterImplPut = new IPresenterImplPut(this);
            map.put("data", jsonArray.toString());
            iPresenterImplPut.startLogin(url, map, 10);

        }
    }

    @Override
    public void error3(Object error) {

    }

    @Override
    public void success2(Object data) {
        Toast.makeText(this, "添加成功,快去购物车结算吧", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void error2(Object error) {

    }
}
