package co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Files {

    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Files() {
    }

    /**
     *
     * @param photos
     */
    public Files(List<Photo> photos) {
        super();
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

}