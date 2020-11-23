package Interface;

import Handling.SolarMap;
import TransformUtilities.DataConversion.Entries;
import TransformUtilities.DataConversion.GetStartOf;
import com.github.lgooddatepicker.components.DatePickerSettings;

import java.text.ParseException;
import java.util.Date;

public class DatePicker extends com.github.lgooddatepicker.components.DatePicker {
    public DatePicker() {
    }

    public void addVetoPolicy(SolarMap data) {
        DatePickerSettings settings = this.getSettings();
        settings.setVetoPolicy(date -> {
            Date dateasdate = GetStartOf.day(date);
            boolean bool = false;
            try {
                for(Date timestamp : Entries.entriesperday(dateasdate)) {
                    if (data.containsKey(dateasdate)) {
                        bool = true;
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return bool;
        });
    }
}
