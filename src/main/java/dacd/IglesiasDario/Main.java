package dacd.IglesiasDario;

import dacd.IglesiasDario.control.*;
import dacd.IglesiasDario.model.Location;
import dacd.IglesiasDario.model.Weather;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<Location> locations = loadLocationsFromFile("/dacd/IglesiasDario/locations.tsv");
        BufferedReader reader = new BufferedReader(new FileReader("locations.tsv"));

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

    private static List<Location> loadLocationsFromFile(String filePath) {
        List<Location> locations = new ArrayList<>();
            try (InputStream is = Main.class.getClassLoader().getResourceAsStream(filePath)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    String name = parts[0];
                    float latitude = Float.parseFloat(parts[1]);
                    float longitude = Float.parseFloat(parts[2]);
                    locations.add(new Location(name, latitude, longitude));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
