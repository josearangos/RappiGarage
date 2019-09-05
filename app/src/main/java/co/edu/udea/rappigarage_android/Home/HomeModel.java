package co.edu.udea.rappigarage_android.Home;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.APIClient;
import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.GlobalServices.Category.CategoryService;
import co.edu.udea.rappigarage_android.GlobalServices.Category.ICategoryImplement;
import co.edu.udea.rappigarage_android.Home.API.IProduct;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeModel implements  IHome.IInteractor , ICategoryImplement.CompleteListener {

    private IHome.CompleteListenerCategories completeListenerCategories;
    private CategoryService categoryService;
    private IHome.IPresenter presenter;
    private ArrayList<ProductSummary> productSummaries;
    private IProduct apiInterface;

    public HomeModel(IHome.CompleteListenerCategories completeListenerCategories,IHome.IPresenter presenter) {
        this.completeListenerCategories = completeListenerCategories;
        this.categoryService =  new CategoryService(this);
        this.presenter =presenter;
    }

    @Override
    public void getCategories() {
        categoryService.getCategories();

    }

    @Override
    public void getProducts(String query, int first, int limit) {
        apiInterface = APIClient.getApiClient().create(IProduct.class);
        Call<ProductSummary> call = apiInterface.getProductsforQuery(query,first,limit);

        call.enqueue(new Callback<ProductSummary>() {
            @Override
            public void onResponse(Call<ProductSummary> call, Response<ProductSummary> response) {
                if(response.isSuccessful()){
                    presenter.onSuccessSearch(response.body().getSearch());
                }else{
                    presenter.onErrorSearch(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductSummary> call, Throwable t) {
                presenter.onErrorSearch(t.getMessage());

            }
        });
    }

    @Override
    public void onSuccess(ArrayList<Category> categories) {
        this.completeListenerCategories.onSuccessCategories(categories);

    }

    @Override
    public void onError(String error) {
        this.completeListenerCategories.onErrorCategories(error);
    }
}
