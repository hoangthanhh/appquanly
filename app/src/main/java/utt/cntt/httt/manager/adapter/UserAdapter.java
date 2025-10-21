package utt.cntt.httt.manager.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import utt.cntt.httt.manager.Interface.ItemClickListener;
import utt.cntt.httt.manager.R;
import utt.cntt.httt.manager.activity.ChiTietActivity;
import utt.cntt.httt.manager.model.EventBus.SuaXoaEvent;
import utt.cntt.httt.manager.model.SanPhamMoi;
import utt.cntt.httt.manager.model.User;
import utt.cntt.httt.manager.utils.Utils;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{
    Context context;
    List<User> array;

    public UserAdapter(Context context, List<User> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tk, parent, false);
        return new UserAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        User user = array.get(position);

        holder.txtemail.setText("Email: " + user.getEmail());
        holder.txtpass.setText("Pass: " + user.getPass());
        holder.txtusername.setText("Username: " + user.getUsername());
        holder.txtmobile.setText("Mobile:" + user.getMobile());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (isLongClick) {
                    EventBus.getDefault().postSticky(new SuaXoaEvent(user));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView txtemail, txtpass, txtusername, txtmobile;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtemail = itemView.findViewById(R.id.itemtk_email);
            txtpass = itemView.findViewById(R.id.itemtk_pass);
            txtusername = itemView.findViewById(R.id.itemtk_username);
            txtmobile = itemView.findViewById(R.id.itemtk_mobile);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 0, getAdapterPosition(), "Sửa");
            menu.add(0, 1, getAdapterPosition(), "Xóa");
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}
