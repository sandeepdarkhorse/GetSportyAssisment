package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by shekhar on 30/7/18.
 */
public class InstituteDataPojo {
    String inst_type;
    String inst_name;

    public String getInst_type() {
        return inst_type;
    }

    public void setInst_type(String inst_type) {
        this.inst_type = inst_type;
    }

    public String getInst_name() {
        return inst_name;
    }

    public void setInst_name(String inst_name) {
        this.inst_name = inst_name;
    }

    public String getInst_email() {
        return inst_email;
    }

    public void setInst_email(String inst_email) {
        this.inst_email = inst_email;
    }

    public String getInst_mobnumber() {
        return inst_mobnumber;
    }

    public void setInst_mobnumber(String inst_mobnumber) {
        this.inst_mobnumber = inst_mobnumber;
    }

    public String getInst_regno() {
        return inst_regno;
    }

    public void setInst_regno(String inst_regno) {
        this.inst_regno = inst_regno;
    }

    public String getInst_address() {
        return inst_address;
    }

    public void setInst_address(String inst_address) {
        this.inst_address = inst_address;
    }

    public String getInst_location() {
        return inst_location;
    }

    public void setInst_location(String inst_location) {
        this.inst_location = inst_location;
    }

    public String getInst_image() {
        return inst_image;
    }

    public void setInst_image(String inst_image) {
        this.inst_image = inst_image;
    }

    String inst_email;
    String inst_mobnumber;
    String inst_regno;
    String inst_address;
    String inst_location;
    String inst_image;
    public InstituteDataPojo(String inst_type, String inst_name, String inst_email, String inst_mobnumber, String inst_regno, String inst_address, String inst_location, String inst_image) {
        this.inst_type = inst_type;
        this.inst_name = inst_name;
        this.inst_email = inst_email;
        this.inst_mobnumber = inst_mobnumber;
        this.inst_regno = inst_regno;
        this.inst_address = inst_address;
        this.inst_location = inst_location;
        this.inst_image = inst_image;
    }




}
