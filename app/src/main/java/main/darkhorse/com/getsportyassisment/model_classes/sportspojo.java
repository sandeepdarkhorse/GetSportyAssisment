package main.darkhorse.com.getsportyassisment.model_classes;


import java.io.Serializable;

public class sportspojo implements Serializable {
    public sportspojo(String name, int drawable_id) {
        this.name = name;
        this.drawable_id = drawable_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable_id() {
        return drawable_id;
    }

    public void setDrawable_id(int drawable_id) {
        this.drawable_id = drawable_id;
    }

    String name;
    int drawable_id;

}
