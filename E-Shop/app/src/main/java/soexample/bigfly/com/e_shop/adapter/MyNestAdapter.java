package soexample.bigfly.com.e_shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.bean.OrderAllListData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/1/14   19:27<p>
 * <p>更改时间：2019/1/14   19:27<p>
 * <p>版本号：1<p>
 */

public class MyNestAdapter extends RecyclerView.Adapter<MyNestAdapter.ViewHolder> {
    private List<OrderAllListData.OrderListBean> lists;
    private Context context;

    public MyNestAdapter(List<OrderAllListData.OrderListBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.nest_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.My_Order_Number_Nest.setText(lists.get(i).getOrderId());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        viewHolder.My_All_List_Recy_Nest.setLayoutManager(manager);
        List<OrderAllListData.OrderListBean.DetailListBean> detailList = lists.get(i).getDetailList();
        OrderAllListAdapter orderAllListAdapter = new OrderAllListAdapter(detailList, context);
        viewHolder.My_All_List_Recy_Nest.setAdapter(orderAllListAdapter);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView My_Order_Number_Nest;
        private RecyclerView My_All_List_Recy_Nest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            My_Order_Number_Nest = itemView.findViewById(R.id.My_Order_Number_Nest);
            My_All_List_Recy_Nest = itemView.findViewById(R.id.My_All_List_Recy_Nest);


        }
    }
}
