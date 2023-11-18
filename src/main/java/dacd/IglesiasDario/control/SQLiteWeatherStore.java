package dacd.IglesiasDario.control;

import dacd.IglesiasDario.model.Location;
import dacd.IglesiasDario.model.Weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

public abstract class SQLiteWeatherStore implements WeatherStore {

    public SQLiteWeatherStore() {
    }
    private Connection connect() {
        // Conexión a la base de datos SQLite
        String url = "jdbc:sqlite:weather_forecast.db"; // Reemplaza con tu ruta de base de datos
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Método para crear la tabla islandNames
    public void initializeDatabase(String islandNames) {
        String sql = "CREATE TABLE IF NOT EXISTS " + islandNames + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "temperature REAL," +
                "humidity INTEGER," +
                "rain REAL," +
                "windSpeed REAL," +
                "clouds REAL," +
                "locationName TEXT," +
                "latitude REAL," +
                "longitude REAL," +
                "timestamp TEXT)";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla WeatherData creada o ya existente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void storeWeatherData(Weather weather, Location location, Instant timestamp) {
        String sql = "INSERT INTO WeatherData (temperature, humidity, rain, windSpeed, clouds, locationName, latitude, longitude, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, weather.getTemperature());
            pstmt.setInt(2, weather.getHumidity());
            pstmt.setFloat(3, weather.getRain());
            pstmt.setFloat(4, weather.getWindSpeed());
            pstmt.setFloat(5, weather.getClouds());
            pstmt.setString(6, location.getName());
            pstmt.setDouble(7, location.getLatitude());
            pstmt.setDouble(8, location.getLongitude());
            pstmt.setString(9, timestamp.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
