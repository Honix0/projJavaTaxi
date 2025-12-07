package service;

import exceptions.TaxiException;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

public class Menu {

    private List<Administrator> admins = new ArrayList<>();
    private List<Driver> drivers = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();

    // НОВОЕ: Список заказов, которые ждут водителя
    private List<String> pendingOrders = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);
    private Person currentUser = null;

    public Menu() {
        initializeData();
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            if (currentUser == null) {
                isRunning = showAuthMenu();
            } else {
                if (currentUser instanceof Administrator) {
                    showAdminMenu();
                } else if (currentUser instanceof Driver) {
                    showDriverMenu();
                } else if (currentUser instanceof Client) {
                    showClientMenu();
                }
            }
        }
    }

    // --- МЕНЮ АВТОРИЗАЦИИ ---
    private boolean showAuthMenu() {
        System.out.println("\nWITAJ W APLIKACJI TAXI");
        System.out.println("1. Zaloguj się (Login)");
        System.out.println("2. Zarejestruj się (Register)");
        System.out.println("0. Wyjście (Exit)");
        System.out.print("Wybór: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> login();
            case "2" -> register();
            case "0" -> {
                System.out.println("Do widzenia!");
                return false;
            }
            default -> System.out.println("Niepoprawna opcja.");
        }
        return true;
    }

    // --- LOGOWANIE ---
    private void login() {
        System.out.println("\nLOGOWANIE");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Hasło: ");
        String password = scanner.nextLine();

        for (Administrator admin : admins) {
            if (admin.getEmail().equals(email) && admin.getPasswordHash().equals(password)) {
                currentUser = admin;
                System.out.println("Witaj Administratorze, " + admin.getFirstName() + "!");
                return;
            }
        }
        for (Driver driver : drivers) {
            if (driver.getEmail().equals(email) && driver.getPasswordHash().equals(password)) {
                currentUser = driver;
                System.out.println("Witaj Kierowco, " + driver.getFirstName() + "!");
                return;
            }
        }
        for (Client client : clients) {
            if (client.getEmail().equals(email) && client.getPasswordHash().equals(password)) {
                currentUser = client;
                System.out.println("Witaj Kliencie, " + client.getFirstName() + "!");
                return;
            }
        }
        System.out.println("BŁĄD: Niepoprawny email lub hasło!");
    }

    // --- REJESTRACJA (ОБНОВЛЕННАЯ) ---
    private void register() {
        System.out.println("\nREJESTRACJA");
        System.out.println("Kim chcesz zostać?");
        System.out.println("1. Klient");
        System.out.println("2. Kierowca (Nowy pracownik)");
        System.out.print("Wybór: ");
        String roleChoice = scanner.nextLine();

        if (!roleChoice.equals("1") && !roleChoice.equals("2")) {
            System.out.println("Anulowano.");
            return;
        }

        String firstName = getValidInput("Imię", "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", "Imię musi zaczynać się dużą literą.");
        String lastName = getValidInput("Nazwisko", "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", "Nazwisko musi zaczynać się dużą literą.");
        String email = getValidInput("Email", "^.+@.+$", "Email musi zawierać znak @.");
        String phone = getValidInput("Telefon", "^\\d{9}$", "Telefon musi składać się z 9 cyfr.");
        LocalDate birthDate = getValidDate();
        System.out.print("Podaj hasło: ");
        String password = scanner.nextLine();

        int newId = (clients.size() + drivers.size() + admins.size()) + 100;

        if (roleChoice.equals("1")) {
            System.out.print("Podaj swoje miasto: ");
            String address = scanner.nextLine();
            clients.add(new Client(newId, firstName, lastName, birthDate, "N/A", email, phone, password, address));
            System.out.println("Rejestracja udana! Możesz się zalogować.");
        } else {
            // --- НОВЫЕ ВОПРОСЫ ДЛЯ ВОДИТЕЛЯ ---
            System.out.print("Podaj numer prawa jazdy: ");
            String license = scanner.nextLine();

            System.out.print("Model twojego samochodu (np. Toyota Prius): ");
            String carModel = scanner.nextLine();

            System.out.print("Ile lat jeździsz autem (doświadczenie): ");
            int exp = 0;
            try {
                exp = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                exp = 0;
            }

            // Создаем водителя с новыми полями
            drivers.add(new Driver(newId, firstName, lastName, birthDate, "N/A", email, phone, password, license, 0.0, carModel, exp));
            System.out.println("Rejestracja udana! Witaj w zespole.");
        }
    }

    // --- MENU KLIENTA ---
    private void showClientMenu() {
        System.out.println("\nMENU KLIENTA");
        System.out.println("1. Zamów taksówkę");
        System.out.println("2. Pokaż historię przejazdów");
        System.out.println("3. Mój profil");
        System.out.println("0. Wyloguj");
        System.out.print("Twój wybór: ");

        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1" -> orderTaxi();
                case "2" -> {
                    if (currentUser instanceof Client) ((Client) currentUser).printHistory();
                }
                case "3" -> System.out.println(currentUser);
                case "0" -> logout();
                default -> System.out.println("Niepoprawna opcja.");
            }
        } catch (TaxiException e) {
            System.out.println("BŁĄD ZAMÓWIENIA: " + e.getMessage());
        }
    }

    // --- MENU KIEROWCY (ОБНОВЛЕННОЕ) ---
    private void showDriverMenu() {
        System.out.println("\nMENU KIEROWCY");
        System.out.println("1. Pokaż dostępne zlecenia (Weź kurs)"); // НОВОЕ
        System.out.println("2. Mój aktywny przejazd"); // НОВОЕ
        System.out.println("3. Kiedy wypłata?"); // НОВОЕ
        System.out.println("4. Mój profil");
        System.out.println("0. Wyloguj");
        System.out.print("Twój wybór: ");

        Driver driver = (Driver) currentUser; // Приводим к типу Driver

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> takeOrder(driver); // Логика взятия заказа
            case "2" -> System.out.println("AKTUALNY KURS: " + driver.getActiveOrder());
            case "3" -> System.out.println("Następna wypłata: " + driver.getNextPayDay());
            case "4" -> System.out.println(driver);
            case "0" -> logout();
            default -> System.out.println("Niepoprawna opcja.");
        }
    }

    // Метод для взятия заказа водителем
    private void takeOrder(Driver driver) {
        System.out.println("\n--- DOSTĘPNE ZLECENIA ---");
        if (pendingOrders.isEmpty()) {
            System.out.println("Brak zleceń w tej chwili. Czekaj...");
            return;
        }

        // Показываем список ожидающих заказов
        for (int i = 0; i < pendingOrders.size(); i++) {
            System.out.println((i + 1) + ". " + pendingOrders.get(i));
        }

        System.out.print("Wybierz numer zlecenia (lub 0 aby wrócić): ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < pendingOrders.size()) {
                // Водитель берет заказ
                String order = pendingOrders.remove(index); // Удаляем из общего списка
                driver.setActiveOrder(order); // Назначаем водителю
                System.out.println("Przyjąłeś zlecenie: " + order);
                System.out.println("Szerokiej drogi!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Błąd wyboru.");
        }
    }

    // --- MENU ADMINA ---
    private void showAdminMenu() {
        System.out.println("\nMENU ADMINISTRATORA");
        System.out.println("1. Pokaż wszystkich użytkowników");
        System.out.println("2. Pokaż flotę taksówek");
        System.out.println("3. Usuń klienta");
        System.out.println("4. Usuń kierowcę");
        System.out.println("5. Zmień cennik");
        System.out.println("0. Wyloguj");
        System.out.print("Twój wybór: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> showAllUsers();
            case "2" -> showCars();
            case "3" -> deleteClient();
            case "4" -> deleteDriver();
            case "5" -> changePrice();
            case "0" -> logout();
            default -> System.out.println("Niepoprawna opcja.");
        }
    }

    // --- МЕТОДЫ ДЛЯ АДМИНА ---
    private void deleteClient() {
        System.out.print("Podaj ID klienta: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            clients.removeIf(c -> c.getId() == id);
            System.out.println("Usunięto.");
        } catch (Exception e) { System.out.println("Błąd ID."); }
    }

    private void deleteDriver() {
        System.out.print("Podaj ID kierowcy: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            drivers.removeIf(d -> d.getId() == id);
            System.out.println("Usunięto.");
        } catch (Exception e) { System.out.println("Błąd ID."); }
    }

    private void changePrice() {
        showCars();
        System.out.print("Podaj ID taryfy: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Car c = cars.stream().filter(car -> car.getId() == id).findFirst().orElse(null);
            if (c != null) {
                System.out.print("Nowa cena: ");
                double p = Double.parseDouble(scanner.nextLine());
                c.setPricePerKm(p);
                System.out.println("Zmieniono.");
            }
        } catch (Exception e) { System.out.println("Błąd."); }
    }

    private void logout() {
        currentUser = null;
        System.out.println("Pomyślnie wylogowano.");
    }

    // --- ВАЛИДАЦИЯ ---
    private String getValidInput(String fieldName, String regex, String errorMessage) {
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            System.out.print(fieldName + ": ");
            String input = scanner.nextLine();
            if (pattern.matcher(input).matches()) return input;
            System.out.println("BŁĄD: " + errorMessage);
        }
    }

    private LocalDate getValidDate() {
        while (true) {
            System.out.print("Data urodzenia (RRRR-MM-DD): ");
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("BŁĄD: Zły format daty!");
            }
        }
    }

    // --- ZAMAWIANIE TAXI (ОБНОВЛЕННОЕ) ---
    private void orderTaxi() throws TaxiException {
        System.out.println("\n--- ZAMAWIANIE TAKSÓWKI ---");
        System.out.print("Podaj adres docelowy: ");
        String destination = scanner.nextLine();
        System.out.print("Szacowany dystans w km: ");
        double km;
        try {
            km = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Błąd liczby.");
            return;
        }

        showCars();
        System.out.print("Wybierz ID taryfy: ");
        int carId;
        try {
            carId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) { return; }

        Car selectedCar = null;
        for (Car car : cars) {
            if (car.getId() == carId) { selectedCar = car; break; }
        }

        if (selectedCar == null) throw new TaxiException("Nie ma takiego auta.");

        double price = selectedCar.calculatePrice(km);
        System.out.println("CENA: " + price + " PLN");

        System.out.print("Zamawiasz? (t/n): ");
        if (scanner.nextLine().equalsIgnoreCase("t")) {
            Payment p = new Payment(UUID.randomUUID().toString(), price, "TX-" + System.currentTimeMillis());
            p.processPayment();

            // 1. Сохраняем в историю клиента
            if (currentUser instanceof Client) {
                String log = LocalDate.now() + " | " + selectedCar.getBrand() + " | " + destination + " | " + price + " PLN";
                ((Client) currentUser).addRideToHistory(log);
            }

            // 2. ОТПРАВЛЯЕМ ЗАКАЗ В СПИСОК ОЖИДАНИЯ ДЛЯ ВОДИТЕЛЕЙ
            String orderForDriver = "Zlecenie: " + destination + " (" + km + "km) - Zarobek: " + (price * 0.7) + " PLN"; // Водитель получает 70%
            pendingOrders.add(orderForDriver);

            System.out.println("Zamówienie wysłane do kierowców! Czekaj na przyjazd.");
        } else {
            System.out.println("Anulowano.");
        }
    }

    private void showCars() {
        System.out.println("\nTARYFY");
        for (Car car : cars) System.out.println(car);
    }

    private void showAllUsers() {
        System.out.println("\nUŻYTKOWNICY");
        admins.forEach(System.out::println);
        drivers.forEach(System.out::println);
        clients.forEach(System.out::println);
    }

    private void initializeData() {
        cars.add(new StandardCar(1, "Toyota Prius", "WA 11111", "Biały", 2020, 2.5));
        cars.add(new StandardCar(2, "Skoda Octavia", "WA 22222", "Srebrny", 2021, 2.8));
        cars.add(new PremiumCar(3, "Mercedes E-Class", "W0 BOSS", "Czarny", 2023, 5.0, 0.0));

        admins.add(new Administrator(1, "Admin", "", LocalDate.of(1980,1,1), "M", "admin@taxi.pl", "000000000", "admin123", "KEY-1"));


        drivers.add(new Driver(1, "Ramzan", "Tairov", LocalDate.of(1975,3,10), "M", "ramzantairov@taxi.pl", "500100100", "pass1", "LIC-A", 4500, "Skoda Fabia", 10));
        drivers.add(new Driver(2, "Maksym", "Zawodnik", LocalDate.of(1984,12,7), "M", "maksimZavodnik@taxi.pl", "500100300", "pass3", "LIC-C", 8000, "BMW M3", 15));

        clients.add(new Client(1, "Nikita", "Zotkin", LocalDate.now(), "M", "klient@wp.pl", "999888777", "klient123", "Warszawa Centrum"));
    }
}