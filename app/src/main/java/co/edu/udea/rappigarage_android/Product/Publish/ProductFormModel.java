package co.edu.udea.rappigarage_android.Product.Publish;

import java.util.ArrayList;

import co.edu.udea.rappigarage_android.GlobalServices.Category.Category;
import co.edu.udea.rappigarage_android.GlobalServices.Category.CategoryService;
import co.edu.udea.rappigarage_android.GlobalServices.Category.ICategoryImplement;

public class ProductFormModel implements IProductForm.IInteractor, ICategoryImplement.CompleteListener {

    private IProductForm.CompleteListenerCategories completeListenerCategories;
    private CategoryService categoryService;

    public ProductFormModel(IProductForm.CompleteListenerCategories completeListenerCategories) {
        this.completeListenerCategories = completeListenerCategories;
        categoryService = new CategoryService(this);
    }

    @Override
    public void getCategories() {
        categoryService.getCategories();
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
