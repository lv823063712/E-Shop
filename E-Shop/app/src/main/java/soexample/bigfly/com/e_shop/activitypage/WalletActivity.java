package soexample.bigfly.com.e_shop.activitypage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.bean.WalletData;
import soexample.bigfly.com.e_shop.ipresenter.IPresenter;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImplGet;
import soexample.bigfly.com.e_shop.view.IView;

public class WalletActivity extends BaseActivity implements IView {


    private TextView mMyBalance;
    private XRecyclerView mMyConsumptionDetail;
    private ImageView mBackWallet;
    private String url = "http://172.17.8.100/small/user/verify/v1/findUserWallet";
    private IPresenterImplGet iPresenterImplGet;
    private int userId;
    private String sessionId;

    @Override
    protected int getLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView() {
        mMyBalance = findViewById(R.id.My_Balance);
        mMyConsumptionDetail = findViewById(R.id.My_Consumption_Detail);
        mBackWallet = findViewById(R.id.Back_Wallet);
    }

    @Override
    protected void initData() {
        SharedPreferences user = WalletActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = user.getInt("userId", 0);
        sessionId = user.getString("sessionId", "");
    }

    @Override
    protected void setOnClick() {
        mBackWallet.setOnClickListener(this);
    }

    @Override
    protected void proLogic() {
        iPresenterImplGet = new IPresenterImplGet(this);
        iPresenterImplGet.startLogin(url + "?userId=" + userId + "&sessionId=" + sessionId, null, 6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Back_Wallet:
                finish();
                break;
        }
    }

    @Override
    public void success(Object data) {
        WalletData walletData = (WalletData) data;
        
    }

    @Override
    public void error(Object error) {

    }
}
