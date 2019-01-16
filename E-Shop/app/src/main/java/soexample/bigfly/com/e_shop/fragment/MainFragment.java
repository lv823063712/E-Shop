package soexample.bigfly.com.e_shop.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.activitypage.MyPersonalActivity;
import soexample.bigfly.com.e_shop.activitypage.WalletActivity;
import soexample.bigfly.com.e_shop.address.AddressActivity;
import soexample.bigfly.com.e_shop.ipresenter.IPresenterImpl;
import soexample.bigfly.com.e_shop.view.IView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements IView {

    private View inflate;
    private String url = "http://172.17.8.100/small/user/verify/v1/modifyHeadPic";
    private IPresenterImpl iPresenter;
    private HashMap<String, String> map = new HashMap<>();
    private TextView Main_User_Name;
    private ImageView PersonalData;
    private LinearLayout My_personal_data;
    private LinearLayout My_my_circle;
    private LinearLayout My_My_footprint;
    private LinearLayout My_my_wallet;
    private LinearLayout My_shipping_address;
    private ImageView My_Head_Phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_main, null);
        initView(inflate);

        SharedPreferences user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int userId = user.getInt("userId", 0);
        String sessionId = user.getString("sessionId", "");
        String headPic = user.getString("headPic", "");
        Uri uri = Uri.parse(headPic);
        String nickName = user.getString("nickName", "");
        My_Head_Phone.setImageURI(uri);
        Main_User_Name.setText(nickName);

        iPresenter = new IPresenterImpl(this);
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        iPresenter.startLogin(url, map, 4);
        setListener();
        return inflate;
    }

    private void setListener() {
        My_my_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });

        My_personal_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPersonalActivity.class);

                startActivity(intent);
            }
        });
        My_shipping_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void error(Object error) {

    }

    private void initView(View inflate) {
        Main_User_Name = (TextView) inflate.findViewById(R.id.Main_User_Name);
        PersonalData = (ImageView) inflate.findViewById(R.id.PersonalData);
        My_personal_data = (LinearLayout) inflate.findViewById(R.id.My_personal_data);
        My_my_circle = (LinearLayout) inflate.findViewById(R.id.My_my_circle);
        My_My_footprint = (LinearLayout) inflate.findViewById(R.id.My_My_footprint);
        My_my_wallet = (LinearLayout) inflate.findViewById(R.id.My_my_wallet);
        My_shipping_address = (LinearLayout) inflate.findViewById(R.id.My_shipping_address);
        My_Head_Phone = (ImageView) inflate.findViewById(R.id.My_Head_Phone);
    }
}
