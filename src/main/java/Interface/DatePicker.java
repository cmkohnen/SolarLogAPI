package Interface;

import TransformUtilities.DataConversion.Entries;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DatePicker extends com.github.lgooddatepicker.components.DatePicker {
    public DatePicker(Map<Date, List<Integer>> data) {
        DatePickerSettings settings = this.getSettings();
        settings.setVetoPolicy(new DateVetoPolicy() {
            @Override
            public boolean isDateAllowed(LocalDate date) {
                Date dateasdate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
            }
        });
    }
}
