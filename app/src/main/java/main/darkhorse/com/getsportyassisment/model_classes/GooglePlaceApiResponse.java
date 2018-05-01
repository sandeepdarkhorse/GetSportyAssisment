package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

/**
 * Created by shekhar kashyap on 4/8/17.
 */
public class GooglePlaceApiResponse {
    public GooglePlaceApiResponse(String status, ArrayList<Predictions> predictions) {
        this.status = status;
        this.predictions = predictions;
    }

    private String status;

    private ArrayList<Predictions> predictions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Predictions> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<Predictions> predictions) {
        this.predictions = predictions;
    }



}
