package co.edu.udea.rappigarage_android.Home.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoSource;

public class ProductSummary {

    @SerializedName("search")
    @Expose
    private List<Search> search = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductSummary() {
    }

    /**
     *
     * @param search
     */
    public ProductSummary(List<Search> search) {
        super();
        this.search = search;
    }

    public List<Search> getSearch() {
        return search;
    }

    public void setSearch(List<Search> search) {
        this.search = search;
    }


}
