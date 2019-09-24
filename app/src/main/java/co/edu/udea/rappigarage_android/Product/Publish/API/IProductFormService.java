package co.edu.udea.rappigarage_android.Product.Publish.API;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels.Result;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IProductFormService  {


    @POST("Products")
    Call<ProductResponse> publishProduct(@Body Product body);

    @Multipart
    @POST("ImageContainers/product-photo-container/upload")
    Call<Result> uploadPhoto (@Part List<MultipartBody.Part> photos );

    @POST("ProductXCategories/addCategories/")
    Call<AddCategoriesResponse> addCategories(@Body AddCategoriesBody addCategoriesBody);

}
