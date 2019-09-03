package co.edu.udea.rappigarage_android.Home;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.Home.API.ProductSummary;
import co.edu.udea.rappigarage_android.Home.API.Search;

public class HomePresenter implements  IHome.IPresenter, IHome.CompleteListenerCategories{

    IHome.IView view;
    IHome.IInteractor interactor;


    public HomePresenter(IHome.IView view) {
        this.view = view;
        interactor = new HomeModel(this,this);
    }

    @Override
    public void onDestroy() {
        view=null;
    }

    @Override
    public void getCategories() {
        if(view !=null){
            view.displayLoader(true);
            interactor.getCategories();
        }
    }

    @Override
    public void getProducts(String query, int first, int limit) {
        if(view !=null){
            view.displayLoader(true);
            interactor.getProducts(query,first,limit);
        }

    }

    @Override
    public void onSuccessCategories(ArrayList<Category> categories) {
        view.displayLoader(false);
        view.displayCategories(categories);
    }

    @Override
    public void onErrorCategories(String error) {
        view.displayLoader(false);
        view.displayError(error);
    }

    @Override
    public void onSuccessSearch(List<Search> productSummaries) {
        view.displayLoader(false);
        view.displayProducts(productSummaries);
    }

    @Override
    public void onErrorSearch(String error) {
        view.displayLoader(false);
        view.displayError(error);

    }
}
