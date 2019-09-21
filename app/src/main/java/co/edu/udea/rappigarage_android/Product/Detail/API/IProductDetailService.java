package co.edu.udea.rappigarage_android.Product.Detail.API;

import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IProductDetailService {

    @GET
    Call<Product> getProductById(@Url String url);


}
