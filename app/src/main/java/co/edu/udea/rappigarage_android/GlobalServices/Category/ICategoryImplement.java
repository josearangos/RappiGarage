package co.edu.udea.rappigarage_android.GlobalServices.Category;

import java.util.ArrayList;

public interface ICategoryImplement {

    void getCategories();

    interface CompleteListener {
        void onSuccess(ArrayList<Category> categories);
        void onError(String error);
    }


}
