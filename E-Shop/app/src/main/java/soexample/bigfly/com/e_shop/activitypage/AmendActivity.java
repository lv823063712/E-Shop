package soexample.bigfly.com.e_shop.activitypage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import soexample.bigfly.com.e_shop.R;

public class AmendActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText My_Updata_NiCheng;
    private EditText My_Updata_Pwd;
    private Button My_Updata_Confirm;
    private SharedPreferences shared;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_amend);
        initView();
        shared = getSharedPreferences("user", Context.MODE_PRIVATE);
        edit = shared.edit();

    }

    private void initView() {
        My_Updata_NiCheng = (EditText) findViewById(R.id.My_Updata_NiCheng);
        My_Updata_Pwd = (EditText) findViewById(R.id.My_Updata_Pwd);
        My_Updata_Confirm = (Button) findViewById(R.id.My_Updata_Confirm);

        My_Updata_Confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Updata_Confirm:
                String my_updata_NiCheng = My_Updata_NiCheng.getText().toString().trim();
                String my_updata_Pwd = My_Updata_Pwd.getText().toString().trim();
                edit.putString("nickName", my_updata_NiCheng);
                edit.putString("pwd", my_updata_Pwd);
                edit.commit();
                Toast.makeText(this, "成功啦", Toast.LENGTH_SHORT).show();
                finish();

                break;
        }
    }

}
