# SolarLogAPI
![Maven](https://github.com/ChaosMelone9/SolarLogAPI/workflows/Java%20CI%20with%20Maven/badge.svg)
![CodeQL](https://github.com/ChaosMelone9/SolarLogAPI/workflows/CodeQL/badge.svg)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/ChaosMelone9/SolarLogAPI/blob/main/LICENSE)

Hello everyone and thank you for checking out this little project. This API is for reading data and converting it from the weekly backup files sent by your Solarlog.

The goal is to keep a modular way to easily read and manipulate the data to be able to work with it in other projects. I am also working on a UI for visualizing it directly, you might wanna check this out [here](https://github.com/ChaosMelone9/SolarLogVisualizer).

If you want to improve some functions or contribute feel free to do so, also feel free to use this project in any way that could help you personally.

Greetings, Chaosmelone9
(Sorry for the lack of english skills :P )

## Features

- Read from .dat-files
- Direct tar support
- InfluxDB support
- EML file support
- Persistent storage support
- Very Basic UI elements
- Moderate conversion

## How to use

This will guide you through a very basic process of being able to use data for your own.

### Importing 

Sadly this project is not hosted anywhere (yet), so you have to import it on your own. I'll assume you use maven for this case, steps for other platforms or IDEs are well documented in the internet. To import type the following while replacing both {VERSION} tags with your desired version.

```
mvn install:install-file -Dfile=SolarLogAPI{VERSION}.jar -DgroupId=me.meloni -DartifactId=SolarLogAPI -Dversion={VERSION} -Dpackaging=jar
```

Now it is installed in your local repository and you are free to add it as a dependency for your project.
Simply drop this in your pom.xml (Change the {VERSION} Tag here too):

```xml
<dependency>
    <groupId>me.meloni</groupId>
    <artifactId>SolarLogAPI</artifactId>
    <version>{VERSION}</version>
</dependency>
```

### Usage

This project is structured around the SolarMap object. You can easyly add data to it and retrieve from it.

Here's an example:

```java
SolarMap solarMap = new SolarMap();

File dataFile = GetFile.validChosenDataFile();

solarMap.addFromDataFile(dataFile);

Map<Date, List<Integer>> asMap = solarMap.getAsMap();
```

Now you can work with the map further on.

If you even want a basic Graph for display usages simply run the following:

```java
JPanel graph = new new DayView(solarMap.getDayGraphData({YOURDATE}));
```

There is also implementation of some UI Interfaces:
```java
SolarMap solarMap = BasicSolarMapCustomizer.solarMap();
```
or 
```java
GraphCustomizer.run();
```
