package co.edu.udea.rappigarage_android.GlobalServices.Category;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICategoryAPI {
    @GET("Categories")
    Call<ArrayList<Category>> getCategories();

}
