package dacd.IglesiasDario.control;

import dacd.IglesiasDario.model.Location;
import dacd.IglesiasDario.model.Weather;

import java.time.Instant;
import java.util.List;

public interface WeatherProvider {
    List<Weather> getWeatherData(Location location, Instant timeStamp);

}
