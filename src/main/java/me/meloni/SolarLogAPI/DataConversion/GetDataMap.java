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
package me.meloni.SolarLogAPI.DataConversion;

import me.meloni.SolarLogAPI.SolarMap;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class includes a function to get Data from multiple files.
 * @author Christoph Kohnen
 * @since 0.0.3
 * @deprecated
 */
public class GetDataMap {
    /**
     * Get all data from multiple files in the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format. This function is deprecated.
     * @param paths A list of file paths from which you want to extract data
     * @return All data from the files in the {@link Map}<{@link Date}, {@link List}<{@link Integer}>> format
     * @deprecated
     */
    public static Map<Date, List<Integer>> getDataFromDatFiles(List<File> paths) {
        SolarMap solarMap = new SolarMap();
        solarMap.addFromDatFiles(paths);
        return solarMap.getAsMap();
    }
}
