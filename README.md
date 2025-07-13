# Technologies

The project is written in Java and uses the following third-party elements:

* Open-meteo api for weather forecasts: https://open-meteo.com/en/docs
* Geocoding api for converting location names into usable coordinates: https://geocode.maps.co/
* JSON handling library: https://github.com/stleary/JSON-java
* Javafx library for handling the graphical side of the program and event handling: https://openjfx.io/

Additionally following technologies and concepts were used:

* Usage of css to style the graphical side of the program
* Working with resources - png and css files
* Uses HTTPS requests to external APIs

# Features

Project allows for receiving data about current and long-term weather data including:

* Temperature
* Apparent temperature
* Humidity
* Precipitation
* Wind speed
* Pressure
* Weather codes (WMO representation)
* Time

# Notes

Used apis have a few limitations like for example open-meteo weather forecast api updating current data every 15 minutes. 

For the program to work "INSERTAPIKEY" must be replaced with a correct api key from the geocoding api at line 111 of the "main.java" file 

# Demonstration

Demonstration of the project is located at:
