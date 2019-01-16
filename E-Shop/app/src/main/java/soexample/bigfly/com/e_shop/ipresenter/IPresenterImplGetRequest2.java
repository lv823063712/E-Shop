package soexample.bigfly.com.e_shop.ipresenter;

import java.util.HashMap;

import soexample.bigfly.com.e_shop.callback.MyCallBack;
import soexample.bigfly.com.e_shop.moudle.MyMoudleImplGetRequest;
import soexample.bigfly.com.e_shop.view.IView;
import soexample.bigfly.com.e_shop.view.IView2;
import soexample.bigfly.com.e_shop.view.IView3;

/**
 * <p>文件描述：<p>
 * <p>作者：${lvf}<p>
 * <p>创建时间：2018/12/27<p>
 * <p>更改时间：2018/12/27<p>
 * <p>版本号：1<p>
 */

public class IPresenterImplGetRequest2 implements IPresenter {
    private IView iView;
    private MyMoudleImplGetRequest myMoudle;

    public IPresenterImplGetRequest2(IView iView) {
        this.iView = iView;
        myMoudle = new MyMoudleImplGetRequest();
    }


    @Override
    public void startLogin(String url, HashMap<String, String> map, final int type) {
        myMoudle.startRequest(url, map, type, new MyCallBack() {
            @Override
            public void setData(Object data) {
                switch (type) {
                    case 1:
                        iView.success(data);
                        break;



                }
            }

            @Override
            public void setError(Object error) {
                iView.error(error);
            }
        });
    }

    //防止内存泄漏
    public void onDatacth() {
        if (myMoudle != null) {
            myMoudle = null;
        }
        if (iView != null) {
            iView = null;
        }

    }
}
