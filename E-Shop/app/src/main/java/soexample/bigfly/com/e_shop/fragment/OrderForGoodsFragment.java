package soexample.bigfly.com.e_shop.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.fragmentsun.HaveFinishFragment;
import soexample.bigfly.com.e_shop.fragmentsun.OrderAllListFragment;
import soexample.bigfly.com.e_shop.fragmentsun.WaitCommentFragment;
import soexample.bigfly.com.e_shop.fragmentsun.WaitMoneyFragment;
import soexample.bigfly.com.e_shop.fragmentsun.WaitReceiveFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderForGoodsFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mAllList;
    private LinearLayout mWaitMoney;
    private LinearLayout mWaitReceive;
    private LinearLayout mWaitComment;
    private LinearLayout mHaveFinish;
    private LinearLayout mTiaomu;
    private FrameLayout mMyAllListFragment;
    private FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_order_for_goods, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 实现透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        mAllList.setOnClickListener(this);
        mWaitMoney.setOnClickListener(this);
        mWaitReceive.setOnClickListener(this);
        mWaitComment.setOnClickListener(this);
        mHaveFinish.setOnClickListener(this);
    }

    private void initView(View inflate) {
        mAllList = (LinearLayout) inflate.findViewById(R.id.All_List);
        mWaitMoney = (LinearLayout) inflate.findViewById(R.id.Wait_Money);
        mWaitReceive = (LinearLayout) inflate.findViewById(R.id.Wait_Receive);
        mWaitComment = (LinearLayout) inflate.findViewById(R.id.Wait_Comment);
        mHaveFinish = (LinearLayout) inflate.findViewById(R.id.Have_Finish);
        mTiaomu = (LinearLayout) inflate.findViewById(R.id.tiaomu);
        mMyAllListFragment = (FrameLayout) inflate.findViewById(R.id.My_All_List_Fragment);
        manager = getChildFragmentManager();
        manager.beginTransaction().replace(R.id.My_All_List_Fragment, new OrderAllListFragment()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.All_List:
                manager = getChildFragmentManager();
                manager.beginTransaction().replace(R.id.My_All_List_Fragment, new OrderAllListFragment()).commit();
                break;
            case R.id.Wait_Money:
                manager = getChildFragmentManager();
                manager.beginTransaction().replace(R.id.My_All_List_Fragment, new WaitMoneyFragment()).commit();
                break;
            case R.id.Wait_Receive:
                manager = getChildFragmentManager();
                manager.beginTransaction().replace(R.id.My_All_List_Fragment, new WaitReceiveFragment()).commit();
                break;
            case R.id.Wait_Comment:
                manager = getChildFragmentManager();
                manager.beginTransaction().replace(R.id.My_All_List_Fragment, new WaitCommentFragment()).commit();
                break;
            case R.id.Have_Finish:
                manager = getChildFragmentManager();
                manager.beginTransaction().replace(R.id.My_All_List_Fragment, new HaveFinishFragment()).commit();
                break;
        }
    }
}
