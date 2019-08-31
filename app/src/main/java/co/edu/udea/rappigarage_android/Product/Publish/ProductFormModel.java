package co.edu.udea.rappigarage_android.Product.Publish;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.GlobalServices.Category.CategoryService;
import co.edu.udea.rappigarage_android.GlobalServices.Category.ICategoryImplement;
import co.edu.udea.rappigarage_android.Product.Publish.API.IProductFormService;
import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels.Photo;
import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels.Result;
import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoSource;
import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import co.edu.udea.rappigarage_android.Product.Publish.API.ProductResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import static okhttp3.MediaType.*;

public class ProductFormModel implements IProductForm.IInteractor, ICategoryImplement.CompleteListener {

    private IProductForm.CompleteListenerCategories completeListenerCategories;
    private CategoryService categoryService;
    private IProductForm.IPresenter presenter;
    private ArrayList<String> photosNames = new ArrayList<>();
    private static String BASE_URL ="https://apideveloprappigarage.herokuapp.com/api/ImageContainers/product-photo-container/download/";


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://apideveloprappigarage.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    IProductFormService iProductFormService =  retrofit.create(IProductFormService.class);



    public ProductFormModel(IProductForm.CompleteListenerCategories completeListenerCategories,IProductForm.IPresenter presenter) {
        this.completeListenerCategories = completeListenerCategories;
        categoryService = new CategoryService(this);
        this.presenter = presenter;
    }

    @Override
    public void getCategories() {
        categoryService.getCategories();
    }

    @Override
    public void publishProduct(Product product, ArrayList<String> uriImages) {


        List<MultipartBody.Part> photosParts = new ArrayList<>();

        for (int i = 0; i<uriImages.size();i++) {
            photosParts.add(namePart(uriImages.get(i)));
        }



        Call<Result> call = iProductFormService.uploadPhoto(photosParts);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    System.out.println("RESPONSE" + response.body().getFiles());

                }else{

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                presenter.onErrorPublish(t.getMessage());
            }
        });

    }


    public void publishPhotos(ArrayList<String> uriImages) {




    }



    public MultipartBody.Part namePart(String pathFile){
        File file = null;
        MultipartBody.Part requestImage = null;
        if(file == null){
            file = new File(pathFile);
        }
        RequestBody requesFile = (RequestBody) RequestBody.create(MediaType.parse("multipart/form-data"),file);
        requestImage =MultipartBody.Part.createFormData("photos",file.getName(),requesFile);

       return  requestImage;
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
