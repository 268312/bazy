package entity;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj nazwisko: ");
        String surname = scanner.nextLine();

        System.out.print("Podaj hasło: ");
        String password = scanner.nextLine();

        String userType = authenticateUser(surname, password);

        switch (userType) {
            case "technik":
                System.out.println("Zalogowano jako technik.");
                // kod dla technika

                break;
            case "lekarz":
                System.out.println("Zalogowano jako lekarz.");

                // kod dla lekarza

                break;
            default:
                System.out.println("Nieprawidłowe nazwisko lub hasło.");
                break;
        }
    }

    private static String authenticateUser(String surname, String password) {
        String url = "jdbc:mysql://localhost:3306/projekt";
        String user = "root";
        String dbPassword = "root";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            // Sprawdź tabelę lekarzy
            String queryDoctors = "SELECT 'lekarz' AS userType FROM doctors WHERE doctor_surname = ? AND doctor_password = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryDoctors)) {
                statement.setString(1, surname);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("userType");
                    }
                }
            }

            // Sprawdź tabelę techników
            String queryTechnicians = "SELECT 'technik' AS userType FROM technicians WHERE technician_surname = ? AND technician_password = ?";
            try (PreparedStatement statement = connection.prepareStatement(queryTechnicians)) {
                statement.setString(1, surname);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("userType");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "nieprawidlowe";
    }
}
