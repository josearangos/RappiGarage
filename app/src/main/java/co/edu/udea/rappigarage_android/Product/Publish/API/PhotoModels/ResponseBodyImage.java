package co.edu.udea.rappigarage_android.Product.Publish.API.PhotoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBodyImage {

    @SerializedName("result")
    @Expose
    private Result result;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseBodyImage() {
    }

    /**
     *
     * @param result
     */
    public ResponseBodyImage(Result result) {
        super();
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
