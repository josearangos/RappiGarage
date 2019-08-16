package co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("container")
    @Expose
    private String container;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("providerResponse")
    @Expose
    private ProviderResponse providerResponse;

    /**
     * No args constructor for use in serialization
     *
     */
    public Photo() {
    }

    /**
     *
     * @param field
     * @param container
     * @param name
     * @param type
     * @param providerResponse
     * @param size
     */
    public Photo(String container, String name, String type, String field, Integer size, ProviderResponse providerResponse) {
        super();
        this.container = container;
        this.name = name;
        this.type = type;
        this.field = field;
        this.size = size;
        this.providerResponse = providerResponse;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public ProviderResponse getProviderResponse() {
        return providerResponse;
    }

    public void setProviderResponse(ProviderResponse providerResponse) {
        this.providerResponse = providerResponse;
    }

}