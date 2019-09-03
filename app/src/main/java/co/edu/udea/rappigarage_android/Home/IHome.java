package co.edu.udea.rappigarage_android.Home;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.Home.API.Search;

public interface IHome {

    interface IView {
        void displayLoader(boolean loader);
        void initializeViews(View view );
        void displayCategories(ArrayList<Category> categories);
        void displayProducts(List<Search> productSummaries);
        void displayError(String error);
        void displaySuccesFull(String ms);
    }


    interface IPresenter {
        void onDestroy();
        void getCategories();
        void getProducts(String query , int first, int limit);
        void onSuccessSearch(List<Search> productSummaries);
        void onErrorSearch(String error);
    }


    interface IInteractor{
        void getCategories();
        void getProducts(String query , int first, int limit);

    }



    interface CompleteListenerCategories {
        void onSuccessCategories(ArrayList<Category> categories);
        void onErrorCategories(String error);

    }
}
