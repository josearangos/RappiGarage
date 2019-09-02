package co.edu.udea.rappigarage_android.Home;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;

public class HomePresenter implements  IHome.IPresenter, IHome.CompleteListenerCategories{

    IHome.IView view;
    IHome.IInteractor interactor;


    public HomePresenter(IHome.IView view) {
        this.view = view;
        interactor = new HomeModel(this,this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getCategories() {
        if(view !=null){
            view.displayLoader(true);
            interactor.getCategories();
        }
    }

    @Override
    public void getProducts(int first, int limit) {

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
}
