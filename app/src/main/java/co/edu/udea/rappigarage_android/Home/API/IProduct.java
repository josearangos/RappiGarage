package co.edu.udea.rappigarage_android.Home.API;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IProduct {

    //Products/search?query=camisa&skip=4&limit=4
    @GET("Products/search")
    Call<ProductSummary> getProductsforQuery(@Query("query") String keyWord,@Query("skip") int first,@Query("limit") int limit);



}
