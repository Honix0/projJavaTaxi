package model;

import java.time.LocalDate;

public class Administrator extends Person {
    private String adminCode;

    public Administrator(int id, String firstName, String lastName, LocalDate birthDate, String sex, String email, String phoneNumber, String passwordHash, String adminCode) {
        super(id, firstName, lastName, birthDate, sex, email, phoneNumber, passwordHash);
        this.adminCode = adminCode;
    }

        public String getAdminCode() {
            return this.adminCode;
        }

        public void setAdminCode(String adminCode) {
            this.adminCode = adminCode;
        }

    @Override
    public String getRole() {
        return "ADMINISTRATOR";
    }

    @Override
    public String toString() {
        // Убрали var10000 и перевели на польский
        return super.toString() + " [ADMIN] (Kod dostępu: " + this.adminCode + ")";
    }
}