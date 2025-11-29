package model;
import java.time.LocalDate;

public class Administrator extends Person {
    public Administrator(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
    }
}
