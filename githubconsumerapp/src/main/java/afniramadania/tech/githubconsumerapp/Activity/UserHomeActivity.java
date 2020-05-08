package afniramadania.tech.githubconsumerapp.Activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import afniramadania.tech.githubconsumerapp.Adapter.UserFavoriteAdapter;
import afniramadania.tech.githubconsumerapp.R;

import static afniramadania.tech.githubconsumerapp.Database.DatabaseContract.FavoriteColoumn.FAVORITE_URI;

public class UserHomeActivity extends AppCompatActivity {

    UserFavoriteAdapter adapter;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        rv = findViewById(R.id.rv_favorite);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));



        new FAV().execute();
    }



    private class FAV extends AsyncTask<Void,Void, Cursor> {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return UserHomeActivity.this.getContentResolver().query(FAVORITE_URI,null,null,null,null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
//            Log.d("cursor", String.valueOf(cursor));
            adapter = new UserFavoriteAdapter(getApplicationContext());
            adapter.setCursor(cursor);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);

        }
    }
}
