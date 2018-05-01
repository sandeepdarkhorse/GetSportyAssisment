package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

public class LatestResults {
    private String nameOfCompetation;
    private String dateOfCompetation;
    private String result;
    private String event;
    private String roundOrHeat;
    private String time;

    private String image;


    public LatestResults(String result, String event, String roundOrHeat, String time, String nameOfCompetation, String dateOfCompetation, String image) {
        this.result = result;
        this.event = event;
        this.roundOrHeat = roundOrHeat;
        this.time = time;
        this.nameOfCompetation = nameOfCompetation;
        this.dateOfCompetation = dateOfCompetation;
        this.image = image;
    }

    public LatestResults() {

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getRoundOrHeat() {
        return roundOrHeat;
    }

    public void setRoundOrHeat(String roundOrHeat) {
        this.roundOrHeat = roundOrHeat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNameOfCompetation() {
        return nameOfCompetation;
    }

    public void setNameOfCompetation(String nameOfCompetation) {
        this.nameOfCompetation = nameOfCompetation;
    }

    public String getDateOfCompetation() {
        return dateOfCompetation;
    }

    public void setDateOfCompetation(String dateOfCompetation) {
        this.dateOfCompetation = dateOfCompetation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
