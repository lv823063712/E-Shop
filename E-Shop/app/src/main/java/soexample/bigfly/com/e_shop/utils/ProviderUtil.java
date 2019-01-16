package soexample.bigfly.com.e_shop.utils;

import android.content.Context;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/1/5   11:38<p>
 * <p>更改时间：2019/1/5   11:38<p>
 * <p>版本号：1<p>
 */

public class ProviderUtil {

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".provider";
    }
}
