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
import afniramadania.tech.usergithubapp.Adapter.UserFollowingAdapter;
import afniramadania.tech.usergithubapp.Model.FollowingModel;
import afniramadania.tech.usergithubapp.R;
import afniramadania.tech.usergithubapp.ViewModel.UserFollowerViewModel;
import afniramadania.tech.usergithubapp.ViewModel.UserFollowingViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFollowingFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserFollowingViewModel followingViewModel;
    private UserFollowingAdapter adapterItem;
    UserFollowingFragment followingFragment;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    ArrayList<FollowingModel> followingResponsess = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_following, container, false);
        recyclerView = v.findViewById(R.id.rv_following);
        followingViewModel = ViewModelProviders.of(this).get(UserFollowingViewModel.class);
        adapterItem = new UserFollowingAdapter();
        adapterItem.setOnItemClickCallback(new UserFollowingAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(FollowingModel followingResponse) {
                showSelectedItem(followingResponse);
            }
        });

        initRV();
        getData();

        return v;
    }

    private void showSelectedItem(FollowingModel item) {
        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra("ID", item.getLogin());
        startActivity(intent);
    }

    private void getData() {
        followingViewModel.loadEvent(UserDetailActivity.currentuser);
        followingViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<FollowingModel>>() {
            @Override
            public void onChanged(ArrayList<FollowingModel> followingResponses) {

                adapterItem.setData(followingResponsess);
                followingResponsess.addAll(followingResponses);
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
        followingViewModel.loadEvent(UserDetailActivity.currentuser);

    }
}
