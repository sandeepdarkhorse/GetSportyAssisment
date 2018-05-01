package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import java.util.ArrayList;

public class Achivement {


    private ArrayList<BestResult> bestResult = null;
    private ArrayList<Award> awards = null;

    public Achivement() {
        bestResult = new ArrayList<>();
        awards = new ArrayList<>();
    }

    public Achivement(ArrayList<BestResult> bestResult, ArrayList<Award> awards) {
        this.bestResult = bestResult;
        this.awards = awards;
    }

    public ArrayList<BestResult> getBestResult() {
        return bestResult;
    }

    public void setBestResult(ArrayList<BestResult> bestResult) {
        this.bestResult = bestResult;
    }

    public ArrayList<Award> getAwards() {
        return awards;
    }

    public void setAwards(ArrayList<Award> awards) {
        this.awards = awards;
    }
}
