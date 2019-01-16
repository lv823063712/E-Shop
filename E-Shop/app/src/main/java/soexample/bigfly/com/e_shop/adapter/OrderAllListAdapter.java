package soexample.bigfly.com.e_shop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.bean.OrderAllListData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/1/13   21:23<p>
 * <p>更改时间：2019/1/13   21:23<p>
 * <p>版本号：1<p>
 */

public class OrderAllListAdapter extends RecyclerView.Adapter<OrderAllListAdapter.ViewHolder> {
    private List<OrderAllListData.OrderListBean.DetailListBean> datas;
    private List<String> sss = new ArrayList<>();
    private Context context;

    public OrderAllListAdapter(List<OrderAllListData.OrderListBean.DetailListBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.orderalllist_item, viewGroup, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.MyTitle.setText(datas.get(i).getCommodityName());
        viewHolder.Myorderprice.setText("¥" + datas.get(i).getCommodityPrice());
        String images = datas.get(i).getCommodityPic();//得到图片集
        String[] split = images.split("\\,");//得到一个图片
        if (split != null) {
            for (int j = 0; j < split.length; j++) {
                sss.add(split[j]);
            }
            Glide.with(context).load(sss.get(0)).into(viewHolder.MygoodsImage);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView MygoodsImage;
        private TextView MyTitle;
        private TextView Myorderprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MygoodsImage = itemView.findViewById(R.id.MygoodsImage);
            MyTitle = itemView.findViewById(R.id.MyTitle);
            Myorderprice = itemView.findViewById(R.id.Myorderprice);
        }
    }
}
