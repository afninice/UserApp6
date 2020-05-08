package afniramadania.tech.usergithubapp.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import afniramadania.tech.usergithubapp.Database.DatabaseContract;
import afniramadania.tech.usergithubapp.Database.FavoriteDatabaseHelper;
import afniramadania.tech.usergithubapp.Database.FavoriteUserHelper;
import afniramadania.tech.usergithubapp.Model.UserModel;
import afniramadania.tech.usergithubapp.R;
import afniramadania.tech.usergithubapp.ViewModel.UserViewModel;
import afniramadania.tech.usergithubapp.fragment.UserFollowerFragment;
import afniramadania.tech.usergithubapp.fragment.UserFollowingFragment;

import static afniramadania.tech.usergithubapp.Database.DatabaseContract.FavoriteColoumn.TABLE_FAVORITE_NAME;

public class UserDetailActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    public static String currentuser;

    ImageView imgProfile;
    TextView Name,Username,Location,Email,Website;

    private ViewPager viewpager;
    TabLayout tabLayout;

    private FavoriteUserHelper favoriteHelper ;
    UserModel user;
    ArrayList<UserModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Intent intent = getIntent();
        imgProfile = findViewById(R.id.img_profile);
        Name = findViewById(R.id.name_detail);
        Username = findViewById(R.id.username);
        Location = findViewById(R.id.location);
        Email = findViewById(R.id.email);
        Website = findViewById(R.id.website);
        final MaterialFavoriteButton favBtn = findViewById(R.id.fav_btn);
        favoriteHelper = FavoriteUserHelper.getInst(getApplicationContext());
        favoriteHelper.open();

        tabLayout = findViewById(R.id.navtab);
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new viewpageradapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        currentuser = intent.getStringExtra("ID");
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.loadEvent(currentuser);
        userViewModel.getData().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(@Nullable UserModel UserModel) {
                Name.setText(UserModel.getLogin());
                Username.setText(UserModel.getName());
                Location.setText(UserModel.getLocation());
                Email.setText(UserModel.getEmail());
                Website.setText(UserModel.getBlog());
                Glide.with(getApplicationContext())
                        .load(UserModel.getAvatarUrl())
                        .into(imgProfile);


                if (Exist(currentuser)){
                    favBtn.setFavorite(true);
                    favBtn.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite){
                                        list = favoriteHelper.getAllFavorites();
                                        favoriteHelper.favoriteInsert(UserModel);
                                        Toast toast = Toast.makeText(getApplicationContext(),"Add to Favorite",Toast.LENGTH_SHORT);
                                        toast.show();

                                    }
                                    else {
                                        list = favoriteHelper.getAllFavorites();
                                        favoriteHelper.favoriteDelete(currentuser);
                                        Toast toast = Toast.makeText(getApplicationContext(),"Delete to Favorite",Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            });

                }else {
                    favBtn.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite){
                                        list = favoriteHelper.getAllFavorites();
                                        favoriteHelper.favoriteInsert(UserModel);
                                        Toast toast = Toast.makeText(getApplicationContext(),"Add to Favorite",Toast.LENGTH_SHORT);
                                        toast.show();

                                    }
                                    else {
                                        list = favoriteHelper.getAllFavorites();
                                        favoriteHelper.favoriteDelete(currentuser);
                                        Toast toast = Toast.makeText(getApplicationContext(),"Delete to Favorite",Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            });
                }

            }
        });

    }
    public class viewpageradapter extends FragmentStatePagerAdapter {
        int mNumofTabs;
        @SuppressWarnings("deprecation")
        viewpageradapter(FragmentManager fragmentManager, int mNumOfTabs) {
            super(fragmentManager);
            this.mNumofTabs = mNumOfTabs;
        }

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new UserFollowerFragment();
                case 1:
                    return new UserFollowingFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumofTabs;
        }
    }
    public boolean Exist(String item) {
        String pilih = DatabaseContract.FavoriteColoumn.TITLE+" =?";
        String[] pilihArg = {item};
        String limit = "1";
        favoriteHelper = new FavoriteUserHelper(this);
        favoriteHelper.open();
        FavoriteDatabaseHelper dataBaseHelper = new FavoriteDatabaseHelper(UserDetailActivity.this);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_FAVORITE_NAME, null, pilih, pilihArg, null, null, null, limit);
        boolean exists;
        exists = (cursor.getCount() > 0);
        cursor.close();

        return exists;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
