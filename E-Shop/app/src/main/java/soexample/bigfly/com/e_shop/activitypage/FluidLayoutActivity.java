package soexample.bigfly.com.e_shop.activitypage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.dao.MyDao;
import soexample.bigfly.com.e_shop.fragmentsun.FluidFragment;
import soexample.bigfly.com.e_shop.fragmentsun.SearchFragment;
import soexample.bigfly.com.e_shop.user_defined.User_Defined;
import soexample.bigfly.com.e_shop.user_defined.user_defined_View;

public class FluidLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] lists = {"手机", "电脑", "零食", "配件", "耳机"};
    private user_defined_View mMyHeader;
    private TextView mMyDel;
    private User_Defined mMyHistory;
    private User_Defined mMyHot;
    private MyDao myDao;
    private ArrayList<String> datas = new ArrayList<>();
    private ArrayList<String> mHistory = new ArrayList<>();
    private String mName;


    private FragmentManager manager;
    private SearchFragment searchFragment = new SearchFragment();
    private FluidFragment fluidFragment = new FluidFragment();
    private FrameLayout My_Seach_Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluid_layout);
        initView();
    }

    private void initView() {
        My_Seach_Fragment = findViewById(R.id.My_Seach_Fragment);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.My_Seach_Fragment, fluidFragment).commit();

    }

    @Override
    public void onClick(View v) {

    }
}
