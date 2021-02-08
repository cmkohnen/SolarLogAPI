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
import me.meloni.SolarLogAPI.BasicGUI.BasicGraphCustomizer;
import me.meloni.SolarLogAPI.BasicGUI.BasicSolarMapCustomizer;
import me.meloni.SolarLogAPI.Handling.Logger;
import me.meloni.SolarLogAPI.SolarMap;

/**
 * This is a testing environment for tasks to do
 * @author Christoph Kohnen
 */
public class Main {
    public static void main(String[] args) {
        Logger.setEnabled(true);
        SolarMap solarMap = BasicSolarMapCustomizer.solarMap();
        BasicGraphCustomizer.visualize(solarMap);
    }
}
