package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by shekhar kashyap on 4/8/17.
 */
public class Predictions {
    public Predictions(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

}
