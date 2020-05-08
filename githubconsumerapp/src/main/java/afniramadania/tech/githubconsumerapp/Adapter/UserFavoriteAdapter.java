package afniramadania.tech.githubconsumerapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import afniramadania.tech.githubconsumerapp.Model.UserModel;
import afniramadania.tech.githubconsumerapp.R;

public class UserFavoriteAdapter extends RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder> {

    private Context context;
    Cursor cursor;

    public UserFavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
    private UserModel getItems(int Position){
        if (!cursor.moveToPosition(Position)){
            throw new IllegalStateException("Invalid");
        }
        return new UserModel(cursor);
    }
    @NonNull
    @Override
    public UserFavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_list,viewGroup,false);
        return new UserFavoriteAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        UserModel user = getItems(i);
        holder.bind(user);
    }



    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);



        }

        public void bind(UserModel user) {
            Log.d("login",user.getLogin());
            nama.setText(user.getLogin());
            Glide.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .apply(new RequestOptions().override(120, 120))
                    .into(imageView);
        }
    }
}
