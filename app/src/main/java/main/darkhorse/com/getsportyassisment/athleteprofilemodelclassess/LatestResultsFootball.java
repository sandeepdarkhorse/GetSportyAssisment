package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import org.json.JSONObject;

public class LatestResultsFootball {
    private String result;
    private String round;
    private String score;
    private String penalty;
    private JSONObject detail;
    private String opponent;
    private String nameOfCompetation;
    private String dateOfCompetation;
    private String image;

    public LatestResultsFootball(String result, String round, String score, String penalty, JSONObject detail, String opponent, String nameOfCompetation, String dateOfCompetation, String image) {
        this.result = result;
        this.round = round;
        this.score = score;
        this.penalty = penalty;
        this.detail = detail;
        this.opponent = opponent;
        this.nameOfCompetation = nameOfCompetation;
        this.dateOfCompetation = dateOfCompetation;
        this.image = image;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public JSONObject getDetail() {
        return detail;
    }

    public void setDetail(JSONObject detail) {
        this.detail = detail;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
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

