import model.Klient;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    // Переместили klienty сюда - теперь доступна во всех методах
    private static ArrayList<Klient> klienty = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Rejestracja");
            System.out.println("2. Pokaż wszystkich klientów");
            System.out.println("3. Zaloguj się do profilu");
            System.out.println("4. Wyjść z aplikacji");
            System.out.print("Wybierz opcję: ");

            int choise = scanner.nextInt();
            scanner.nextLine();

            switch (choise) {
                case 1:
                    registracija(scanner);
                    break;
                case 2:
                    pokazatKlientov();
                    break;
                case 3:
                    zaloguj(scanner);
                    break;
                case 4:
                    System.out.println("Do widzenia!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Nieprawidłowy wybór!");
            }
        }
    }

    public static void registracija(Scanner scanner) {
        System.out.println("\n=== REJESTRACJA ===");

        System.out.print("Wprowadź id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Wpisz imię: ");
        String firstName = scanner.nextLine();

        System.out.print("Wpisz nazwisko: ");
        String lastName = scanner.nextLine();

        System.out.print("Wprowadź datę urodzenia (rok-miesiąc-dzień): ");
        String dateStr = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(dateStr);

        System.out.print("Wprowadź płeć (M/K): ");
        String sex = scanner.nextLine();

        System.out.print("Wpisz email: ");
        String email = scanner.nextLine();

        System.out.print("Wpisz numer telefonu: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Wprowadź hasło: ");
        String password = scanner.nextLine();

        // Создаём клиента
        Klient klient = new Klient(id, firstName, lastName, birthDate,
                sex, email, phoneNumber, password);
        klienty.add(klient);

        System.out.println("Klient zarejestrowany ");
        System.out.println(klient);
    }

    public static void pokazatKlientov() {
        System.out.println("WSZYSCY KLIENCI");
        if (klienty.isEmpty()) {
            System.out.println("Brak zarejestrowanych klientów");
        } else {
            for (Klient k : klienty) {
                System.out.println(k);
            }
        }
    }

    public static void zaloguj(Scanner scanner) {
        System.out.println("LOGOWANIE");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Hasło: ");
        String password = scanner.nextLine();

        for (Klient k : klienty) {
            if (k.getEmail().equals(email) && k.getPasswordHash().equals(password)) {
                System.out.println("Zalogowano pomyślnie!");
                return;
            }
        }
        System.out.println("Nieprawidłowy email lub hasło!");
    }
}