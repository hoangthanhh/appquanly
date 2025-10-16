package utt.cntt.httt.manager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import utt.cntt.httt.manager.R;
import utt.cntt.httt.manager.model.Item;
import utt.cntt.httt.manager.utils.Utils;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHolder> {
    List<Item> itemList;
    Context context;

    public ChiTietAdapter(Context context,List<Item> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);

        Log.d("ChiTietDonHangAdapter", "Sản phẩm: " + item.getTensp() + ", SL: " + item.getSoluong());
        Log.d("ChiTietDonHangAdapter", "Bind position " + position
                + " / tổng " + itemList.size());


        holder.txtten.setText(item.getTensp() + "");
        holder.txtsoluong.setText("Số lượng: " + item.getSoluong() + "");
//        Glide.with(context).load(item.getHinhanh()).into(holder.imagechitiet);
        if (item.getHinhanh().contains("http")) {
            Glide.with(context).load(item.getHinhanh().trim()).into(holder.imagechitiet);
        } else {
            String hinh = Utils.BASE_URL + "images/" + item.getHinhanh().trim();
            Glide.with(context).load(hinh).into(holder.imagechitiet);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagechitiet;
        TextView txtten, txtsoluong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechitiet = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
        }
    }
}
