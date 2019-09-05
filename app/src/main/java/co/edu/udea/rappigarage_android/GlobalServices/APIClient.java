package co.edu.udea.rappigarage_android.GlobalServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BASE_URL ="https://apideveloprappigarage.herokuapp.com/api/";
    public static Retrofit retrofit;
    public static Retrofit getApiClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        }

        return retrofit;
    }


}
