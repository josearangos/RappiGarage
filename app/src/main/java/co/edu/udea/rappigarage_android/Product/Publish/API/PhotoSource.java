package co.edu.udea.rappigarage_android.Product.Publish.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoSource {

    @SerializedName("source")
    @Expose
    private String source;

    /**
     * No args constructor for use in serialization
     *
     */
    public PhotoSource() {
    }

    /**
     *
     * @param source
     */
    public PhotoSource(String source) {
        super();
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}