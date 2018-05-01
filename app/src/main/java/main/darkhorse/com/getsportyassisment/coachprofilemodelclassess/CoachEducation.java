package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

import java.util.ArrayList;


public class CoachEducation {
    private ArrayList<SportEducationData> sportEducation;
    private ArrayList<FormalEducationData> formalEducation;
    private ArrayList<OtherCertificationData> otherCertification;

    public CoachEducation(ArrayList<SportEducationData> sportEducation, ArrayList<FormalEducationData> formalEducation, ArrayList<OtherCertificationData> otherCertification) {
        this.sportEducation = sportEducation;
        this.formalEducation = formalEducation;
        this.otherCertification = otherCertification;
    }

    public CoachEducation() {

    }

    public ArrayList<SportEducationData> getSportEducation() {
        return sportEducation;
    }

    public void setSportEducation(ArrayList<SportEducationData> sportEducation) {
        this.sportEducation = sportEducation;
    }

    public ArrayList<FormalEducationData> getFormalEducation() {
        return formalEducation;
    }

    public void setFormalEducation(ArrayList<FormalEducationData> formalEducation) {
        this.formalEducation = formalEducation;
    }

    public ArrayList<OtherCertificationData> getOtherCertification() {
        return otherCertification;
    }

    public void setOtherCertification(ArrayList<OtherCertificationData> otherCertification) {
        this.otherCertification = otherCertification;
    }
}
