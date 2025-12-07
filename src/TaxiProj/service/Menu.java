//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package service;

import exceptions.RentalException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import model.Car;
import model.Klient;
import model.Payment;
import model.PremiumCar;
import model.StandardCar;

public class Menu {
    private List<Car> cars = new ArrayList();
    private List<Klient> clients = new ArrayList();
    private Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.initializeData();
    }

    public void start() {
        boolean isRunning = true;

        while(isRunning) {
            this.printMenu();

            try {
                int choice = Integer.parseInt(this.scanner.nextLine());
                switch (choice) {
                    case 0:
                        System.out.println("Zamykanie systemu...");
                        isRunning = false;
                        break;
                    case 1:
                        this.showCars();
                        break;
                    case 2:
                        this.sortCarsByPrice();
                        break;
                    case 3:
                        this.rentCar();
                        break;
                    case 4:
                        this.showClients();
                        break;
                    case 5:
                        this.addClient();
                        break;
                    case 6:
                        this.sortCarsByBrand();
                        break;
                    default:
                        System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
                }
            } catch (NumberFormatException var3) {
                System.out.println("BŁĄD: Proszę podać liczbę!");
            } catch (RentalException e) {
                System.out.println("BŁĄD WYPOŻYCZENIA: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Wystąpił nieoczekiwany błąd: " + e.getMessage());
            }
        }

    }

    private void printMenu() {
        System.out.println("\n=== SYSTEM WYPOŻYCZALNI SAMOCHODÓW ===");
        System.out.println("1. Pokaż dostępne samochody");
        System.out.println("2. Sortuj samochody wg ceny (Comparable)");
        System.out.println("3. Wypożycz samochód");
        System.out.println("4. Lista klientów");
        System.out.println("5. Dodaj nowego klienta");
        System.out.println("6. Sortuj samochody wg marki (Comparator)");
        System.out.println("0. Wyjście");
        System.out.print("Twój wybór: ");
    }

    private void showCars() {
        System.out.println("\n--- FLOTA POJAZDÓW ---");
        if (this.cars.isEmpty()) {
            System.out.println("Brak samochodów w bazie.");
        } else {
            for(Car car : this.cars) {
                System.out.println(car);
            }
        }

    }

    private void sortCarsByPrice() {
        Collections.sort(this.cars);
        System.out.println("Posortowano samochody od najtańszego (Cena).");
        this.showCars();
    }

    private void sortCarsByBrand() {
        Collections.sort(this.cars, new CarComparator());
        System.out.println("Posortowano samochody alfabetycznie (Marka).");
        this.showCars();
    }

    private void rentCar() throws RentalException {
        System.out.println("\n--- WYPOŻYCZANIE ---");
        this.showCars();
        System.out.print("Podaj ID samochodu do wynajęcia: ");
        int carId = Integer.parseInt(this.scanner.nextLine());
        Car selectedCar = null;

        for(Car car : this.cars) {
            if (car.getId() == carId) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar == null) {
            throw new RentalException("Nie znaleziono samochodu o ID: " + carId);
        } else if (selectedCar.isRented()) {
            throw new RentalException("Ten samochód jest już wypożyczony!");
        } else {
            System.out.print("Podaj na ile dni chcesz wypożyczyć: ");
            int days = Integer.parseInt(this.scanner.nextLine());
            double cost = selectedCar.calculateFinalCost(days);
            System.out.println("Wybrano: " + selectedCar.getBrand());
            System.out.println("Koszt całkowity: " + cost + " PLN");
            System.out.print("Potwierdzasz? (t/n): ");
            String confirm = this.scanner.nextLine();
            if (confirm.equalsIgnoreCase("t")) {
                selectedCar.setRented(true);
                Payment payment = new Payment(UUID.randomUUID().toString(), cost, "TRANS-" + System.currentTimeMillis());
                payment.processPayment();
                System.out.println("Sukces! Samochód został wypożyczony.");
            } else {
                System.out.println("Anulowano operację.");
            }

        }
    }

    private void showClients() {
        System.out.println("\n--- KLIENCI ---");

        for(Klient client : this.clients) {
            System.out.println(client);
        }

    }

    private void addClient() {
        System.out.println("\n--- DODAWANIE KLIENTA ---");
        System.out.print("Podaj imię: ");
        String name = this.scanner.nextLine();
        System.out.print("Podaj nazwisko: ");
        String surname = this.scanner.nextLine();
        System.out.print("Podaj miasto: ");
        String city = this.scanner.nextLine();
        int id = this.clients.size() + 1;
        Klient newClient = new Klient(id, name, surname, LocalDate.now(), "M/K", "email@test.com", "123456789", "hash", city);
        this.clients.add(newClient);
        System.out.println("Dodano nowego klienta!");
    }

    private void initializeData() {
        this.cars.add(new StandardCar(1, "Toyota", "WA 12345", "Biały", 2020, (double)100.0F));
        this.cars.add(new StandardCar(2, "Ford", "KR 55555", "Niebieski", 2019, (double)120.0F));
        this.cars.add(new PremiumCar(3, "BMW", "PO 99999", "Czarny", 2023, (double)500.0F, (double)50.0F));
        this.cars.add(new PremiumCar(4, "Mercedes", "GD 77777", "Srebrny", 2024, (double)600.0F, (double)100.0F));
        this.clients.add(new Klient(1, "Jan", "Kowalski", LocalDate.of(1990, 1, 1), "M", "jan@wp.pl", "500600700", "pass1", "Warszawa"));
        this.clients.add(new Klient(2, "Anna", "Nowak", LocalDate.of(1995, 5, 20), "K", "anna@onet.pl", "600700800", "pass2", "Kraków"));
    }
}
