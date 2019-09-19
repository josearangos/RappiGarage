package co.edu.udea.rappigarage_android.Product.Publish.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCategoriesResponse {

    @SerializedName("response")
    @Expose
    private String response;

    /**
     * No args constructor for use in serialization
     */
    public AddCategoriesResponse() {
    }

    /**
     * @param response
     */
    public AddCategoriesResponse(String response) {
        super();
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
