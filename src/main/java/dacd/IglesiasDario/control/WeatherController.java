package dacd.IglesiasDario.control;

import dacd.IglesiasDario.model.Location;
import dacd.IglesiasDario.model.Weather;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherController {
    private Location location;
    private Integer days;  // Añadido de nuevo por si lo necesitas en el futuro
    private WeatherProvider weatherProvider;
    private WeatherStore weatherStore;
    private ScheduledExecutorService executorService;


    public WeatherController(Location location, WeatherProvider weatherProvider, WeatherStore weatherStore) {
        this.location = location;
        this.days = days;  // Configuración del número de días, si es necesario
        this.weatherProvider = weatherProvider;
        this.weatherStore = weatherStore;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        scheduleWeatherDataCollection();
        shutdown();
    }

    private void scheduleWeatherDataCollection() {
        executorService.scheduleAtFixedRate(this::execute, 0, 6, TimeUnit.HOURS); // Ejecuta cada 6 horas
    }

    public void execute() {
        Instant now = Instant.now();
        List<Weather> weatherData = weatherProvider.getWeatherData(location, now);

        for (Weather weather : weatherData) {
            if (weather != null) {
                weatherStore.storeWeatherData(weather, location, now);
            }
        }
    }

    // Método para cerrar el ejecutor de servicios
    public void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
