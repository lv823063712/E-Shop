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

public class WaitReceiveAdapter_F extends RecyclerView.Adapter<WaitReceiveAdapter_F.ViewHolder> {
    private List<WaitMoneyData.OrderListBean> lists;
    private Context context;



    public WaitReceiveAdapter_F(List<WaitMoneyData.OrderListBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.wait_receive_item1, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.My_Wait_Recy_Nest.setText(lists.get(i).getOrderId());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        viewHolder.My_Wait_Recy_Recy.setLayoutManager(manager);
        List<WaitMoneyData.OrderListBean.DetailListBean> detailList = lists.get(i).getDetailList();
        Wait_Receive_F2Adapter wait_receive_f2Adapter = new Wait_Receive_F2Adapter(detailList, context);
        viewHolder.My_Wait_Recy_Recy.setAdapter(wait_receive_f2Adapter);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView My_Wait_Recy_Nest;
        private TextView My_Wait_Recy_Date;
        private RecyclerView My_Wait_Recy_Recy;
        private TextView My_Fast_Mail;
        private TextView My_Express_number;
        private TextView QDSH;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            My_Wait_Recy_Nest = itemView.findViewById(R.id.My_Wait_Recy_Nest);
            My_Wait_Recy_Date = itemView.findViewById(R.id.My_Wait_Recy_Date);
            My_Wait_Recy_Recy = itemView.findViewById(R.id.My_Wait_Recy_Recy);
            My_Fast_Mail = itemView.findViewById(R.id.My_Fast_Mail);
            My_Express_number = itemView.findViewById(R.id.My_Express_number);
            QDSH = itemView.findViewById(R.id.QDSH);



        }
    }
}
