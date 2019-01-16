package soexample.bigfly.com.e_shop.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //抽取基类调用,否则会变白板
        init();
    }

    //布局视图
    protected abstract int getLayout();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //点击事件监听
    protected abstract void setOnClick();

    //逻辑代码书写
    protected abstract void proLogic();

    //设定方法的执行顺序
    void init() {
        if (getLayout() != 0) {
            setContentView(getLayout());
            initView();
            initData();
            setOnClick();
            proLogic();
        }
    }
}
