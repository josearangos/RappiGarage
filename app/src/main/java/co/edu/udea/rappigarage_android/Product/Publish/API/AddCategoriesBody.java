package co.edu.udea.rappigarage_android.Product.Publish.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddCategoriesBody {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("categories")
    @Expose
    private List<Integer> categories = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddCategoriesBody() {
    }

    /**
     *
     * @param categories
     * @param productId
     */
    public AddCategoriesBody(Integer productId, List<Integer> categories) {
        super();
        this.productId = productId;
        this.categories = categories;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

}