package service;
import exceptions.TaxiException; // Wymaganie: #7
import model.*;
import enums.Languages;
import enums.OrderStatus;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

public class Menu {

    // Wymaganie: #16 Kolekcje
    private List<Administrator> admins = new ArrayList<>();
    private List<Driver> drivers = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();
    private List<Order> pendingOrders = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);
    private Person currentUser = null; // Wymaganie: #5 Upcasting
    private Languages.Language currentLanguage = Languages.Language.PL;

    // Wymaganie: #6 Metoda statyczna #2
    public static void logMessage(String message) { // Wymaganie: #12
        System.out.println("[LOG]: " + message);
    }

    public Menu() {
        initializeData();
        // Wymaganie: #12 Wywołanie metody statycznej
        logMessage("Aplikacja została uruchomiona i dane początkowe załadowane.");
    }

    public void start() {
        // Wymaganie Użytkownika: Wybór języka przy starcie aplikacji
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
                currentUser = admin; // Wymaganie: #5 Upcasting
                System.out.println(Languages.LOGIN_SUCCESS.get(currentLanguage));
                return;
            }
        }
        for (Driver driver : drivers) {
            if (driver.getEmail().equals(email) && driver.getPasswordHash().equals(password)) {
                currentUser = driver; // Wymaganie: #5 Upcasting
                System.out.println(Languages.LOGIN_SUCCESS.get(currentLanguage));
                return;
            }
        }
        for (Client client : clients) {
            if (client.getEmail().equals(email) && client.getPasswordHash().equals(password)) {
                currentUser = client; // Wymaganie: #5 Upcasting
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

        // DODANO: Zapytanie o płeć z walidacją na M, K, I
        String sex = getValidInput("Płeć (M/K/I):", "^[MKI]$", "Wprowadź M (Mężczyzna), K (Kobieta) lub I (Inne).");

        System.out.println(Languages.ENTER_BIRTHDATE.get(currentLanguage));
        LocalDate birthDate = getValidDate();

        System.out.print(Languages.ENTER_PASS.get(currentLanguage));
        String password = scanner.nextLine();

        // Wymaganie: #6 Wywołanie statycznej metody (generowanie ID)
        int newId = Person.generateNextId();

        if (roleChoice.equals("1")) {
            System.out.print("City: ");
            String address = scanner.nextLine();
            // ZMIANA: Użycie zmiennej 'sex'
            clients.add(new Client(newId, firstName, lastName, birthDate, sex, email, phone, password, address));
        } else {
            // Rejestracja kierowcy
            System.out.print("License: ");
            String license = scanner.nextLine();

            System.out.print("Car Model (np. Skoda Fabia): ");
            String carModelName = scanner.nextLine();

            // Wymaganie: #8 Obsługa wyjątków (NumberFormatException)
            System.out.print("Experience: ");
            int exp = 0;
            try {
                exp = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                logMessage("Błąd parsowania doświadczenia. Ustawiono 0.");
            }

            // DODANO: Tworzenie dedykowanego obiektu Car dla nowego kierowcy
            Car newCar = new StandardCar(cars.size() + 100 + newId, carModelName, "NOWY-" + (cars.size() + 100 + newId), "Biały", LocalDate.now().getYear(), 2.5);

            drivers.add(new Driver(newId, firstName, lastName, birthDate, sex, email, phone, password, license, 0.0, newCar, exp)); // ZMIANA: Użycie zmiennej 'sex'
        }
        System.out.println(Languages.SUCCESS.get(currentLanguage));
    }

    //MENU KLIENTA
    private void showClientMenu() {
        System.out.println("\n" + Languages.CLIENT_MENU.get(currentLanguage));
        System.out.println("1. " + Languages.ORDER_TAXI.get(currentLanguage));
        System.out.println("2. " + Languages.SHOW_HISTORY.get(currentLanguage));
        System.out.println("3. " + Languages.MY_PROFILE.get(currentLanguage));
        System.out.println("4. STATUS ZAMÓWIENIA / ANULUJ");
        System.out.println("0. " + Languages.LOGOUT.get(currentLanguage));
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        String choice = scanner.nextLine();
        try { // Wymaganie: #8 Obsługa wyjątku użytkownika
            switch (choice) {
                case "1" -> orderTaxi();
                case "2" -> {
                    if (currentUser instanceof Client) ((Client) currentUser).printHistory(); // Wymaganie: #5 Downcasting, #12 Wywołanie
                    else System.out.println(Languages.HISTORY_EMPTY.get(currentLanguage));
                }
                case "3" -> {
                    System.out.println(currentUser);
                    // Wymaganie: #13 Wywołanie metody z interfejsu IPrintable
                    System.out.println("Krótkie info (Interfejs): " + ((IPrintable)currentUser).getShortInfo()); // Wymaganie: #12
                }
                case "4" -> checkOrderStatusAndCancel();
                case "0" -> logout();
                default -> System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
            }
        } catch (TaxiException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void checkOrderStatusAndCancel() throws TaxiException {
        Client client = (Client) currentUser; // Wymaganie: #5 Downcasting
        Order activeOrder = client.getActiveOrder();

        if (activeOrder == null) {
            System.out.println("Nie masz aktywnego zamówienia.");
            return;
        }

        System.out.println("\n--- STATUS TWOJEGO ZAMÓWIENIA ---");
        System.out.println(activeOrder);
        System.out.println("Status: " + activeOrder.getStatus().getDetailedDescription());
        System.out.println("---------------------------------");

        // Klient może anulować tylko, jeśli zamówienie jest PENDING
        if (activeOrder.getStatus() == OrderStatus.PENDING) {
            System.out.print("Czy chcesz anulować zamówienie? (t/n): ");
            if (scanner.nextLine().equalsIgnoreCase("t")) {
                activeOrder.setStatus(OrderStatus.CANCELLED); // Wymaganie: #12 Zmiana statusu
                client.setActiveOrder(null);
                pendingOrders.remove(activeOrder); // Usunięcie z puli oczekujących
                System.out.println("Zamówienie " + activeOrder.getOrderId() + " zostało anulowane.");
                return;
            }
        } else if (activeOrder.getStatus() == OrderStatus.ACCEPTED || activeOrder.getStatus() == OrderStatus.IN_PROGRESS) {
            System.out.println("Nie można anulować. Kurs jest w trakcie realizacji (kierowca już jedzie).");
        }
    }


    //MENU KIEROWCY
    private void showDriverMenu() {
        System.out.println("\n" + Languages.DRIVER_MENU.get(currentLanguage));
        System.out.println("1. " + Languages.SHOW_ORDERS.get(currentLanguage));
        System.out.println("2. STATUS AKTYWNEGO ZAMÓWIENIA");
        System.out.println("3. ZAKOŃCZ AKTYWNE ZAMÓWIENIE");
        System.out.println("4. " + Languages.NEXT_PAYDAY.get(currentLanguage));
        System.out.println("5. " + Languages.MY_PROFILE.get(currentLanguage));
        System.out.println("0. " + Languages.LOGOUT.get(currentLanguage));
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        Driver driver = (Driver) currentUser; // Wymaganie: #5 Downcasting
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> takeOrder(driver);
            case "2" -> {
                if (driver.getActiveOrder() != null) System.out.println(Languages.CURRENT_RIDE.get(currentLanguage) + driver.getActiveOrder());
                else System.out.println("Brak aktywnego zamówienia.");
            }
            case "3" -> completeOrder(driver);
            case "4" -> System.out.println(driver.getNextPayDay());
            case "5" -> System.out.println(driver);
            case "0" -> logout();
            default -> System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
        }
    }

    private void takeOrder(Driver driver) {
        if (driver.getActiveOrder() != null) {
            System.out.println("Musisz najpierw zakończyć aktywny kurs.");
            return;
        }

        System.out.println("\n--- " + Languages.SHOW_ORDERS.get(currentLanguage) + " ---");
        if (pendingOrders.isEmpty()) {
            System.out.println(Languages.NO_ORDERS.get(currentLanguage));
            return;
        }

        for (int i = 0; i < pendingOrders.size(); i++) { // Wymaganie: #4 Pętla #1
            Order order = pendingOrders.get(i);
            double zarobek = order.getFinalPrice() * 0.7;
            // Wyświetla informacje o taryfie (aucie wybranym przez klienta)
            System.out.printf("%d. [ID: %s] %s -> %s (%.1f km). Taryfa: %s. Zarobek: %.2f PLN%n",
                    (i + 1), order.getOrderId(), order.getClient().getFirstName(),
                    order.getDestination(), order.getDistanceKm(), order.getCarTariff().getBrand(), zarobek);
        }

        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));
        try { // Wymaganie: #8 Obsługa wyjątków
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < pendingOrders.size()) {
                Order acceptedOrder = pendingOrders.remove(index);

                acceptedOrder.assignDriver(driver); // Wymaganie: #12 Ustawia status na ACCEPTED i Driver.activeOrder
                acceptedOrder.getClient().setActiveOrder(acceptedOrder);

                System.out.println(Languages.ORDER_TAKEN.get(currentLanguage) + acceptedOrder.getOrderId());
            } else {
                System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
            }
        } catch (NumberFormatException e) {
            System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
        }
    }

    private void completeOrder(Driver driver) {
        Order activeOrder = driver.getActiveOrder();

        if (activeOrder == null) {
            System.out.println("Brak aktywnego zamówienia do zakończenia.");
            return;
        }

        System.out.println("Kończysz przejazd: " + activeOrder); // Wyświetli dane kierowcy i jego auta
        System.out.print("Czy na pewno zakończyć kurs i rozliczyć klienta? (t/n): ");
        if (scanner.nextLine().equalsIgnoreCase("t")) {

            activeOrder.setStatus(OrderStatus.COMPLETED); // Wymaganie: #12 Zmiana statusu

            Client client = activeOrder.getClient();
            String log = LocalDate.now() + " | " + activeOrder.getCarTariff().getBrand() + " | " + activeOrder.getDestination() + " | " + activeOrder.getFinalPrice() + " PLN";
            client.addRideToHistory(log); // Wymaganie: #12 Wywołanie metody z funkcjonalnością
            client.setActiveOrder(null); // Wymaganie: #12

            System.out.println("Zakończono. Klient rozliczony. Status: " + activeOrder.getStatus().getDescription());
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
        System.out.println("6. Pokaż flotę (Sortowanie Comparable: Cena/Km)"); // Wymaganie: #14
        System.out.println("7. Pokaż flotę (Sortowanie Comparator: Rocznik)"); // Wymaganie: #14
        System.out.println("8. Pokaż krótkie info (Metoda generyczna dla kierowców)"); // Wymaganie: #15
        System.out.println("0. " + Languages.LOGOUT.get(currentLanguage));
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> showAllUsers();
            case "2" -> showCars();
            case "3" -> deleteClient();
            case "4" -> deleteDriver();
            case "5" -> changePrice();
            case "6" -> sortCarsByComparable(); // WYWOŁANIE SORTOWANIA COMPARABLE
            case "7" -> sortCarsByComparator(new CarComparator()); // WYWOŁANIE SORTOWANIA COMPARATOR
            case "8" -> printGenericList("Kierowcy", drivers); // Wymaganie: #15
            case "0" -> logout();
            default -> System.out.println(Languages.INVALID_OPTION.get(currentLanguage));
        }
    }

    private void deleteClient() {
        System.out.print(Languages.ENTER_ID.get(currentLanguage));
        try { // Wymaganie: #8
            int id = Integer.parseInt(scanner.nextLine());
            boolean wasDeleted = clients.removeIf(c -> c.getId() == id);
            if (!wasDeleted) {
                throw new TaxiException(Languages.NOT_FOUND.get(currentLanguage) + " Client ID: " + id); // Wymaganie: #7
            }
            System.out.println(Languages.DELETED.get(currentLanguage));
        } catch (NumberFormatException e) {
            System.out.println(Languages.ERROR_ID.get(currentLanguage));
        } catch (TaxiException e) { // Wymaganie: #8
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void deleteDriver() {
        System.out.print(Languages.ENTER_ID.get(currentLanguage));
        try { // Wymaganie: #8
            int id = Integer.parseInt(scanner.nextLine());
            boolean wasDeleted = drivers.removeIf(d -> d.getId() == id);
            if (!wasDeleted) {
                throw new TaxiException(Languages.NOT_FOUND.get(currentLanguage) + " Driver ID: " + id); // Wymaganie: #7
            }
            System.out.println(Languages.DELETED.get(currentLanguage));
        } catch (NumberFormatException e) {
            System.out.println(Languages.ERROR_ID.get(currentLanguage));
        } catch (TaxiException e) { // Wymaganie: #8
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void changePrice() {
        showCars();
        System.out.print(Languages.ENTER_ID.get(currentLanguage));
        try { // Wymaganie: #8
            int id = Integer.parseInt(scanner.nextLine());
            Car c = cars.stream().filter(car -> car.getId() == id).findFirst().orElse(null);
            if (c != null) {
                System.out.print(Languages.NEW_PRICE.get(currentLanguage));
                double p = Double.parseDouble(scanner.nextLine());
                c.setPricePerKm(p); // Wymaganie: #12
                System.out.println(Languages.CHANGED.get(currentLanguage));
            } else {
                throw new TaxiException(Languages.NOT_FOUND.get(currentLanguage)); // Wymaganie: #7
            }
        } catch (NumberFormatException e) {
            System.out.println(Languages.ERROR_ID.get(currentLanguage));
        } catch (TaxiException e) { // Wymaganie: #8
            System.out.println("Błąd: " + e.getMessage());
        }
    }
    private void logout() { currentUser = null; System.out.println(Languages.LOGOUT.get(currentLanguage) + " OK."); }

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

    // Wymaganie: #14 Sortowanie Comparable
    private void sortCarsByComparable() {
        System.out.println("\n--- FLOTA - SORTOWANIE (Comparable: Cena/Km) ---");
        // Sortowanie domyślne (Comparable), zaimplementowane w Car.compareTo()
        Collections.sort(cars);
        for (Car car : cars) System.out.println(car);
    }

    // Wymaganie: #14 Sortowanie Comparator
    private void sortCarsByComparator(Comparator<Car> comparator) {
        System.out.println("\n--- FLOTA - SORTOWANIE (Comparator: Rocznik) ---");
        // Sortowanie z użyciem zewnętrznego komparatora (CarComparator)
        cars.sort(comparator);
        for (Car car : cars) System.out.println(car);
    }

    // Wymaganie: #15 Metoda generyczna
    public <T extends Person & IPrintable> void printGenericList(String title, List<T> list) {
        System.out.println("\n--- " + title.toUpperCase() + " (METODA GENERYCZNA) ---");
        for (T item : list) {
            System.out.println(item.getShortInfo());
        }
    }

    // DODANO: Metoda pomocnicza do sortowania dostępnych aut w trakcie zamawiania
    private void sortAvailableCars(List<Car> carsToSort) { // Wymaganie: #14
        System.out.println("\n--- WYBÓR SORTOWANIA TARYF ---");
        System.out.println("1. Sortuj według Ceny/Km (Domyślne - Comparable)");
        System.out.println("2. Sortuj według Rocznika (Comparator)");
        System.out.print(Languages.CHOOSE_OPTION.get(currentLanguage));

        String choice = scanner.nextLine();

        try { // Wymaganie: #8 Obsługa wyjątków
            switch (choice) {
                case "1":
                    // Sortowanie Comparable (Cena/Km)
                    Collections.sort(carsToSort);
                    System.out.println("Taryfy posortowane według Ceny/Km (rosnąco).");
                    break;
                case "2":
                    // Sortowanie Comparator (Rocznik - wymaga CarComparator)
                    carsToSort.sort(new CarComparator()); // Wymaganie: #14
                    System.out.println("Taryfy posortowane według Rocznika (od najstarszego).");
                    break;
                default:
                    Collections.sort(carsToSort);
                    System.out.println("Wybrano domyślne sortowanie (Cena/Km).");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Błąd sortowania. Użyto domyślnego.");
            Collections.sort(carsToSort);
        }
    }


    // Tworzenie zamówienia przez Klienta
    private void orderTaxi() throws TaxiException { // Wymaganie: #7

        Client currentClient = (Client) currentUser; // Wymaganie: #5 Downcasting
        if (currentClient.getActiveOrder() != null) {
            throw new TaxiException("Już masz aktywne zamówienie! Status: " + currentClient.getActiveOrder().getStatus().getDescription()); // Wymaganie: #7
        }

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

        // Filtrowanie floty TYLKO na podstawie minimalnego dystansu dla Minibusów
        List<Car> availableCars = new ArrayList<>(); // Wymaganie: #16
        for (Car car : cars) { // Wymaganie: #4 Pętla #4

            if (!car.getType().isAvailableForShortTrips() && km < 10) { // Wymaganie: #12
                System.out.println("  [UWAGA: " + car.getBrand() + " nie jest dostępne dla kursów poniżej 10km.]");
                continue;
            }

            availableCars.add(car);
        }

        if (availableCars.isEmpty()) {
            throw new TaxiException(Languages.NO_CARS.get(currentLanguage) + " - brak aut spełniających Twoje kryteria (np. zbyt krótki dystans)."); // Wymaganie: #7
        }

        // Wymaganie: #14 Wywołanie sortowania przed wyświetleniem
        sortAvailableCars(availableCars);

        // Wyświetlanie dostępnych aut
        System.out.println("\nDOSTĘPNE TYPY SAMOCHODÓW:");
        for (Car car : availableCars) {
            System.out.println(car);
            // Wyświetlamy Max. pasażerów jako informację dla klienta
            System.out.println("   [Opis: " + car.getType().getDetailedDescription() +
                    ", Max. pasażerów: " + car.getType().getMaxPassengers() +
                    ", Min. bagażnik: " + car.getType().getMinTrunkCapacity() + " L]"); // Wymaganie: #12 Wywołanie
        }

        System.out.print(Languages.CHOOSE_TARIFF.get(currentLanguage));
        int carId;
        try {
            carId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) { return; }

        Car selectedCar = null;
        for (Car car : availableCars) {
            if (car.getId() == carId) { selectedCar = car; break; }
        }

        if (selectedCar == null) throw new TaxiException(Languages.NOT_FOUND.get(currentLanguage) + " lub auto nie spełnia wymagań."); // Wymaganie: #7

        double price = selectedCar.calculatePrice(km); // Wymaganie: #12
        System.out.println(Languages.PRICE.get(currentLanguage) + price + " PLN");

        System.out.print(Languages.CONFIRM_ORDER.get(currentLanguage));
        if (scanner.nextLine().equalsIgnoreCase("t")) {

            Payment p = new Payment(price);
            p.processPayment(); // Wymaganie: #12

            // 1. Utworzenie nowego obiektu Order
            Order newOrder = new Order(currentClient, destination, km, price, selectedCar); // Wymaganie: #12

            // 2. Przypisanie zamówienia do Klienta
            currentClient.setActiveOrder(newOrder); // Wymaganie: #12

            // 3. Dodanie do puli oczekujących dla kierowców
            pendingOrders.add(newOrder);

            System.out.println(Languages.ORDER_SENT.get(currentLanguage));
            System.out.println("Status: " + newOrder.getStatus().getDescription());
        } else {
            System.out.println(Languages.ORDER_CANCEL.get(currentLanguage));
        }
    }

    private void showCars() {
        System.out.println("\n" + Languages.SHOW_FLEET.get(currentLanguage));

        // Wymaganie: #4 Tablica #1 i Pętla #5
        System.out.println("\n--- Marki samochodów w tablicy (Wymaganie #4) ---");
        String[] carBrands = new String[cars.size()];
        for (int i = 0; i < cars.size(); i++) { // Pętla #5
            carBrands[i] = cars.get(i).getBrand();
            System.out.print(carBrands[i] + (i < cars.size() - 1 ? ", " : ""));
        }
        System.out.println("\n---------------------------------------------------");

        for (Car car : cars) {
            System.out.println(car);
            System.out.println("   [Opis szczegółowy: " + car.getType().getDetailedDescription() + "]"); // Wymaganie: #12
        }

        // Wymaganie: #6 i #12 Wywołanie statycznej metody Car.printCarInfo()
        Car.printCarInfo(cars.get(0));
    }

    private void showAllUsers() {
        System.out.println("\n" + Languages.SHOW_USERS.get(currentLanguage));
        admins.forEach(System.out::println);
        drivers.forEach(System.out::println);
        clients.forEach(System.out::println);

        // Wymaganie: #4 Tablica #2 i Tablica #3
        String[] clientEmails = clients.stream().map(Person::getEmail).toArray(String[]::new); // Tablica #2
        String[] driverEmails = drivers.stream().map(Person::getEmail).toArray(String[]::new); // Tablica #3

        System.out.println("\nEmails klientów (Wymaganie #4, Tablica #2): " + Arrays.toString(clientEmails));
        System.out.println("Emails kierowców (Wymaganie #4, Tablica #3): " + Arrays.toString(driverEmails));
    }

    private void initializeData() {
        // Wymaganie: #6 Ustawienie Person.nextId


        // --- INICJALIZACJA SAMOCHODÓW (BĘDĄ SŁUŻYŁY JAKO AUTA KIEROWCÓW I TARYFY) ---
        Car toyota = new StandardCar(Person.generateNextId(), "Toyota Prius", "WA 71586", "Biały", 2020, 2.5); // ID 1
        Car vw = new StandardCar(Person.generateNextId(), "Volkswagen Tiguan ", "WA 71585", "Srebrny", 2021, 2.8); // ID 2
        Car bmw = new PremiumCar(Person.generateNextId(), "BMW M5", "W0 77777", "Czarny", 2023, 5.0, 3.0);
        Car mercedes = new PremiumCar(Person.generateNextId(), "Mercedes S-Class", "WW M2222", "Srebrny", 2024, 6.5, 4.0);
        Car transit = new VanCar(Person.generateNextId(), "Ford Transit", "WK 90909", "Niebieski", 2018, 3.0, 1.5);
        Car renault = new VanCar(Person.generateNextId(), "Renault Trafic", "WI 11111", "Szary", 2019, 3.2, 1.7);
        Car fiat = new MinibusCar(Person.generateNextId(), "Fiat Ducato", "WB 88888", "Biały", 2017, 3.5, 5.0);
        Car iveco = new MinibusCar(Person.generateNextId(), "Iveco Daily", "WB 99999", "Czerwony", 2016, 3.8, 6.0);
        Car tesla = new EcoCar(Person.generateNextId(), "Tesla Model 3", "WE 00000", "Biały", 2024, 2.0, 0.5);
        Car nissan = new EcoCar(Person.generateNextId(), "Nissan Leaf", "WE 10101", "Zielony", 2022, 1.8, 0.3);

        // Wymaganie: #16 Kolekcja aut
        cars.addAll(Arrays.asList(toyota, vw, bmw, mercedes, transit, renault, fiat, iveco, tesla, nissan));

        // Inicjalizacja użytkowników (ID 11-16) - Wymaganie #18
        admins.add(new Administrator(Person.generateNextId(), "Admin", "", LocalDate.of(1980,1,1), Person.DEFAULT_SEX, "admin@taxi.pl", "000000000", "admin123", "KEY-1"));
        admins.add(new Administrator(Person.generateNextId(), "Super", "User", LocalDate.of(1990,2,2), Person.DEFAULT_SEX, "super@taxi.pl", "111111111", "super123", "KEY-2"));

        // --- INICJALIZACJA KIEROWCÓW I PRZYPISANIE AUT ---
        // Wymaganie Użytkownika: przypisanie samochodu do kierowcy
        drivers.add(new Driver(Person.generateNextId(), "Ramzan", "Tairov", LocalDate.of(1975,3,10), "M", "ramzantairov@taxi.pl", "500100100", "pass1", "LIC-A", 4500, toyota, 10)); // PRZYPISANO TOYOTĘ
        drivers.add(new Driver(Person.generateNextId(), "Maksym", "Zawodnik", LocalDate.of(1984,12,7), "M", "maksimZavodnik@taxi.pl", "500100300", "pass3", "LIC-C", 8000, vw, 15)); // PRZYPISANO VW

        clients.add(new Client(Person.generateNextId(), "Nikita", "Zotkin", LocalDate.now(), "M", "klient@wp.pl", "999888777", "klient123", "Warszawa Centrum"));
        clients.add(new Client(Person.generateNextId(), "Anna", "Kowalska", LocalDate.of(1995,5,15), "K", "anna@wp.pl", "123456789", "anna123", "Kraków"));
    }
}