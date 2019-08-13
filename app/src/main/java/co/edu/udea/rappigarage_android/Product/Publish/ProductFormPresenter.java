package co.edu.udea.rappigarage_android.Product.Publish;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.GlobalServices.Category.ICategoryImplement;

public class ProductFormPresenter implements IProductForm.IPresenter ,IProductForm.CompleteListenerCategories {

    IProductForm.IView view;
    IProductForm.IInteractor interactor;

    public ProductFormPresenter(IProductForm.IView view) {
        this.view = view;
        interactor = new ProductFormModel(this);

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
    public void onSuccessCategories(ArrayList<Category> categories) {
        view.displayLoader(false);
        view.getCategories(categories);
    }

    @Override
    public void onErrorCategories(String error) {
        view.displayLoader(false);
        view.displayError(error);
    }
}
