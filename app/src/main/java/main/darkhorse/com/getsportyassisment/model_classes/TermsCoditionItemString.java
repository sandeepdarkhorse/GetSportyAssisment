package main.darkhorse.com.getsportyassisment.model_classes;

import java.io.Serializable;

/**
 * Created by sandeepsingh on 19/4/18.
 */

public class TermsCoditionItemString implements Serializable {

    public TermsCoditionItemString(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    String term;
}
