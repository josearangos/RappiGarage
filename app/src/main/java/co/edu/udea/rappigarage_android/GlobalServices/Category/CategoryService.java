package co.edu.udea.rappigarage_android.GlobalServices.Category;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryService implements  ICategoryImplement {

    private  ICategoryImplement.CompleteListener listener;

    public CategoryService(CompleteListener listener) {
        this.listener = listener;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://apideveloprappigarage.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ICategoryAPI iCategoryAPI =  retrofit.create(ICategoryAPI.class);

    @Override
    public void getCategories() {

        retrofit2.Call<ArrayList<Category>> call =  iCategoryAPI.getCategories();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if(!response.isSuccessful()){
                    System.out.println("Error: "+String.valueOf(response.body()));
                    return;
                }
                listener.onSuccess( response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });


    }
}
