package dacd.IglesiasDario;

import dacd.IglesiasDario.control.*;
import dacd.IglesiasDario.model.Location;
import dacd.IglesiasDario.model.Weather;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        List<Location> locations = loadLocationsFromFile("/locations.tsv");

        WeatherProvider weatherProvider = new OpenWeatherMapProvider();
        WeatherStore weatherStore = new SQLiteWeatherStore() {
            @Override
            public void storeWeatherData(Weather weather, Location location, Instant timestamp) {
                // Implementación del método storeWeatherData
                storeWeatherData(weather, location, timestamp);
            }
        };

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            for (Location location : locations) {
                WeatherController controller = new WeatherController(location, weatherProvider, weatherStore);
                controller.execute();
            }
        }, 0, 6, TimeUnit.HOURS);

        // Agrega un gancho de apagado para cerrar el executorService
        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));
    }
}
