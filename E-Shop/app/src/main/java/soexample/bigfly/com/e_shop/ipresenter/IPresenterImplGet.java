package soexample.bigfly.com.e_shop.ipresenter;

import java.util.HashMap;

import soexample.bigfly.com.e_shop.callback.MyCallBack;
import soexample.bigfly.com.e_shop.moudle.MyMoudleImpl;
import soexample.bigfly.com.e_shop.moudle.MyMoudleImplGet;
import soexample.bigfly.com.e_shop.view.IView;
import soexample.bigfly.com.e_shop.view.IView2;

/**
 * <p>文件描述：<p>
 * <p>作者：${lvf}<p>
 * <p>创建时间：2018/12/27<p>
 * <p>更改时间：2018/12/27<p>
 * <p>版本号：1<p>
 */

public class IPresenterImplGet implements IPresenter {
    private IView iView;
    private MyMoudleImplGet myMoudle;

    public IPresenterImplGet(IView iView) {
        this.iView = iView;
        myMoudle = new MyMoudleImplGet();
    }


    @Override
    public void startLogin(String url, HashMap<String, String> map, final int type) {
        myMoudle.startRequest(url, type, new MyCallBack() {
            @Override
            public void setData(Object data) {
                switch (type) {
                    case 1:
                        iView.success(data);
                        break;
                    case 2:
                        iView.success(data);
                        break;
                    case 3:
                        iView.success(data);
                        break;
                    case 4:
                        iView.success(data);
                        break;
                    case 5:
                        iView.success(data);
                        break;
                    case 6:
                        iView.success(data);
                        break;
                    case 7:
                        iView.success(data);
                        break;
                    case 8:
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
