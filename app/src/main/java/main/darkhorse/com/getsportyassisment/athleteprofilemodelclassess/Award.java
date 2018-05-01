package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

public class Award {

    private String nameOfAward;
   private String description;
    private String date;
    public Award(String nameOfAward, String description, String date) {
        this.nameOfAward = nameOfAward;
        this.description = description;
        this.date = date;
    }

    public String getNameOfAward() {
        return nameOfAward;
    }

    public void setNameOfAward(String nameOfAward) {
        this.nameOfAward = nameOfAward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
