package soexample.bigfly.com.e_shop.fragmentsun;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.activitypage.FluidLayoutActivity;
import soexample.bigfly.com.e_shop.activitypage.SearchResultActivity;
import soexample.bigfly.com.e_shop.dao.MyDao;
import soexample.bigfly.com.e_shop.user_defined.User_Defined;
import soexample.bigfly.com.e_shop.user_defined.user_defined_View;

/**
 * A simple {@link Fragment} subclass.
 */
public class FluidFragment extends Fragment implements View.OnClickListener {
    private String[] lists = {"手机", "电脑", "零食", "配件", "耳机"};
    private View inflate;
    private TextView My_del;
    private User_Defined My_History;
    private User_Defined My_Hot;
    private MyDao myDao;
    private ArrayList<String> datas = new ArrayList<>();
    private ArrayList<String> mHistory = new ArrayList<>();
    private String mName;
    private user_defined_View mMyHeader;
    private FragmentManager manager;
    private SearchFragment searchFragment = new SearchFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_fluid, null);
        myDao = new MyDao(getContext());
        mHistory = myDao.show();
        //初始化数据
        initData();
        initView(inflate);
        //进行一个判断
        if (!mHistory.isEmpty()) {
            //将默认数据直接从数据库添加到集合中
            My_History.setData(mHistory);
        }
        return inflate;
    }

    private void initData() {
        for (int i = 0; i < lists.length; i++) {
            datas.add(lists[i]);
        }
    }

    private void initView(View inflate) {
        My_del = (TextView) inflate.findViewById(R.id.My_del);
        My_History = (User_Defined) inflate.findViewById(R.id.My_History);
        My_Hot = (User_Defined) inflate.findViewById(R.id.My_Hot);
        My_Hot.setData(datas);
        My_del.setOnClickListener(this);
        mMyHeader = (user_defined_View) inflate.findViewById(R.id.My_Header);
        mMyHeader.getBack().setOnClickListener(this);
        mMyHeader.getAdd().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Search_Btn:
                String name = mMyHeader.getSearch().trim();
                //进行一个非空判断
                if (name != null && name.equals("")) {
                    Toast.makeText(getContext(), "输入为空", Toast.LENGTH_SHORT).show();
                } else {

                    myDao.insertall(mMyHeader.getSearch().trim());
                    //自己封装一个删除子控件
                    My_History.removeChildView();
                    //将数据添加到集合中
                    mHistory.add(name);
                    My_History.setData(mHistory);

                    EventBus.getDefault().postSticky(name);
                    manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.My_Seach_Fragment, searchFragment).commit();


                }
                break;
            case R.id.My_del:
                //删除数据库
                myDao.delete();
                //删除输入历史
                My_History.removeChildView();
                //对数据集合进行清空
                mHistory.clear();
                break;
            case R.id.My_Btn_Back:
                getActivity().finish();
                break;
        }
    }
}
