package entity;
import java.sql.*;
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

                boolean loggedIn = true;

                while (loggedIn) {
                    System.out.println("Menu lekarza:");
                    System.out.println("1. Wyświetl rezerwacje sprzętu");
                    System.out.println("2. Wyświetl listę urządzeń");
                    System.out.println("3. Zarezerwuj sprzęt");
                    System.out.println("4. Rozpocznij użycie sprzętu");
                    System.out.println("5. Zakończ użycie sprzętu");
                    System.out.println("0. Wyloguj");

                    System.out.print("Wybierz opcję: ");
                    int option = scanner.nextInt();

                    switch (option) {
                        case 1:
                            System.out.println("Podaj numer id urządzenia");
                            int deviceNumber = scanner.nextInt();
                            doctorsModule.displayDeviceReservationsView(deviceNumber);
                            break;
                        case 2:
                            doctorsModule.displayDeviceTable();
                            break;
                        case 3:
                            System.out.println("W celu dokonania rezerwacji, wypełnij formularz:");
                            System.out.println("Imię:");
                            String name = scanner.nextLine();
                            scanner.nextLine();
                            System.out.println("Id urządzenia:");
                            int device_id = scanner.nextInt();
                            System.out.println("Dzień, miesiąc i rok startu rezerwacji:");
                            int s_day = scanner.nextInt();
                            int s_m = scanner.nextInt();
                            int s_y = scanner.nextInt();
                            System.out.println("Dzień, miesiąc i rok końca rezerwacji:");
                            int e_day = scanner.nextInt();
                            int e_m = scanner.nextInt();
                            int e_y = scanner.nextInt();
                            DoctorsModule.makeReservationForDoctor(name, device_id, s_y, s_m, s_day, e_y, e_m, e_day);
                            break;
                        case 4:
                            System.out.println("Podaj ID urządzenia:");
                            int id = scanner.nextInt();
                            DoctorsModule.startDeviceUsage(id);
                            // tu nie działa
                            break;
                        case 5:
                            System.out.println("Podaj ID urządzenia:");
                            int dev_id = scanner.nextInt();
                            DoctorsModule.updateDeviceStatus(dev_id, "free");
                            break;
                        case 0:
                            loggedIn = false;  // Wylogowanie lekarza
                            System.out.println("Wylogowano lekarza");
                            break;
                        default:
                            System.out.println("Nieprawidłowa opcja. Wybierz ponownie.");
                    }
                }

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
