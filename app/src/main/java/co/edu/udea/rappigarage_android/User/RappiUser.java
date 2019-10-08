package co.edu.udea.rappigarage_android.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RappiUser {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public RappiUser() {
    }

    /**
     *
     * @param name
     * @param photoUrl
     */
    public RappiUser(String name, String photoUrl) {
        super();
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}