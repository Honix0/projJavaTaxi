package enums;

public enum Languages {
    MENU("MENU", "MENU", "МЕНЮ"),
    REGISTER("Rejestracja", "Register", "Регистрация"),
    WELCOME("Witaj!", "Welcome!", "Привет!"),
    ENTER_NAME("Wpisz imię:", "Enter name:", "Введите имя:"),
    ENTER_SURNAME("Wpisz nazwisko:", "Enter surname:", "Введите фамилию:"),
    ENTER_BIRTHDATE("Wprowadź datę urodzenia (rok-miesiąc-dzień):", "Enter birthdate (year-month-day):", "Введите дату рождения (год-месяц-день):"),
    LOGIN_SUCCESS("Logowanie udane!", "Login successful!", "Успешный вход!"),
    LOGIN_FAIL("Błąd logowania!", "Login failed!", "Ошибка входа!");

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
            default: return en;
        }
    }

}
