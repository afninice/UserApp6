package afniramadania.tech.usergithubapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import afniramadania.tech.usergithubapp.Adapter.UserFavoriteAdapter;
import afniramadania.tech.usergithubapp.Database.FavoriteUserHelper;
import afniramadania.tech.usergithubapp.Model.UserModel;
import afniramadania.tech.usergithubapp.R;

public class UserFavoriteActivity extends AppCompatActivity {

    ArrayList<UserModel> list = new ArrayList<>();
    private FavoriteUserHelper favoriteHelper;
    private UserFavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favorite);

        favoriteHelper = FavoriteUserHelper.getInst(UserFavoriteActivity.this);
        favoriteHelper.open();
        RecyclerView rv = findViewById(R.id.rv_favorite);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserFavoriteAdapter();
        adapter.setOnItemClickCallback(new UserFavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(UserModel userModel) {
                showSelectedItem(userModel);
            }
        });
        rv.setAdapter(adapter);
    }

    private void showSelectedItem(UserModel userResponse) {
        Intent intent = new Intent(UserFavoriteActivity.this, UserDetailActivity.class);
        intent.putExtra("ID", userResponse.getLogin());
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        list = favoriteHelper.getAllFavorites();
        adapter.setData(list);
        Log.d("cekkkkk", String.valueOf(list));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        movieHelper.close();
    }
}
