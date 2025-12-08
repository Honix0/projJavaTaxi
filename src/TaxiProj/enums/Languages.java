package enums;

public enum Languages {
    MENU("MENU", "MENU", "МЕНЮ"),
    WELCOME("Witaj w aplikacji Taxi!", "Welcome to Taxi App!", "Добро пожаловать в Такси!"),
    LOGIN("Zaloguj się", "Login", "Войти"),
    REGISTER("Rejestracja", "Register", "Регистрация"),
    EXIT("Wyjście", "Exit", "Выход"),
    CHOOSE_OPTION("Wybierz opcję: ", "Choose option: ", "Выберите опцию: "),
    CHANGE_LANGUAGE("Zmień język", "Change language", "Сменить язык"),
    INVALID_OPTION("Niepoprawna opcja.", "Invalid option.", "Неверная опция."),
    LOGOUT("Wyloguj", "Logout", "Выйти из аккауnta"),
    BYE("Do widzenia!", "Goodbye!", "До свидания!"),
    SUCCESS("Sukces!", "Success!", "Успешно!"),
    ERROR_ID("Błąd: ID musi być liczbą.", "Error: ID must be a number.", "Ошибка: ID должен быть числом."),
    NOT_FOUND("Nie znaleziono.", "Not found.", "Не найдено."),

    //LOGOWANIE I REJESTRACJA
    ENTER_EMAIL("Podaj Email: ", "Enter Email: ", "Введите Email: "),
    ENTER_PASS("Podaj Hasło: ", "Enter Password: ", "Введите Пароль: "),
    LOGIN_SUCCESS("Logowanie udane!", "Login successful!", "Успешный вход!"),
    LOGIN_FAIL("Błąd logowania!", "Login failed!", "Ошибка входа!"),
    ENTER_NAME("Wpisz imię:", "Enter name:", "Введите имя:"),
    ENTER_SURNAME("Wpisz nazwisko:", "Enter surname:", "Введите фамилию:"),
    ENTER_BIRTHDATE("Data urodzenia (RRRR-MM-DD):", "Birthdate (YYYY-MM-DD):", "Дата рождения (ГГГГ-ММ-ДД):"),
    CHOOSE_ROLE("Kim chcesz zostać?", "Choose role?", "Кем хотите стать?"),
    ROLE_CLIENT("1. Klient", "1. Client", "1. Клиент"),
    ROLE_DRIVER("2. Kierowca", "2. Driver", "2. Водитель"),

    //MENU KLIENTA
    CLIENT_MENU("--- MENU KLIENTA ---", "--- CLIENT MENU ---", "--- МЕНЮ КЛИЕНТА ---"),
    ORDER_TAXI("Zamów taksówkę", "Order Taxi", "Заказать такси"),
    SHOW_HISTORY("Historia przejazdów", "Ride History", "История поездок"),
    MY_PROFILE("Mój profil", "My Profile", "Мой профиль"),
    HISTORY_EMPTY("Historia jest pusta.", "History is empty.", "История пуста."),

    //ZAMAWIANIE
    ENTER_ADDRESS("Podaj adres: ", "Enter address: ", "Введите адрес: "),
    ENTER_DISTANCE("Dystans (km): ", "Distance (km): ", "Расстояние (км): "),
    CHOOSE_TARIFF("Wybierz ID taryfy: ", "Choose Tariff ID: ", "Выберите ID тарифа: "),
    PRICE("CENA: ", "PRICE: ", "ЦЕНА: "),
    CONFIRM_ORDER("Zamawiasz? (t/n): ", "Confirm? (y/n): ", "Заказываете? (t/n): "),
    ORDER_SENT("Wysłano do kierowców!", "Sent to drivers!", "Отправлено водителям!"),
    ORDER_CANCEL("Anulowano.", "Cancelled.", "Отменено."),
    NO_CARS("Brak aut.", "No cars available.", "Нет машин."),

    //MENU KIEROWCY
    DRIVER_MENU("--- MENU KIEROWCY ---", "--- DRIVER MENU ---", "--- МЕНЮ ВОДИТЕЛЯ ---"),
    SHOW_ORDERS("Dostępne zlecenia", "Available Orders", "Доступные заказы"),
    ACTIVE_ORDER("Mój aktywny przejazd", "My Active Ride", "Мой активный заказ"),
    NEXT_PAYDAY("Kiedy wypłata?", "Next Payday?", "Когда зарплата?"),
    NO_ORDERS("Brak zleceń.", "No orders.", "Нет заказов."),
    ORDER_TAKEN("Przyjąłeś zlecenie: ", "Order taken: ", "Вы приняли заказ: "),
    CURRENT_RIDE("AKTUALNY KURS: ", "CURRENT RIDE: ", "ТЕКУЩИЙ ЗАКАЗ: "),

    //MENU ADMINA
    ADMIN_MENU("--- MENU ADMINA ---", "--- ADMIN MENU ---", "--- МЕНЮ АДМИНА ---"),
    SHOW_USERS("Pokaż użytkowników", "Show Users", "Показать пользователей"),
    SHOW_FLEET("Pokaż flotę", "Show Fleet", "Показать автопарк"),
    DELETE_CLIENT("Usuń klienta", "Delete Client", "Удалить клиента"),
    DELETE_DRIVER("Usuń kierowcę", "Delete Driver", "Удалить водителя"),
    CHANGE_PRICE("Zmień cennik", "Change Price", "Изменить цены"),
    ENTER_ID("Podaj ID: ", "Enter ID: ", "Введите ID: "),
    DELETED("Usunięto.", "Deleted.", "Удалено."),
    NEW_PRICE("Nowa cena: ", "New price: ", "Новая цена: "),
    CHANGED("Zmieniono.", "Changed.", "Изменено.");

    private final String pl;
    private final String en;
    private final String ru;

    Languages(String pl, String en, String ru) {
        this.pl = pl;
        this.en = en;
        this.ru = ru;
    }

    public enum Language {
        PL, EN, RU
    }

    public String get(Language lang) {
        switch (lang) {
            case PL: return pl;
            case EN: return en;
            case RU: return ru;
            default: return pl;
        }
    }
}