package co.edu.udea.rappigarage_android.Product.Publish;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels.Result;
import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import co.edu.udea.rappigarage_android.Product.Publish.API.ProductResponse;

public interface IProductForm {


    interface IView {
        void displayLoader(boolean loader);
        void initializeViews();
        void getCategories(ArrayList<Category> categories);
        void displayError(String error);
        void publishProduct();
        void publishProductResponse(ProductResponse productResponse);
        void displaySuccesFull(String ms);
    }

    interface IPresenter {
        void onDestroy();
        void getCategories();
        void publishProduct(Product product, ArrayList<String> urisPhotos, List<Integer> categories);
        void onSuccessPublish(ProductResponse productResponse);
        void onErrorPublish(String error);
    }

    interface IInteractor{
        void getCategories();
        void publishProduct(Product product,ArrayList<String> urisPhotos,List<Integer> categories);

    }


    interface CompleteListenerCategories {
        void onSuccessCategories(ArrayList<Category> categories);
        void onErrorCategories(String error);
    }



}
