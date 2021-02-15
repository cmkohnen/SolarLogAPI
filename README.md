# SolarLogAPI
![Maven](https://github.com/ChaosMelone9/SolarLogAPI/workflows/Java%20CI%20with%20Maven/badge.svg)
![CodeQL](https://github.com/ChaosMelone9/SolarLogAPI/workflows/CodeQL/badge.svg)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/ChaosMelone9/SolarLogAPI/blob/main/LICENSE)

Hello everyone and thank you for checking out this little project. This API is for reading data and converting it from the weekly backup files sent by your Solarlog.

The goal is to keep a modular way to easily read and manipulate the data to be able to work with it in other projects. I am also working on a UI for visualizing it directly, you might want to check this out [here](https://github.com/ChaosMelone9/SolarLogVisualizer).

If you want to improve some functions or contribute feel free to do so, also feel free to use this project in any way that could help you personally.

Greetings, ChaosMelone9
(Sorry for the lack of english skills :P )

## Features

- Read from .dat-files
- Read from .js-files
- Direct .tar.gz support
- FTP support
- InfluxDB support
- MySQL support
- EML file support
- Persistent storage support
- SolarLog JSON Interface support
- Very Basic UI elements

## How to use

This will guide you through a very basic process of being able to use data for your own.

### Importing 

Sadly this project is not hosted anywhere (yet), so you have to import it on your own. I'll assume you use Maven for this case, steps for other platforms or IDEs are well documented in the internet. To import type the following while replacing both {VERSION} tags with your desired version.

```
mvn install:install-file -Dfile=SolarLogAPI-4.0.1.jar -DgroupId=me.meloni -DartifactId=SolarLogAPI -Dversion=4.0.1 -Dpackaging=jar
```

Now it is installed in your local repository, and you are free to add it as a dependency for your project.
Simply drop this in your pom.xml (Change the {VERSION} Tag here too):

```xml
<dependency>
    <groupId>me.meloni</groupId>
    <artifactId>SolarLogAPI</artifactId>
    <version>4.0.1</version>
</dependency>
```

### Usage

This project is structured around the SolarMap object. You can easily add data to it and retrieve from it.

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
JPanel graph = new DayView(solarMap.getDayGraphData({YOURDATE}));
```

There is also implementation of some UI Interfaces:
```java
SolarMap solarMap = BasicSolarMapCustomizer.solarMap();
```
or 
```java
GraphCustomizer.visualize(BasicSolarMapCustomizer.solarMap());
```
