package afniramadania.tech.usergithubapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import afniramadania.tech.usergithubapp.Model.UserModel;
import afniramadania.tech.usergithubapp.R;

public class UserFavoriteAdapter extends RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder> {

    private ArrayList<UserModel> data = new ArrayList<>();
    private UserFavoriteAdapter.OnItemClickCallback onItemClickCallback;


    public void setData(ArrayList<UserModel> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    public void setOnItemClickCallback(UserFavoriteAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public UserFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent,false);
        return new UserFavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFavoriteAdapter.ViewHolder holder, int position) {
        UserModel item = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imageView);

        holder.nama.setText(item.getLogin());
        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(data.get(holder.getAdapterPosition())));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);

        }

    }



    public interface OnItemClickCallback {
        void onItemClicked(UserModel userResponse);
    }

}
