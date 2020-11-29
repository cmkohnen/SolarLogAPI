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
            Date dateAsDate = GetStartOf.day(date);
            boolean bool = false;
            try {
                for(Date ignored : Entries.entriesPerDay(dateAsDate)) {
                    if (data.containsKey(dateAsDate)) {
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
