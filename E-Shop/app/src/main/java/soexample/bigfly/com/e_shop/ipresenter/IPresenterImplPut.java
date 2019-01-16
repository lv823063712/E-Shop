package soexample.bigfly.com.e_shop.ipresenter;

import java.util.HashMap;

import soexample.bigfly.com.e_shop.callback.MyCallBack;
import soexample.bigfly.com.e_shop.moudle.MyMoudleImpl;
import soexample.bigfly.com.e_shop.moudle.MyMoudlePut;
import soexample.bigfly.com.e_shop.view.IView;
import soexample.bigfly.com.e_shop.view.IView2;

/**
 * <p>文件描述：<p>
 * <p>作者：${lvf}<p>
 * <p>创建时间：2018/12/27<p>
 * <p>更改时间：2018/12/27<p>
 * <p>版本号：1<p>
 */

public class IPresenterImplPut implements IPresenter {
    private IView2 iView2;
    private MyMoudlePut myMoudle;

    public IPresenterImplPut(IView2 iView2) {
        this.iView2 = iView2;
        myMoudle = new MyMoudlePut();
    }


    @Override
    public void startLogin(String url, HashMap<String, String> map, final int type) {
        myMoudle.startRequest(url, map, type, new MyCallBack() {
            @Override
            public void setData(Object data) {
                switch (type) {
                    case 10:
                        iView2.success2(data);
                        break;
                    case 11:
                        iView2.success2(data);
                        break;
                    case 12:
                        iView2.success2(data);
                        break;
                }

            }

            @Override
            public void setError(Object error) {
                iView2.error2(error);
            }
        });
    }

    //防止内存泄漏
    public void onDatacth() {
        if (myMoudle != null) {
            myMoudle = null;
        }
        if (iView2 != null) {
            iView2 = null;
        }

    }
}
