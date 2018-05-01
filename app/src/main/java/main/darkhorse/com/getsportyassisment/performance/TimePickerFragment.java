package main.darkhorse.com.getsportyassisment.performance;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import main.darkhorse.com.getsportyassisment.UtilsFile.TimeGetter;


/**
 * Created by nitin on 12/9/17.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener, TimeGetter {
    public static int flag=-1;
    private static int h=0;// static variable to record the already
    private static int m=0;// set time of the User schedule

    public static TimePickerFragment newInstance() {
        TimePickerFragment timePicker = new TimePickerFragment();
        h=0;
        m=0;
        flag=-1;
        return timePicker;
    }
    public static TimePickerFragment newInstance(int i, int hh, int mm) {
        TimePickerFragment timePicker = new TimePickerFragment();
        flag = i;
        h=hh;
        m=mm;
        return timePicker;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        if(h==0&&m==0) {
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,true);
        }
        else{
            return new TimePickerDialog(getActivity(), this, h, m,true);
        }

    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int hh, int mm) {
        TimeGetter getter = (TimeGetter) getActivity();
        if(flag==-1) {
            getter.setTimeSelected(hh, mm);
        }else{
            int pos = flag;
            getter.editTime(hh,mm,pos);

        }
    }

    @Override
    public void setTimeSelected(int hh, int mm) {

    }


    @Override
    public void editTime(int hh, int mm, int pos) {

    }

}
