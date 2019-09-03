package co.edu.udea.rappigarage_android.Home.API;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoSource;

public class Search {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photos")
    @Expose
    private List<PhotoSource> photos = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Search() {
    }

    /**
     *
     * @param photos
     * @param id
     * @param price
     * @param name
     */
    public Search(Integer id, Integer price, String name, List<PhotoSource> photos) {
        super();
        this.id = id;
        this.price = price;
        this.name = name;
        this.photos = photos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhotoSource> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoSource> photos) {
        this.photos = photos;
    }

}
