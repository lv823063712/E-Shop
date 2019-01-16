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

import java.text.SimpleDateFormat;
import java.util.List;

import soexample.bigfly.com.e_shop.R;
import soexample.bigfly.com.e_shop.bean.CommentData;

/**
 * <p>文件描述：<p>
 * <p>作者：${吕飞}<p>
 * <p>创建时间：2019/1/8   19:10<p>
 * <p>更改时间：2019/1/8   19:10<p>
 * <p>版本号：1<p>
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<CommentData.ResultBean> datas;
    private Context context;
    private View inflate;


    public CommentAdapter(List<CommentData.ResultBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        inflate = LayoutInflater.from(context).inflate(R.layout.comment_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.My_Comment_Name.setText(datas.get(i).getNickName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(datas.get(i).getCreateTime());
        viewHolder.My_Comment_Time.setText(format + "");
        viewHolder.My_Comment_Content.setText(datas.get(i).getContent());
        Glide.with(context).load(datas.get(i).getHeadPic()).into(viewHolder.My_Comment_Photo);
        String image = datas.get(i).getImage();
        String[] split = image.split("\\,");
        if (split != null) {
            for (int j = 0; j < split.length; j++) {
                String replace = split[j].replace("https", "http");
                Glide.with(context).load(replace).into(viewHolder.img3);
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView My_Comment_Photo;
        private TextView My_Comment_Name;
        private TextView My_Comment_Time;
        private TextView My_Comment_Content;
        private ImageView img3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            My_Comment_Photo = itemView.findViewById(R.id.My_Comment_Photo);
            My_Comment_Name = itemView.findViewById(R.id.My_Comment_Name);
            My_Comment_Time = itemView.findViewById(R.id.My_Comment_Time);
            My_Comment_Content = itemView.findViewById(R.id.My_Comment_Content);
            img3 = itemView.findViewById(R.id.img3);
        }
    }
}
