package service;
import exceptions.TaxiException;
import model.*;
import enums.Languages;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

public class Menu {

    private List<Administrator> admins = new ArrayList<>();
    private List<Driver> drivers = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();
    private List<String> pendingOrders = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);
    private Person currentUser = null;
    private Languages.Language currentLanguage = Languages.Language.PL;

    public Menu() {
        initializeData();
    }

    public void start() {
        chooseLanguage();

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

    private void chooseLanguage() {
        System.out.println("\nLANGUAGE SELECTION");
        System.out.println("1. Polski");
        System.out.println("2. English");
        System.out.println("3. Русский");
        System.out.print("Choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> currentLanguage = Languages.Language.PL;
            case "2" -> currentLanguage = Languages.Language.EN;
            case "3" -> currentLanguage = Languages.Language.RU;
            default -> currentLanguage = Languages.Language.PL;
        }
    }

    //AUTORYZACJA
    private boolean showAuthMenu() {
        System.out.println("\n" + Languages.WELCOME.get(currentLanguage) + "");
        System.out.println("1. " + Languages.LOGIN.get(currentLanguage));
        System.out.println("2. " + Languages.REGISTER.get(currentLanguage));
        System.out.println("3. " + Languages.CHANGE_LANGUAGE.get(currentLanguage));
        System.out.println("0. " + Languages.EXIT.get(currentLanguage));
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> login();
            case "2" -> register();
            case "3" -> chooseLanguage();
            case "0" -> {
                System.out.println(Languages.BYE.get(currentLanguage));
                return false;
            }
            default -> System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
        }
        return true;
    }

    private void login() {
        System.out.println("\n" + Languages.LOGIN.get(currentLanguage) + "");
        System.out.print(Languages.ENTER_EMAIL.get(currentLanguage));
        String email = scanner.nextLine();
        System.out.print(Languages.ENTER_PASS.get(currentLanguage));
        String password = scanner.nextLine();

        for (Administrator admin : admins) {
            if (admin.getEmail().equals(email) && admin.getPasswordHash().equals(password)) {
                currentUser = admin;
                System.out.println(Languages.LOGIN_SUCCESS.get(currentLanguage));
                return;
            }
        }
        for (Driver driver : drivers) {
            if (driver.getEmail().equals(email) && driver.getPasswordHash().equals(password)) {
                currentUser = driver;
                System.out.println(Languages.LOGIN_SUCCESS.get(currentLanguage));
                return;
            }
        }
        for (Client client : clients) {
            if (client.getEmail().equals(email) && client.getPasswordHash().equals(password)) {
                currentUser = client;
                System.out.println(Languages.LOGIN_SUCCESS.get(currentLanguage));
                return;
            }
        }
        System.out.println(Languages.LOGIN_FAIL.get(currentLanguage));
    }

    private void register() {
        System.out.println("\n" + Languages.REGISTER.get(currentLanguage) + "");
        System.out.println(Languages.CHOOSE_ROLE.get(currentLanguage));
        System.out.println(Languages.ROLE_CLIENT.get(currentLanguage));
        System.out.println(Languages.ROLE_DRIVER.get(currentLanguage));
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        String roleChoice = scanner.nextLine();
        if (!roleChoice.equals("1") && !roleChoice.equals("2")) {
            System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
            return;
        }

        String firstName = getValidInput(Languages.ENTER_NAME.get(currentLanguage), "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", "Format: Abc...");
        String lastName = getValidInput(Languages.ENTER_SURNAME.get(currentLanguage), "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", "Format: Abc...");
        String email = getValidInput("Email", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$", "Invalid Email.");
        String phone = getValidInput("Telefon", "^\\d{9}$", "9 digits.");

        System.out.println(Languages.ENTER_BIRTHDATE.get(currentLanguage));
        LocalDate birthDate = getValidDate();

        System.out.print(Languages.ENTER_PASS.get(currentLanguage));
        String password = scanner.nextLine();

        int newId = (clients.size() + drivers.size() + admins.size()) + 1;

        if (roleChoice.equals("1")) {
            System.out.print("City: ");
            String address = scanner.nextLine();
            clients.add(new Client(newId, firstName, lastName, birthDate, "N/A", email, phone, password, address));
        } else {
            System.out.print("License: ");
            String license = scanner.nextLine();
            System.out.print("Car Model: ");
            String carModel = scanner.nextLine();
            System.out.print("Experience: ");
            int exp = 0;
            try { exp = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}
            drivers.add(new Driver(newId, firstName, lastName, birthDate, "N/A", email, phone, password, license, 0.0, carModel, exp));
        }
        System.out.println(Languages.SUCCESS.get(currentLanguage));
    }

    //MENU KLIENTA
    private void showClientMenu() {
        System.out.println("\n" + Languages.CLIENT_MENU.get(currentLanguage));
        System.out.println("1. " + Languages.ORDER_TAXI.get(currentLanguage));
        System.out.println("2. " + Languages.SHOW_HISTORY.get(currentLanguage));
        System.out.println("3. " + Languages.MY_PROFILE.get(currentLanguage));
        System.out.println("0. " + Languages.LOGOUT.get(currentLanguage));
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1" -> orderTaxi();
                case "2" -> {
                    if (currentUser instanceof Client) ((Client) currentUser).printHistory();
                    else System.out.println(Languages.HISTORY_EMPTY.get(currentLanguage));
                }
                case "3" -> System.out.println(currentUser);
                case "0" -> logout();
                default -> System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
            }
        } catch (TaxiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //MENU KIEROWCY
    private void showDriverMenu() {
        System.out.println("\n" + Languages.DRIVER_MENU.get(currentLanguage));
        System.out.println("1. " + Languages.SHOW_ORDERS.get(currentLanguage));
        System.out.println("2. " + Languages.ACTIVE_ORDER.get(currentLanguage));
        System.out.println("3. " + Languages.NEXT_PAYDAY.get(currentLanguage));
        System.out.println("4. " + Languages.MY_PROFILE.get(currentLanguage));
        System.out.println("0. " + Languages.LOGOUT.get(currentLanguage));
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        Driver driver = (Driver) currentUser;
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> takeOrder(driver);
            case "2" -> System.out.println(Languages.CURRENT_RIDE.get(currentLanguage) + driver.getActiveOrder());
            case "3" -> System.out.println(driver.getNextPayDay());
            case "4" -> System.out.println(driver);
            case "0" -> logout();
            default -> System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
        }
    }

    private void takeOrder(Driver driver) {
        System.out.println("\n--- " + Languages.SHOW_ORDERS.get(currentLanguage) + " ---");
        if (pendingOrders.isEmpty()) {
            System.out.println(Languages.NO_ORDERS.get(currentLanguage));
            return;
        }

        for (int i = 0; i < pendingOrders.size(); i++) {
            System.out.println((i + 1) + ". " + pendingOrders.get(i));
        }

        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < pendingOrders.size()) {
                String order = pendingOrders.remove(index);
                driver.setActiveOrder(order);
                System.out.println(Languages.ORDER_TAKEN.get(currentLanguage) + order);
            }
        } catch (NumberFormatException e) {
            System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
        }
    }

    //MENU ADMINA
    private void showAdminMenu() {
        System.out.println("\n" + Languages.ADMIN_MENU.get(currentLanguage));
        System.out.println("1. " + Languages.SHOW_USERS.get(currentLanguage));
        System.out.println("2. " + Languages.SHOW_FLEET.get(currentLanguage));
        System.out.println("3. " + Languages.DELETE_CLIENT.get(currentLanguage));
        System.out.println("4. " + Languages.DELETE_DRIVER.get(currentLanguage));
        System.out.println("5. " + Languages.CHANGE_PRICE.get(currentLanguage));
        System.out.println("0. " + Languages.LOGOUT.get(currentLanguage));
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> showAllUsers();
            case "2" -> showCars();
            case "3" -> deleteClient();
            case "4" -> deleteDriver();
            case "5" -> changePrice();
            case "0" -> logout();
            default -> System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
        }
    }

    private void deleteClient() {
        System.out.print(Languages.ENTER_ID.get(currentLanguage));
        try {
            int id = Integer.parseInt(scanner.nextLine());
            clients.removeIf(c -> c.getId() == id);
            System.out.println(Languages.DELETED.get(currentLanguage));
        } catch (Exception e) { System.out.println(Languages.ERROR_ID.get(currentLanguage)); }
    }

    private void deleteDriver() {
        System.out.print(Languages.ENTER_ID.get(currentLanguage));
        try {
            int id = Integer.parseInt(scanner.nextLine());
            drivers.removeIf(d -> d.getId() == id);
            System.out.println(Languages.DELETED.get(currentLanguage));
        } catch (Exception e) { System.out.println(Languages.ERROR_ID.get(currentLanguage)); }
    }

    private void changePrice() {
        showCars();
        System.out.print(Languages.ENTER_ID.get(currentLanguage));
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Car c = cars.stream().filter(car -> car.getId() == id).findFirst().orElse(null);
            if (c != null) {
                System.out.print(Languages.NEW_PRICE.get(currentLanguage));
                double p = Double.parseDouble(scanner.nextLine());
                c.setPricePerKm(p);
                System.out.println(Languages.CHANGED.get(currentLanguage));
            } else {
                System.out.println(Languages.NOT_FOUND.get(currentLanguage));
            }
        } catch (Exception e) { System.out.println(Languages.ERROR_ID.get(currentLanguage)); }
    }

    private void logout() {
        currentUser = null;
        System.out.println(Languages.LOGOUT.get(currentLanguage) + " OK.");
    }

    private String getValidInput(String fieldName, String regex, String errorMessage) {
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            System.out.print(fieldName + ": ");
            String input = scanner.nextLine();
            if (pattern.matcher(input).matches()) return input;
            System.out.println("Error: " + errorMessage);
        }
    }

    private LocalDate getValidDate() {
        while (true) {
            System.out.print("YYYY-MM-DD: ");
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Format: YYYY-MM-DD");
            }
        }
    }

    private void orderTaxi() throws TaxiException {
        System.out.println("\n" + Languages.ORDER_TAXI.get(currentLanguage) + "");
        System.out.print(Languages.ENTER_ADDRESS.get(currentLanguage));
        String destination = scanner.nextLine();

        System.out.print(Languages.ENTER_DISTANCE.get(currentLanguage));
        double km;
        try {
            km = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
            return;
        }

        showCars();
        System.out.print(Languages.CHOOSE_TARIFF.get(currentLanguage));
        int carId;
        try {
            carId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) { return; }

        Car selectedCar = null;
        for (Car car : cars) {
            if (car.getId() == carId) { selectedCar = car; break; }
        }

        if (selectedCar == null) throw new TaxiException(Languages.NOT_FOUND.get(currentLanguage));

        double price = selectedCar.calculatePrice(km);
        System.out.println(Languages.PRICE.get(currentLanguage) + price + " PLN");

        System.out.print(Languages.CONFIRM_ORDER.get(currentLanguage));
        if (scanner.nextLine().equalsIgnoreCase("t")) {
            Payment p = new Payment(UUID.randomUUID().toString(), price, "TX-" + System.currentTimeMillis());
            p.processPayment();

            if (currentUser instanceof Client) {
                String log = LocalDate.now() + " | " + selectedCar.getBrand() + " | " + destination + " | " + price + " PLN";
                ((Client) currentUser).addRideToHistory(log);
            }

            String orderForDriver = "Zlecenie: " + destination + " (" + km + "km) - Zarobek: " + (price * 0.7) + " PLN";
            pendingOrders.add(orderForDriver);

            System.out.println(Languages.ORDER_SENT.get(currentLanguage));
        } else {
            System.out.println(Languages.ORDER_CANCEL.get(currentLanguage));
        }
    }

    private void showCars() {
        System.out.println("\n" + Languages.SHOW_FLEET.get(currentLanguage));
        for (Car car : cars) System.out.println(car);
    }

    private void showAllUsers() {
        System.out.println("\n" + Languages.SHOW_USERS.get(currentLanguage));
        admins.forEach(System.out::println);
        drivers.forEach(System.out::println);
        clients.forEach(System.out::println);
    }

    private void initializeData() {
        cars.add(new StandardCar(1, "Toyota Prius", "WA 71586", "Biały", 2020, 2.5));
        cars.add(new StandardCar(2, "Volkswagen Tiguan ", "WA 71585", "Srebrny", 2021, 2.8));
        cars.add(new PremiumCar(3, "BMW M5", "W0 77777", "Czarny", 2023, 5.0, 3.0));

        admins.add(new Administrator(1, "Admin", "", LocalDate.of(1980,1,1), "M", "admin@taxi.pl", "000000000", "admin123", "KEY-1"));

        drivers.add(new Driver(1, "Ramzan", "Tairov", LocalDate.of(1975,3,10), "M", "ramzantairov@taxi.pl", "500100100", "pass1", "LIC-A", 4500, "Skoda Fabia", 10));
        drivers.add(new Driver(2, "Maksym", "Zawodnik", LocalDate.of(1984,12,7), "M", "maksimZavodnik@taxi.pl", "500100300", "pass3", "LIC-C", 8000, "BMW M3", 15));

        clients.add(new Client(1, "Nikita", "Zotkin", LocalDate.now(), "M", "klient@wp.pl", "999888777", "klient123", "Warszawa Centrum"));
    }
}