package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

public class Header {


    private String name;
    private String level;
    private String rank;
    private String location;
    private String description;

    public Header(String name, String level, String rank, String location, String description) {
        this.name = name;
        this.level = level;
        this.rank = rank;
        this.location = location;
        this.description = description;
    }


    public Header() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
