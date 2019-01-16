package soexample.bigfly.com.e_shop.ipresenter;

import java.util.HashMap;

import soexample.bigfly.com.e_shop.callback.MyCallBack;
import soexample.bigfly.com.e_shop.moudle.MyMoudleImplGet;
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

public class IPresenterImplGetRequest implements IPresenter {
    private IView3 iView3;
    private MyMoudleImplGetRequest myMoudle;

    public IPresenterImplGetRequest(IView3 iView3) {
        this.iView3 = iView3;
        myMoudle = new MyMoudleImplGetRequest();
    }

    @Override
    public void startLogin(String url, HashMap<String, String> map, final int type) {
        myMoudle.startRequest(url, map, type, new MyCallBack() {
            @Override
            public void setData(Object data) {
                switch (type) {
                    case 8:
                        iView3.success3(data);
                        break;
                    case 9:
                        iView3.success3(data);
                        break;
                    case 2:
                        iView3.success3(data);
                        break;
                    case 3:
                        iView3.success3(data);
                        break;


                }
            }

            @Override
            public void setError(Object error) {
                iView3.error3(error);
            }
        });
    }

    //防止内存泄漏
    public void onDatacth() {
        if (myMoudle != null) {
            myMoudle = null;
        }
        if (iView3 != null) {
            iView3 = null;
        }

    }
}
