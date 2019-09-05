package co.edu.udea.rappigarage_android.GlobalServices.City;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cities {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    /**
     * No args constructor for use in serialization
     *
     */
    public Cities() {
    }

    /**
     *
     * @param countryId
     * @param id
     * @param name
     * @param longitude
     * @param latitude
     */
    public Cities(Integer id, String name, Integer countryId, String latitude, String longitude) {
        super();
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}