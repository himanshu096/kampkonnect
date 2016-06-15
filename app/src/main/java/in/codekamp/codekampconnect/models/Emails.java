package in.codekamp.codekampconnect.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Himanshu on 5/11/2016.
 */
public class Emails {


    @SerializedName("data")
    @Expose
    private List<AnEmail> data = new ArrayList<AnEmail>();

    /**
     *
     * @return
     * The data
     */
    public List<AnEmail> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<AnEmail> data) {
        this.data = data;
    }

}