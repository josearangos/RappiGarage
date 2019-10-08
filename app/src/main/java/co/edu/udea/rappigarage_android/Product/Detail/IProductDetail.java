package co.edu.udea.rappigarage_android.Product.Detail;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;

public interface IProductDetail {



    interface IView {
        void displayLoader(boolean loader);
        void initializeViews();
        void getCategories();
        void displayError(String error);
        //void publishProduct();
       // void publishProductResponse(ProductResponse productResponse);
        void displaySuccesFull(String ms);
    }

    interface IPresenter {
        void onDestroy();
        void getCategories();
        //void publishProduct(Product product,ArrayList<String> urisPhotos);
        //void onSuccessPublish(ProductResponse productResponse);
        void onErrorPublish(String error);
    }

    interface IInteractor{
        void getCategories();
        //void publishProduct(Product product,ArrayList<String> urisPhotos);

    }


    interface CompleteListenerCategories {
        void onSuccessCategories(ArrayList<Category> categories);
        void onErrorCategories(String error);
    }


}
