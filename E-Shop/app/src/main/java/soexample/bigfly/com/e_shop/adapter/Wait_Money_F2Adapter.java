package soexample.bigfly.com.e_shop.adapter;

import android.content.Context;
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
import soexample.bigfly.com.e_shop.bean.WaitMoneyData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/1/13   21:23<p>
 * <p>更改时间：2019/1/13   21:23<p>
 * <p>版本号：1<p>
 */

public class Wait_Money_F2Adapter extends RecyclerView.Adapter<Wait_Money_F2Adapter.ViewHolder> {
    private List<WaitMoneyData.OrderListBean.DetailListBean> datas;
    private List<String> sss = new ArrayList<>();
    private Context context;

    public Wait_Money_F2Adapter(List<WaitMoneyData.OrderListBean.DetailListBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.wait_money2, viewGroup, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.MyWaitMoneyTitle.setText(datas.get(i).getCommodityName());
        viewHolder.MyWaitMoneyprice.setText("¥" + datas.get(i).getCommodityPrice());
        String images = datas.get(i).getCommodityPic();//得到图片集
        String[] split = images.split("\\,");//得到一个图片
        if (split != null) {
            for (int j = 0; j < split.length; j++) {
                sss.add(split[j]);
            }
            Glide.with(context).load(sss.get(0)).into(viewHolder.MyWaitMoneyImage);
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView MyWaitMoneyImage;
        private TextView MyWaitMoneyTitle;
        private TextView MyWaitMoneyprice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MyWaitMoneyImage = itemView.findViewById(R.id.MyWaitMoneyImage);
            MyWaitMoneyTitle = itemView.findViewById(R.id.MyWaitMoneyTitle);
            MyWaitMoneyprice = itemView.findViewById(R.id.MyWaitMoneyprice);
        }
    }

    public double price(int position) {
        double price = 0;
        for (int i = 0; i < datas.size(); i++) {
            int commodityPrice = datas.get(i).getCommodityPrice();
            price += commodityPrice;
        }
        return price;
    }

}
