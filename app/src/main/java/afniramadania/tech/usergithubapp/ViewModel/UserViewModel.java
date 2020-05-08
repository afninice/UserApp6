package afniramadania.tech.usergithubapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import afniramadania.tech.usergithubapp.Api.Client;
import afniramadania.tech.usergithubapp.Api.Service;
import afniramadania.tech.usergithubapp.Model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private MutableLiveData<UserModel> Userdata;
    public void loadEvent(String username) {
        try {
            Service service = Client.getClient().create(Service.class);
            Call<UserModel> eventCall = service.detailUser("token 4f459e93351c51201e2fe82d587817ec0ae0c5cd",username);
            eventCall.enqueue(new Callback<UserModel>() {

                private Response<UserModel> response;

                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    this.response = response;
                    Userdata.setValue(response.body());
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }
    public LiveData<UserModel> getData() {
        if (Userdata == null) {

            Userdata = new MutableLiveData<>();

        }
        return Userdata;
    }
    
        
}
