package co.edu.udea.rappigarage_android.Product.Publish.API;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels.Photo;

public class ProductResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("availableQuantity")
    @Expose
    private Integer availableQuantity;
    @SerializedName("warranty")
    @Expose
    private String warranty;
    @SerializedName("publishDate")
    @Expose
    private String publishDate;
    @SerializedName("availability")
    @Expose
    private Boolean availability;
    @SerializedName("measures")
    @Expose
    private Measures measures;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductResponse() {
    }

    /**
     *
     * @param location
     * @param cityId
     * @param condition
     * @param availableQuantity
     * @param photos
     * @param id
     * @param price
     * @param description
     * @param name
     * @param userId
     * @param warranty
     * @param measures
     * @param publishDate
     * @param availability
     */
    public ProductResponse(Integer id, Double price, String name, String description, Integer availableQuantity, String warranty, String publishDate, Boolean availability, Measures measures, Integer cityId, Integer userId, String condition, Location location, List<Photo> photos) {
        super();
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.warranty = warranty;
        this.publishDate = publishDate;
        this.availability = availability;
        this.measures = measures;
        this.cityId = cityId;
        this.userId = userId;
        this.condition = condition;
        this.location = location;
        this.photos = photos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

}