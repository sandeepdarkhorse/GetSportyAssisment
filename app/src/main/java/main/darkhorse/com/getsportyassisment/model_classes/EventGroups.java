package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

/**
 * Created by kumargaurav on 25/01/18.
 */

public class EventGroups {
    private String groupId;
    private String ageGroup;
    private String gender;
    private String fees;
    private ArrayList<SelectedCategories> selectedCategories;

    public EventGroups() {
    }

    public EventGroups(String groupId, String ageGroup, String gender, String fees, ArrayList<SelectedCategories> selectedCategories) {
        this.groupId = groupId;
        this.ageGroup = ageGroup;
        this.gender = gender;
        this.fees = fees;
        this.selectedCategories = selectedCategories;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public ArrayList<SelectedCategories> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(ArrayList<SelectedCategories> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }
}
