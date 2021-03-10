/*
Copyright 2020 - 2021 Christoph Kohnen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.meloni.SolarLogAPI.BasicGUI.Components;

import com.github.lgooddatepicker.components.DatePickerSettings;
import me.meloni.SolarLogAPI.DataConversion.Entries;
import me.meloni.SolarLogAPI.DataConversion.GetStartOf;
import me.meloni.SolarLogAPI.InverterMap;
import me.meloni.SolarLogAPI.SolarMap;

import java.util.Date;

/**
 * This class extends a {@link com.github.lgooddatepicker.components.DatePicker}
 * @author Christoph Kohnen
 * @since 3.3.0
 */
public class DatePicker extends com.github.lgooddatepicker.components.DatePicker {

    /**
     * Filter all usable dates based on a {@link SolarMap}
     * @param data The map from which all available dates should be determined
     */
    public void addVetoPolicy(InverterMap data) {
        DatePickerSettings settings = this.getSettings();
        settings.setVetoPolicy(date -> {
            Date dateAsDate = GetStartOf.day(date);
            boolean bool = false;
            for(Date ignored : Entries.getEntriesPerDay(dateAsDate)) {
                if (data.containsKey(dateAsDate)) {
                    bool = true;
                    break;
                }
            }
            return bool;
        });
    }
}
