package in.codekamp.codekampconnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Himanshu on 5/17/2016.
 */
public class Phone {


        @SerializedName("data")
        @Expose
        private List<ParticularPhone> data = new ArrayList<ParticularPhone>();

        /**
         * @return The data
         */
        public List<ParticularPhone> getData() {
            return data;
        }

        /**
         * @param data The data
         */
        public void setData(List<ParticularPhone> data) {
            this.data = data;
        }

    }
