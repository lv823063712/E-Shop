package soexample.bigfly.com.e_shop.view;

/**
 * <p>文件描述：<p>
 * <p>作者：${lvf}<p>
 * <p>创建时间：2018/12/27<p>
 * <p>更改时间：2018/12/27<p>
 * <p>版本号：1<p>
 */

public interface IView4<T> {
    //接口成功回调
    void success4(T data);

    //借口失败回调
    void error4(T error);
}
