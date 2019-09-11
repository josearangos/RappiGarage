package co.edu.udea.rappigarage_android.Product.Detail;

import co.edu.udea.rappigarage_android.User.RappiUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IProductSeller {
    @GET
    Call<RappiUser> getRappiUserInfo(@Url String url);
}
