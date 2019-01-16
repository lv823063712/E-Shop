package soexample.bigfly.com.e_shop.moudle;

import java.util.HashMap;

import soexample.bigfly.com.e_shop.callback.MyCallBack;

/**
 * <p>文件描述：moudle层<p>
 * <p>作者：${lvf}<p>
 * <p>创建时间：2018/12/27<p>
 * <p>更改时间：2018/12/27<p>
 * <p>版本号：1<p>
 */

public interface MyMoudleGet {
    void startRequest(String url,int type, MyCallBack myCallBack);
}
