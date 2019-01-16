package soexample.bigfly.com.e_shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.bean.WaitMoneyData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/1/14   19:27<p>
 * <p>更改时间：2019/1/14   19:27<p>
 * <p>版本号：1<p>
 */

public class WaitMoneryAdapter_F extends RecyclerView.Adapter<WaitMoneryAdapter_F.ViewHolder> {
    private List<WaitMoneyData.OrderListBean> lists;
    private Context context;
    private List<WaitMoneyData.OrderListBean.DetailListBean> detailList;

    public WaitMoneryAdapter_F(List<WaitMoneyData.OrderListBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.wait_money_item1, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.My_Wait_Money_Nest.setText(lists.get(i).getOrderId());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        viewHolder.My_Wait_Money_Recy_Nest.setLayoutManager(manager);
        detailList = lists.get(i).getDetailList();
        Wait_Money_F2Adapter wait_money_f2Adapter = new Wait_Money_F2Adapter(detailList, context);
        viewHolder.My_Wait_Money_Recy_Nest.setAdapter(wait_money_f2Adapter);

        final double price = wait_money_f2Adapter.price(i);
        viewHolder.My_Wait_Money_count.setText("需支付" + price + "元");
        viewHolder.QXDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.QZF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitMoneryCallBack.QuZhiFu(lists.get(i).getOrderId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView My_Wait_Money_Nest;
        private TextView My_Wait_Money_Date;
        private RecyclerView My_Wait_Money_Recy_Nest;
        private TextView My_Wait_Money_count;
        private TextView QXDD;
        private TextView QZF;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            My_Wait_Money_Nest = itemView.findViewById(R.id.My_Wait_Money_Nest);
            My_Wait_Money_Recy_Nest = itemView.findViewById(R.id.My_Wait_Money_Recy_Nest);
            My_Wait_Money_count = itemView.findViewById(R.id.My_Wait_Money_count);
            QXDD = itemView.findViewById(R.id.QXDD);
            QZF = itemView.findViewById(R.id.QZF);

        }
    }


    public interface WaitMoneryCallBack {
        void QuZhiFu(String orderId);
    }

    private WaitMoneryCallBack waitMoneryCallBack;

    //定义一个回调接口
    public void setCallBack(WaitMoneryCallBack callBack) {
        this.waitMoneryCallBack = callBack;

    }
}
