package model;

import java.time.LocalDate;

public class Person {
    public int id;
    public String firstName;
    public String lastName;
    public LocalDate birthDate ;
    public String sex;
    public String email;
    public String phoneNumber;
    public String passwordHash;

    public Person(int id , String firstName , String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
    }

}
