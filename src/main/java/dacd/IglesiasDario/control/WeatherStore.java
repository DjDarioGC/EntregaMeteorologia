package dacd.IglesiasDario.control;

import dacd.IglesiasDario.model.Location;
import dacd.IglesiasDario.model.Weather;

import java.time.Instant;

public interface WeatherStore {
    void storeWeatherData(Weather weather, Location location, Instant timestamp);
}
