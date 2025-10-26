package model;

import java.time.LocalDate;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate ;
    private String sex;
    private String email;
    private String phoneNumber;
    private String passwordHash;

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
