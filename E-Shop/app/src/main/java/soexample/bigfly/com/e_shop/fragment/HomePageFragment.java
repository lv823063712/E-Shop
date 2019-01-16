package soexample.bigfly.com.e_shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xw.repo.XEditText;


import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.activitypage.FluidLayoutActivity;
import soexample.bigfly.com.e_shop.fragmentsun.HomeFragmentSun;

public class HomePageFragment extends Fragment implements View.OnClickListener {

    private ImageView My_btn_menu;
    private XEditText my_search_edittext;
    private TextView My_search_Text;
    private FrameLayout My_Fragment_Sun;
    private FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, null);
        initView(v);
        return v;
    }

    private void initView(View v) {

        My_btn_menu = (ImageView) v.findViewById(R.id.My_btn_menu);
        my_search_edittext = (XEditText) v.findViewById(R.id.my_search_edittext);
        My_search_Text = (TextView) v.findViewById(R.id.My_search_Text);
        My_search_Text.setOnClickListener(this);
        My_Fragment_Sun = (FrameLayout) v.findViewById(R.id.My_Fragment_Sun);
        manager = getChildFragmentManager();
        manager.beginTransaction().replace(R.id.My_Fragment_Sun, new HomeFragmentSun()).commit();

        my_search_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FluidLayoutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
