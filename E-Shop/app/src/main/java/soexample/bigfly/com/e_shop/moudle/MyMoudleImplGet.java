package soexample.bigfly.com.e_shop.moudle;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import soexample.bigfly.com.e_shop.bean.GoodsParticularsData;
import soexample.bigfly.com.e_shop.bean.MyBanner;
import soexample.bigfly.com.e_shop.bean.MyCircleData;
import soexample.bigfly.com.e_shop.bean.MyHotData;
import soexample.bigfly.com.e_shop.bean.MyLogin;
import soexample.bigfly.com.e_shop.bean.MyRegister;
import soexample.bigfly.com.e_shop.bean.MySeachData;
import soexample.bigfly.com.e_shop.bean.ShoppingCarData;
import soexample.bigfly.com.e_shop.bean.WalletData;
import soexample.bigfly.com.e_shop.callback.MyCallBack;
import soexample.bigfly.com.e_shop.utils.OkHttpUtils;

/**
 * <p>文件描述：moudle层的实现类<p>
 * <p>作者：${lvf}<p>
 * <p>创建时间：2018/12/27<p>
 * <p>更改时间：2018/12/27<p>
 * <p>版本号：1<p>
 */

public class MyMoudleImplGet implements MyMoudleGet {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String jsons = (String) msg.obj;
                Gson gson = new Gson();
                if (index == 3) {
                    MyBanner myBanner = gson.fromJson(jsons, MyBanner.class);
                    myCallBack.setData(myBanner);
                }
                if (index == 2) {
                    MyHotData myHotData = gson.fromJson(jsons, MyHotData.class);
                    myCallBack.setData(myHotData);
                }
                if (index == 4) {
                    MySeachData mySeachData = gson.fromJson(jsons, MySeachData.class);
                    myCallBack.setData(mySeachData);
                }
                if (index == 5) {
                    MyCircleData myCircleData = gson.fromJson(jsons, MyCircleData.class);
                    myCallBack.setData(myCircleData);
                }
                if (index == 6) {
                    WalletData walletData = gson.fromJson(jsons, WalletData.class);
                    myCallBack.setData(walletData);
                }
                if (index == 7) {
                    GoodsParticularsData goodsParticularsData = gson.fromJson(jsons, GoodsParticularsData.class);
                    myCallBack.setData(goodsParticularsData);
                }

            }
        }
    };

    private MyCallBack myCallBack;
    private int index;

    @Override
    public void startRequest(String url, int type, MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        this.index = type;
        OkHttpUtils.getInstance().get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.sendMessage(handler.obtainMessage(0, response.body().string()));
            }
        });
    }
}
