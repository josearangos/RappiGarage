package co.edu.udea.rappigarage_android.Product.Publish;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.GlobalServices.Category.CategoryService;
import co.edu.udea.rappigarage_android.GlobalServices.Category.ICategoryImplement;
import co.edu.udea.rappigarage_android.Product.Publish.API.IProductFormService;
import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import co.edu.udea.rappigarage_android.Product.Publish.API.ProductResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductFormModel implements IProductForm.IInteractor, ICategoryImplement.CompleteListener {

    private IProductForm.CompleteListenerCategories completeListenerCategories;
    private CategoryService categoryService;
    private IProductForm.CompleteListenerPublish presenter;




    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://apideveloprappigarage.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    IProductFormService iProductFormService =  retrofit.create(IProductFormService.class);



    public ProductFormModel(IProductForm.CompleteListenerCategories completeListenerCategories,IProductForm.CompleteListenerPublish presenter) {
        this.completeListenerCategories = completeListenerCategories;
        categoryService = new CategoryService(this);
        this.presenter = presenter;
    }

    @Override
    public void getCategories() {
        categoryService.getCategories();
    }

    @Override
    public void publishProduct(Product product) {
        System.out.println("Productttt"+product.getName());
        Call<ProductResponse> call = iProductFormService.publishProduct(product);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println("Error: "+String.valueOf(response.code()));
                    return;
                }
                presenter.onSuccessPublish(response.body());

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                presenter.onErrorPublish(t.getMessage());
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
