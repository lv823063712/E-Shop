package soexample.bigfly.com.e_shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import soexample.bigfly.com.e_shop.bean.WalletData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/1/6   21:37<p>
 * <p>更改时间：2019/1/6   21:37<p>
 * <p>版本号：1<p>
 */

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private ArrayList<WalletData.ResultBean> datas;
    private Context context;

    public WalletAdapter(ArrayList<WalletData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
