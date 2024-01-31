package entity;
import java.sql.*;
import java.util.Date;

public class DoctorsModule {
        public void displayDeviceReservationsView(int device_num) {
        // wyświetla rezerwacje urządzenia
        try {
            String url = "jdbc:mysql://localhost:3306/projekt";
            String user = "root";
            String password = "root";

            Connection connection = DriverManager.getConnection(url, user, password);

            String sqlQuery = "SELECT * FROM devicereservationsview WHERE reservated_device = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, device_num);

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();


            int columnWidth = 20;

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.print(padRight(columnName, columnWidth) + " ");
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.print(padRight(columnValue, columnWidth) + " ");
                }
                System.out.println();
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayDeviceTable() {
        // wyświetla listę wszystkich urządzeń
        try {

            String url = "jdbc:mysql://localhost:3306/projekt";
            String user = "root";
            String password = "root";

            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();

            String sqlQuery = "SELECT * FROM device";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            System.out.println("deviceID\tdevice_name\tdevice_type\tproduction_date\tlast_service\tdevice_status\tservice_validity_period");

            while (resultSet.next()) {
                int deviceID = resultSet.getInt("deviceID");
                String device_name = resultSet.getString("device_name");
                String device_type = resultSet.getString("device_type");
                String production_date = resultSet.getString("production_date");
                String last_service = resultSet.getString("last_service");
                String device_status = resultSet.getString("device_status");
                int service_validity_period = resultSet.getInt("service_validity_period");

                System.out.printf("%-8s%-15s%-15s%-15s%-15s%-15s%-25s\n",
                        deviceID, device_name, device_type, production_date,
                        last_service, device_status, service_validity_period);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String padRight(String s, int width) {
        // do formatowania tabeli w javie
        return String.format("%-" + width + "s", s);
    }

    public static void makeReservationForDoctor(String doctorName, int deviceID, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) throws SQLException {
        // rezerwacja urządzenia
        String checkAvailabilitySql = "SELECT COUNT(*) AS count FROM device_reservation WHERE reservated_device = ? AND date_to >= ? AND date_from <= ?";
        String insertSql = "INSERT INTO device_reservation (date_from, date_to, reservated_device, reserving_person) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "root");
             PreparedStatement checkAvailabilityStmt = connection.prepareStatement(checkAvailabilitySql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {

            java.sql.Date startDate = new java.sql.Date(startYear - 1900, startMonth - 1, startDay);
            java.sql.Date endDate = new java.sql.Date(endYear - 1900, endMonth - 1, endDay);

            checkAvailabilityStmt.setInt(1, deviceID);
            checkAvailabilityStmt.setDate(2, startDate);
            checkAvailabilityStmt.setDate(3, endDate);

            ResultSet availabilityResult = checkAvailabilityStmt.executeQuery();
            availabilityResult.next();

            int overlappingReservationsCount = availabilityResult.getInt("count");

            if (overlappingReservationsCount == 0) {
                insertStmt.setDate(1, startDate);
                insertStmt.setDate(2, endDate);
                insertStmt.setInt(3, deviceID);
                insertStmt.setString(4, doctorName);
                insertStmt.execute();
                System.out.println("Rezerwacja dodana pomyślnie.");
            } else {
                System.out.println("Sprzęt jest zajęty w danym okresie datowym.");
            }
        }
    }

    public static boolean isServiceValid(int deviceID, Date checkDate) throws SQLException {
        // czy ważny serwis
        String url = "jdbc:mysql://localhost:3306/projekt";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT last_service, service_validity_period FROM device WHERE deviceID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, deviceID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Date lastServiceDate = resultSet.getDate("last_service");
                        int serviceValidityPeriod = resultSet.getInt("service_validity_period");

                        long checkDateTime = checkDate.getTime();
                        long lastServiceDateTime = lastServiceDate.getTime();
                        long serviceValidityEndTime = lastServiceDateTime + (serviceValidityPeriod * 24L * 60L * 60L * 1000L);

                        return checkDateTime >= lastServiceDateTime && checkDateTime <= serviceValidityEndTime;
                    }
                }
            }
        }

        return false;
    }

    public static boolean isDeviceReserved(int deviceID) throws SQLException {
        // czy zarezerwowane
        String url = "jdbc:mysql://localhost:3306/projekt";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT COUNT(*) AS count FROM device_reservation " +
                    "WHERE reservated_device = ? AND date_from <= CURRENT_DATE() AND date_to >= CURRENT_DATE()";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, deviceID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() && resultSet.getInt("count") > 0;
                }
            }
        }
    }

    static void updateDeviceStatus(int deviceID, String status) throws SQLException {
        // zmienia status
        String url = "jdbc:mysql://localhost:3306/projekt";
        String user = "root";
        String password = "root";

        String updateQuery = "UPDATE device SET device_status = ? WHERE deviceID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, status);
            statement.setInt(2, deviceID);

            statement.executeUpdate();
        }
    }
    public static void startDeviceUsage(int deviceID) {
        try {
            // nie działa, nie mam siły sprawdzać czemu
            if (!isDeviceReserved(deviceID) && isServiceValid(deviceID, new Date())) {
                updateDeviceStatus(deviceID, "busy");

                System.out.println("Urządzenie zostało pomyślnie rozpoczęte do użytkowania.");
            } else {
                System.out.println("Nie można rozpocząć użytkowania urządzenia.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
