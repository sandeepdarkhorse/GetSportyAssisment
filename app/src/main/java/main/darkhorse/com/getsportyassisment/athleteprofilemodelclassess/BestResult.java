
package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

public class BestResult {


    private String result;
    private String rounds;
    private String nameComptation;
    private String date;

    public BestResult(String result, String rounds, String nameComptation, String date) {
        this.result = result;
        this.rounds = rounds;
        this.nameComptation = nameComptation;
        this.date = date;
    }



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRounds() {
        return rounds;
    }

    public void setRounds(String rounds) {
        this.rounds = rounds;
    }

    public String getNameComptation() {
        return nameComptation;
    }

    public void setNameComptation(String nameComptation) {
        this.nameComptation = nameComptation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
