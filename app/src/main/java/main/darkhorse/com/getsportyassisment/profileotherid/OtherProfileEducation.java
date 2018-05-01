package main.darkhorse.com.getsportyassisment.profileotherid;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;


/**
 * Created by sandeep on 7/11/17.
 */

public class OtherProfileEducation {
    private ArrayList<SportEducationData> sportEducation;
    private ArrayList<FormalEducationData> formalEducation;
    private ArrayList<OtherCertificationData> otherCertification;

    public OtherProfileEducation(ArrayList<SportEducationData> sportEducation, ArrayList<FormalEducationData> formalEducation, ArrayList<OtherCertificationData> otherCertification) {
        this.sportEducation = sportEducation;
        this.formalEducation = formalEducation;
        this.otherCertification = otherCertification;
    }

    public OtherProfileEducation() {

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
