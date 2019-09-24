package co.edu.udea.rappigarage_android.User.API;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface UserService {
    @POST("auth")
    Call<Token> getVerificationCode(@Body PhoneNumber phoneNumber);
}