package soexample.bigfly.com.e_shop.activitypage;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;

import java.util.ArrayList;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.adapter.MyFragmentAdapter;
import soexample.bigfly.com.e_shop.adapter.NoScrollViewPager;
import soexample.bigfly.com.e_shop.base.BaseActivity;
import soexample.bigfly.com.e_shop.fragment.CircleFragment;
import soexample.bigfly.com.e_shop.fragment.HomePageFragment;
import soexample.bigfly.com.e_shop.fragment.MainFragment;
import soexample.bigfly.com.e_shop.fragment.OrderForGoodsFragment;
import soexample.bigfly.com.e_shop.fragment.ShoppingTrolleyFragment;

public class HomePageActivity extends BaseActivity {

    private RadioGroup My_RG;
    private NoScrollViewPager My_Fragment;
    private ArrayList<Fragment> datas = new ArrayList<>();
    private MyFragmentAdapter myFragmentAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initView() {
        My_RG = findViewById(R.id.My_RG);
        My_Fragment = findViewById(R.id.My_Fragment);


    }

    @Override
    protected void initData() {
        datas.add(new HomePageFragment());
        datas.add(new CircleFragment());
        datas.add(new ShoppingTrolleyFragment());
        datas.add(new OrderForGoodsFragment());
        datas.add(new MainFragment());

        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), datas);
        My_Fragment.setAdapter(myFragmentAdapter);
    }

    @Override
    protected void setOnClick() {

        My_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.My_tab_btn_home) {
                    My_Fragment.setCurrentItem(0);
                } else if (checkedId == R.id.My_tab_btn_circle) {
                    My_Fragment.setCurrentItem(1);
                } else if (checkedId == R.id.My_tab_btn_comment) {
                    My_Fragment.setCurrentItem(2);
                } else if (checkedId == R.id.My_tab_btn_list) {
                    My_Fragment.setCurrentItem(3);
                } else if (checkedId == R.id.My_tab_btn_my) {
                    My_Fragment.setCurrentItem(4);
                }

            }
        });


    }

    @Override
    protected void proLogic() {

    }

    @Override
    public void onClick(View v) {

    }

}
