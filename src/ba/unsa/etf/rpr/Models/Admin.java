package ba.unsa.etf.rpr.Models;

import java.util.Objects;

public class Admin {
    private String apothecaryName, password;

    public Admin() {
        this.apothecaryName = "";
        this.password = "";
    }

    public Admin(String apothecaryName, String password) {
        this.apothecaryName = apothecaryName;
        this.password = password;
    }

    public String getApothecaryName() {
        return apothecaryName;
    }

    public void setApothecaryName(String apothecaryName) {
        this.apothecaryName = apothecaryName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(apothecaryName, admin.apothecaryName) && Objects.equals(password, admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apothecaryName, password);
    }
}
