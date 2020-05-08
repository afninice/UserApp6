package afniramadania.tech.usergithubapp.Api;

import java.util.ArrayList;

import afniramadania.tech.usergithubapp.Model.FollowerModel;
import afniramadania.tech.usergithubapp.Model.FollowingModel;
import afniramadania.tech.usergithubapp.Model.SearchModel;
import afniramadania.tech.usergithubapp.Model.UserModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("users/{username}")
    Call<UserModel> detailUser(@Header("Authorization") String authorization,
                               @Path("username") String username
    );
    @GET("/search/users")
    Call<SearchModel> searchUser(@Header("Authorization") String authorization,
                                 @Query("q") String username);

    @GET("users/{username}/followers")
    Call<ArrayList<FollowerModel>> Follower(@Header("Authorization") String authorization,
                                            @Path("username") String username);

    @GET("users/{username}/following")
    Call<ArrayList<FollowingModel>> Following(@Header("Authorization") String authorization,
                                              @Path("username") String username);

}
