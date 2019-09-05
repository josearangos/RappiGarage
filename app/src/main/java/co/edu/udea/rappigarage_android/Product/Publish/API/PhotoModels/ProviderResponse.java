package co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("lastModified")
    @Expose
    private Object lastModified;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("container")
    @Expose
    private String container;
    @SerializedName("location")
    @Expose
    private String location;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProviderResponse() {
    }

    /**
     *
     * @param lastModified
     * @param etag
     * @param location
     * @param container
     * @param name
     * @param size
     */
    public ProviderResponse(String name, String etag, Object lastModified, Integer size, String container, String location) {
        super();
        this.name = name;
        this.etag = etag;
        this.lastModified = lastModified;
        this.size = size;
        this.container = container;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Object getLastModified() {
        return lastModified;
    }

    public void setLastModified(Object lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}