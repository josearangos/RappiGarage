package co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("files")
    @Expose
    private Files files;
    @SerializedName("fields")
    @Expose
    private Fields fields;

    /**
     * No args constructor for use in serialization
     *
     */
    public Result() {
    }

    /**
     *
     * @param files
     * @param fields
     */
    public Result(Files files, Fields fields) {
        super();
        this.files = files;
        this.fields = fields;
    }

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

}
