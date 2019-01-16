package soexample.bigfly.com.e_shop.moudle;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import soexample.bigfly.com.e_shop.bean.AddressShowData;
import soexample.bigfly.com.e_shop.bean.HeadPortraitData;
import soexample.bigfly.com.e_shop.bean.MyBanner;
import soexample.bigfly.com.e_shop.bean.MyLogin;
import soexample.bigfly.com.e_shop.bean.MyRegister;
import soexample.bigfly.com.e_shop.bean.NewAddress;
import soexample.bigfly.com.e_shop.bean.OrderAllListData;
import soexample.bigfly.com.e_shop.bean.ShoppingCarData;
import soexample.bigfly.com.e_shop.bean.WaitMoneyData;
import soexample.bigfly.com.e_shop.callback.MyCallBack;
import soexample.bigfly.com.e_shop.utils.OkHttpUtils;

/**
 * <p>文件描述：moudle层的实现类<p>
 * <p>作者：${lvf}<p>
 * <p>创建时间：2018/12/27<p>
 * <p>更改时间：2018/12/27<p>
 * <p>版本号：1<p>
 */

public class MyMoudleImplGetRequest implements MyMoudle {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String jsons = (String) msg.obj;
                Gson gson = new Gson();
                if (index == 8) {
                    ShoppingCarData shoppingCarData = gson.fromJson(jsons, ShoppingCarData.class);
                    myCallBack.setData(shoppingCarData);
                }
                if (index == 9) {
                    NewAddress newAddress = gson.fromJson(jsons, NewAddress.class);
                    myCallBack.setData(newAddress);
                }
                if (index == 1) {
                    AddressShowData addressShowData = gson.fromJson(jsons, AddressShowData.class);
                    myCallBack.setData(addressShowData);
                }
                if (index == 2) {
                    OrderAllListData orderAllListData = gson.fromJson(jsons, OrderAllListData.class);
                    myCallBack.setData(orderAllListData);
                }
                if (index == 3) {
                    WaitMoneyData waitMoneyData = gson.fromJson(jsons, WaitMoneyData.class);
                    myCallBack.setData(waitMoneyData);
                }

            }
        }
    };

    private MyCallBack myCallBack;
    private int index;

    @Override
    public void startRequest(String url, HashMap<String, String> map, int type, MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        this.index = type;
        OkHttpUtils.getInstance().getRequestHeader(url, map, new Callback() {
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
