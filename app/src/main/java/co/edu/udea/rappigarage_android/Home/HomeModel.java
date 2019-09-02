package co.edu.udea.rappigarage_android.Home;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.GlobalServices.Category.CategoryService;
import co.edu.udea.rappigarage_android.GlobalServices.Category.ICategoryImplement;

public class HomeModel implements  IHome.IInteractor , ICategoryImplement.CompleteListener {

    private IHome.CompleteListenerCategories completeListenerCategories;
    private CategoryService categoryService;
    private IHome.IPresenter presenter;


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
    public void getProducts(int first, int limit) {
        
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
