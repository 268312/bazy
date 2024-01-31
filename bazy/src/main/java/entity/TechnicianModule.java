package entity;
import java.time.LocalDate;
import java.util.Date;
import java.sql.*;


public class TechnicianModule {

    public static void performService(int deviceID) {
        // wykonanie serwisu
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             PreparedStatement stmt = connection.prepareStatement("UPDATE devices SET device_status = 'busy' WHERE deviceID = ?")) {

            stmt.setInt(1, deviceID);

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void completeService(String technicianName, int deviceID, String serviceDescription, int serviceValidity) {
        // zakończenie serwisu
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             PreparedStatement stmt = connection.prepareStatement("UPDATE device SET status = 'free', service_validity_period = ? WHERE deviceID = ?")) {

            stmt.setInt(1, serviceValidity);
            stmt.setInt(2, deviceID);

            stmt.executeUpdate();
            insertServiceHistory(deviceID, technicianName, serviceDescription);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void makeReservation(String technicianName, int deviceID, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) throws SQLException {
        String insertSql = "INSERT INTO device_reservation (date_from, date_to, reservated_device, reserving_person) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             PreparedStatement stmt = connection.prepareStatement(insertSql)){
            java.sql.Date startDate = new java.sql.Date(startYear-1900, startMonth-1, startDay);
            java.sql.Date endDate = new java.sql.Date(endYear-1900, endMonth-1, endDay);
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            stmt.setInt(3, deviceID);
            stmt.setString(4, technicianName);
            stmt.execute();
        }
    }
    private static void insertServiceHistory(int deviceID, String technicianName, String serviceDescription) throws SQLException {
        String insertSql = "INSERT INTO service_history (service_date, serviced_device, technician, service_description) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
                PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.setDate(1, new java.sql.Date(System.currentTimeMillis())); // obecna data
            stmt.setInt(2, deviceID);
            stmt.setString(3, technicianName);
            stmt.setString(4, serviceDescription);

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void describeService(int deviceID, String description) {
        // opis serwisów
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             PreparedStatement stmt = connection.prepareStatement("UPDATE service_history SET service_description = ? WHERE serviced_device = ?")) {

            stmt.setInt(2, deviceID);
            stmt.setString(1, description);

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayDevicesWithNextServiceDate() {
        // wyswietlenie listy urzadzen i dat nastepnego serwisu
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM device")) {

            while (rs.next()) {
                LocalDate last_service = rs.getDate("last_service").toLocalDate();
                LocalDate next_service = last_service.plusDays(rs.getInt("service_validity_period"));

                System.out.println("Device ID: " + rs.getInt("deviceID") +
                        ", Device Name: " + rs.getString("device_name") +
                        ", Next Service Date: " + next_service );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void filterDevicesByServiceDate(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        // filtrowanie listy urzadzen z podanym przedzialem dat
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM device WHERE last_service BETWEEN ? AND ?")) {
            java.sql.Date startDate = new java.sql.Date(startYear-1900, startMonth-1, startDay);
            java.sql.Date endDate = new java.sql.Date(endYear-1900, endMonth-1, endDay);
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Device ID: " + rs.getInt("deviceID") +
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
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO devices (device_name, device_type, production_date, service_validity_period) VALUES (?, ?, ?, ?)")) {

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