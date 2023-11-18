package dacd.IglesiasDario.model;

import java.time.Instant;

public class Weather {
    private Float temperature;
    private Float rain;
    private Integer humidity;
    private float clouds;
    private Float windSpeed;
    private Location location;
    private Instant timeStamp;

    public Weather(Float temperature, Float rain, Integer humidity, Integer clouds, Float windSpeed, Location location, Instant timeStamp) {
        this.temperature = temperature;
        this.rain = rain;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.location = location;
        this.timeStamp = timeStamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }
}
