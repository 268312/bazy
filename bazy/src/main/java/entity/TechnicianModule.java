package entity;
import java.util.Date;

import java.sql.*;


public class TechnicianModule {

    public static void performService(int deviceID) {
        // wykonanie serwisu
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             CallableStatement stmt = connection.prepareCall("UPDATE devices SET device_status = 'busy' WHERE deviceID = ?")) {

            stmt.setInt(1, deviceID);

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void completeService(int technicianID, int deviceID, String serviceDescription) {
        // zakończenie serwisu
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             CallableStatement stmt = connection.prepareCall("UPDATE devices SET device_status = 'free' WHERE deviceID = ?")) {

            stmt.setInt(1, deviceID);

            stmt.execute();
            insertServiceHistory(connection, deviceID, technicianID, serviceDescription);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void insertServiceHistory(Connection connection, int deviceID, int technikID, String serviceDescription) throws SQLException {
        String insertSql = "INSERT INTO service_history (service_date, serviced_device, technician, service_description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.setDate(1, new java.sql.Date(System.currentTimeMillis())); // obecna data
            stmt.setInt(2, deviceID);
            stmt.setInt(3, technikID);
            stmt.setString(4, serviceDescription);

            stmt.executeUpdate();
        }
    }
    public static void describeService(int deviceID, String description) {
        // opis serwisów
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             CallableStatement stmt = connection.prepareCall("UPDATE service_history SET service_description = ? WHERE serviced_device = ?")) {

            stmt.setInt(1, deviceID);
            stmt.setString(2, description);

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayDevicesWithNextServiceDate() {
        // wyswietlenie listy urzadzen i dat nastepnego serwisu
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM devices")) {

            while (rs.next()) {
                System.out.println("Device ID: " + rs.getInt("deviceID") +
                        ", Device Name: " + rs.getString("device_name") +
                        ", Next Service Date: " + rs.getDate("last_service"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void filterDevicesByServiceDate(Date startDate, Date endDate) {
        // filtrowanie listy urzadzen z podanym przedzialem dat
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             CallableStatement stmt = connection.prepareCall("SELECT * FROM devices WHERE last_service BETWEEN ? AND ?")) {

            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Filtered Device ID: " + rs.getInt("deviceID") +
                            ", Device Name: " + rs.getString("device_name") +
                            ", Next Service Date: " + rs.getDate("last_service"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewDevice(String deviceName, String deviceType, Date productionDate, int serviceValidityPeriod) {
        // dodanie nowego sprzetu
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             CallableStatement stmt = connection.prepareCall("INSERT INTO devices (device_name, device_type, production_date, service_validity_period) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, deviceName);
            stmt.setString(2, deviceType);
            stmt.setDate(3, new java.sql.Date(productionDate.getTime()));
            stmt.setInt(4, serviceValidityPeriod);

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}