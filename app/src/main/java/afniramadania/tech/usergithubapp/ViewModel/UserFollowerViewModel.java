package afniramadania.tech.usergithubapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import afniramadania.tech.usergithubapp.Api.Client;
import afniramadania.tech.usergithubapp.Api.Service;
import afniramadania.tech.usergithubapp.Model.FollowerModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFollowerViewModel extends ViewModel {

    private MutableLiveData<ArrayList<FollowerModel>> Userdata;
    public void loadEvent(String username) {
        try {
            Service service = Client.getClient().create(Service.class);
            Call<ArrayList<FollowerModel>> eventCall = service.Follower("token 4f459e93351c51201e2fe82d587817ec0ae0c5cd",username);
            eventCall.enqueue(new Callback<ArrayList<FollowerModel>>() {

                private Response<ArrayList<FollowerModel>>response;

                @Override
                public void onResponse(Call<ArrayList<FollowerModel>> call, Response<ArrayList<FollowerModel>> response) {
                    this.response = response;
                    Userdata.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<FollowerModel>> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }
    public LiveData<ArrayList<FollowerModel>> getData() {
        if (Userdata == null) {

            Userdata = new MutableLiveData<>();

        }
        return Userdata;
    }

}
