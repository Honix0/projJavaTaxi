import enums.Languages;
import model.Administrator;
import model.Driver;
import model.Klient;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    // Languages
    public static Languages.Language currentLang = Languages.Language.PL;

    // Переместили klienty сюда - теперь доступна во всех методах
    private static ArrayList<Klient> klienty = new ArrayList<>();
    private static ArrayList<Administrator> admins = new ArrayList<>();
    private static ArrayList<Driver> drivers = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //// MockData

        // Adding Clients

        Klient Klient1 = new Klient(1, "Maksym", "Zawodnik", java.time.LocalDate.of(2006, 1, 12), "Man", "suka@gmail.com",
                "999999999", "12345");
        Klient Klient2 = new Klient(2, "Nikita", "Zotkin", java.time.LocalDate.of(2015, 3, 15), "Woman", "suka2@gmail.com",
                "333333333", "12345");

        klienty.add(Klient1);
        klienty.add(Klient2);

        // Adding Admins

        Administrator Admin1 = new Administrator(1, "Andrey", "Immersion", java.time.LocalDate.of(1989, 1, 12), "Man", "suka3@gmail.com",
                "999999999", "11111");
        Administrator Admin2 = new Administrator(2, "Ramzan", "Tairov", java.time.LocalDate.of(1092, 12, 12), "Man", "suka4@gmail.com",
                "777777777", "54321");

        admins.add(Admin1);
        admins.add(Admin2);

        // Adding Drivers

        Driver Driver1 = new Driver(1, "Yehor", "Lysenko", java.time.LocalDate.of(2001, 7, 8), "Man", "suka5@gmail.com",
                "444444444", "66666");
        Driver Driver2 = new Driver(2, "Mykola", "Garbarenko", java.time.LocalDate.of(1998, 6, 28), "Man", "suka6@gmail.com",
                "787878787", "88888");

        drivers.add(Driver1);
        drivers.add(Driver2);

        ////

        while (true) {
            System.out.println(Languages.MENU.get(Main.currentLang));
            System.out.println("1. " + Languages.REGISTER.get(Main.currentLang));
            System.out.println("2. Pokaż wszystkich klientów");
            System.out.println("3. Zaloguj się do profilu");
            System.out.println("4. Zmienić Język" );
            System.out.println("5. Wyjść z aplikacji");
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
                    zmienicJezyk(scanner);
                    break;
                case 5:
                    System.out.println("Do widzenia!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Nieprawidłowy wybór!");
            }
        }
    }

    public static void zmienicJezyk(Scanner scanner) {
        System.out.println("\n=== WYBÓR JĘZYKA / LANGUAGE SELECTION ===");

        // выводим список языков
        int index = 1;
        for (Languages.Language lang : Languages.Language.values()) {
            System.out.println(index + ". " + lang);
            index++;
        }

        System.out.print("Wybierz język: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > Languages.Language.values().length) {
                System.out.println("Nieprawidłowy wybór języka!");
                return;
            }


            currentLang = Languages.Language.values()[choice - 1];

            System.out.println("Język zmieniony na: " + currentLang);

        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Błąd! Wprowadź numer języka.");
        }
    }

    public static void registracija(Scanner scanner) {
        System.out.println(Languages.REGISTER.get(Main.currentLang));

        System.out.print("Wprowadź id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print(Languages.ENTER_NAME.get(Main.currentLang));
        String firstName = scanner.nextLine();

        System.out.print(Languages.ENTER_SURNAME.get(Main.currentLang));
        String lastName = scanner.nextLine();

        System.out.print(Languages.ENTER_BIRTHDATE.get(Main.currentLang));
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
        System.out.println(klient.getFirstName() + " " + klient.getLastName());
    }

    // dodelat'
    public static void zamowicTaksowke()
    {
        System.out.println("Zamówenie taksówki");
    }



    public static void pokazatKlientov() {
        System.out.println("WSZYSCY KLIENCI");
        if (klienty.isEmpty()) {
            System.out.println("Brak zarejestrowanych klientów");
        } else {
            for (Klient k : klienty) {
                System.out.println("Imie: " + k.getFirstName() + ", Nazwisko: " + k.getLastName() + ", Email: " + k.getEmail() + ", Phone Number: " + k.getPhoneNumber());
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
                System.out.println("Zalogowano pomyślnie! " + k.getFirstName() + " " + k.getLastName());
                return;
            }
        }
        System.out.println(Languages.LOGIN_FAIL.get(Main.currentLang));
    }
}