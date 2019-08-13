package co.edu.udea.rappigarage_android.Product.Publish;

import android.view.View;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;

public interface IProductForm {


    interface IView {
        void displayLoader(boolean loader);
        void initializeViews();
        void getCategories(ArrayList<Category> categories);
        void displayError(String error);

    }

    interface IPresenter {
        void onDestroy();
        void getCategories();
    }

    interface IInteractor{
        void getCategories();
    }

    interface CompleteListenerCategories {
        void onSuccessCategories(ArrayList<Category> categories);
        void onErrorCategories(String error);
    }
}
