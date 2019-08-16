package co.edu.udea.rappigarage_android.Product.Publish;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.GlobalServices.Category.ICategoryImplement;
import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels.Result;
import co.edu.udea.rappigarage_android.Product.Publish.API.Product;
import co.edu.udea.rappigarage_android.Product.Publish.API.ProductResponse;

public class ProductFormPresenter implements IProductForm.IPresenter ,IProductForm.CompleteListenerCategories {

    IProductForm.IView view;
    IProductForm.IInteractor interactor;

    public ProductFormPresenter(IProductForm.IView view) {
        this.view = view;
        interactor = new ProductFormModel(this,this);

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
    public void publishProduct(Product product) {
        view.displayLoader(true);
        interactor.publishProduct(product);
    }

    @Override
    public void publishPhotos(ArrayList<String> urisPhotos) {
        view.displayLoader(true);
        this.interactor.publishPhotos(urisPhotos);
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

    @Override
    public void onSuccessPublish(ProductResponse productResponse) {
        view.displayLoader(false);
        view.publishProductResponse(productResponse);
    }

    @Override
    public void onErrorPublish(String error) {
        view.displayLoader(false);
        view.displayError(error);
    }

    @Override
    public void onSuccessPublishPhotos(Result result) {
        view.displayLoader(false);
        view.displaySuccesFull("Loading photo succesful");
    }

    @Override
    public void onErrorPublishPhotos(String error) {
        view.displayLoader(false);
        view.displayError(error);
    }
}
