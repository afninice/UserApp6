package afniramadania.tech.usergithubapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import afniramadania.tech.usergithubapp.Api.Client;
import afniramadania.tech.usergithubapp.Api.Service;
import afniramadania.tech.usergithubapp.Model.SearchDataModel;
import afniramadania.tech.usergithubapp.Model.SearchModel;
import afniramadania.tech.usergithubapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchViewModel extends ViewModel {

    private MutableLiveData<ArrayList<SearchDataModel>> Data = new MutableLiveData<>();

    public void loadEvent(String query) {
        try {
            String token = String.valueOf(R.string.auth);
            Service service = Client.getClient().create(Service.class);
            Call<SearchModel> eventCall = service.searchUser("token 4f459e93351c51201e2fe82d587817ec0ae0c5cd",query);
            eventCall.enqueue(new Callback<SearchModel>() {

                @Override
                public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                    if (!response.isSuccessful()) {
                        Log.d("On Response", response.message());
                    }
                    else if (response.body() != null) {
                        ArrayList<SearchDataModel> items = new ArrayList<>(response.body().getItems());
                        Data.postValue(items);
                    }

                }

                @Override
                public void onFailure(Call<SearchModel> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }
    public LiveData<ArrayList<SearchDataModel>> getData() {
        return Data;
    }
}
