package afniramadania.tech.usergithubapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.Objects;

import afniramadania.tech.usergithubapp.Activity.UserDetailActivity;
import afniramadania.tech.usergithubapp.Activity.UserHomeActivity;
import afniramadania.tech.usergithubapp.Model.SearchDataModel;
import afniramadania.tech.usergithubapp.R;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {

    private ArrayList<SearchDataModel> data = new ArrayList<>();
    private UserHomeActivity homeActivity;


    public void setData(ArrayList<SearchDataModel> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    public UserSearchAdapter(UserHomeActivity homeActivity) {
        this.homeActivity = homeActivity;

    }



    @NonNull
    @Override
    public UserSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserSearchAdapter.ViewHolder holder, final int position) {
        SearchDataModel item = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .apply(new RequestOptions().override(120, 120))
                .into(holder.imageView);

        holder.nama.setText(item.getLogin());

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("UseRequireInsteadOfGet")
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        SearchDataModel SearchDataModel = data.get(position);
                        Intent intent = new Intent(homeActivity, UserDetailActivity.class);
                        intent.putExtra("ID", SearchDataModel.getLogin());
                        Objects.requireNonNull(homeActivity).startActivity(intent);
                    }
                }
            });


        }


    }
}