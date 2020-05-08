package afniramadania.tech.usergithubapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import afniramadania.tech.usergithubapp.Activity.UserDetailActivity;
import afniramadania.tech.usergithubapp.Adapter.UserFollowerAdapter;
import afniramadania.tech.usergithubapp.Model.FollowerModel;
import afniramadania.tech.usergithubapp.R;
import afniramadania.tech.usergithubapp.ViewModel.UserFollowerViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFollowerFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserFollowerViewModel followerViewModel;
    private UserFollowerAdapter adapterItem;
    UserFollowerFragment followerFragment;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    ArrayList<FollowerModel> followerResponsess = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_follower, container, false);
        recyclerView = v.findViewById(R.id.rv_follower);
        followerViewModel = ViewModelProviders.of(this).get(UserFollowerViewModel.class);
        adapterItem = new UserFollowerAdapter();
        adapterItem.setOnItemClickCallback(new UserFollowerAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(FollowerModel followerResponse) {
                showSelectedItem(followerResponse);
            }
        });
        initRV();
        getData();

        return v;
    }



    private void getData() {
        followerViewModel.loadEvent(UserDetailActivity.currentuser);
        followerViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<FollowerModel>>() {
            @Override
            public void onChanged(ArrayList<FollowerModel> followerResponses) {
                adapterItem.setData(followerResponsess);
                followerResponsess.addAll(followerResponses);
                recyclerView.setAdapter(adapterItem);
                adapterItem.notifyDataSetChanged();
            }
        });
    }
    private void initRV() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public void onResume() {
        super.onResume();
        followerViewModel.loadEvent(UserDetailActivity.currentuser);

    }
    private void showSelectedItem(FollowerModel item) {
        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra("ID", item.getLogin());
        startActivity(intent);
    }
}
