

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

    public String getRole() {
        return "ADMINISTRATOR";
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + " [ADMIN] (Код доступа: " + this.adminCode + ")";
    }
}
