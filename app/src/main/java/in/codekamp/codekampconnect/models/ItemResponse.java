package in.codekamp.codekampconnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemResponse<T> {

    @SerializedName("data")
    @Expose
    private T data;

    /**
     *
     * @return
     * The data
     */
    public T getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(T data) {
        this.data = data;
    }

}