package co.edu.udea.rappigarage_android.Product.Publish.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measures {

    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("heigth")
    @Expose
    private Integer heigth;
    @SerializedName("weigth")
    @Expose
    private Integer weigth;
    @SerializedName("large")
    @Expose
    private Integer large;

    /**
     * No args constructor for use in serialization
     *
     */
    public Measures() {
    }

    /**
     *
     * @param weigth
     * @param heigth
     * @param width
     * @param large
     */
    public Measures(Integer width, Integer heigth, Integer weigth, Integer large) {
        super();
        this.width = width;
        this.heigth = heigth;
        this.weigth = weigth;
        this.large = large;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeigth() {
        return heigth;
    }

    public void setHeigth(Integer heigth) {
        this.heigth = heigth;
    }

    public Integer getWeigth() {
        return weigth;
    }

    public void setWeigth(Integer weigth) {
        this.weigth = weigth;
    }

    public Integer getLarge() {
        return large;
    }

    public void setLarge(Integer large) {
        this.large = large;
    }

}