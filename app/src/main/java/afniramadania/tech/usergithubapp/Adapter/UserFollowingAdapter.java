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

import afniramadania.tech.usergithubapp.Model.FollowingModel;
import afniramadania.tech.usergithubapp.R;

public class UserFollowingAdapter extends RecyclerView.Adapter<UserFollowingAdapter.ViewHolder> {

    private ArrayList<FollowingModel> data = new ArrayList<>();
    private UserFollowingAdapter.OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<FollowingModel> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(UserFollowingAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public UserFollowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent,false);
        return new UserFollowingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFollowingAdapter.ViewHolder holder, int position) {
        FollowingModel item = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .apply(new RequestOptions().override(100, 100))
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

        public ViewHolder(@NonNull View v) {
            super(v);
            nama = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);

        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(FollowingModel FollowingModel);
    }

}
