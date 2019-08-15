package co.edu.udea.rappigarage_android.Product.Publish.API;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IProductFormService  {


    @POST("Products")
    Call<ProductResponse> publishProduct(@Body Product body);

}
