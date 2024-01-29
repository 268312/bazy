package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "technicians", schema = "projekt", catalog = "")
public class TechniciansEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "technicianID")
    private int technicianId;
    @Basic
    @Column(name = "technician_name")
    private String technicianName;
    @Basic
    @Column(name = "technician_surname")
    private String technicianSurname;
    @Basic
    @Column(name = "technician_password")
    private String technicianPassword;

    public int getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTechnicianSurname() {
        return technicianSurname;
    }

    public void setTechnicianSurname(String technicianSurname) {
        this.technicianSurname = technicianSurname;
    }

    public String getTechnicianPassword() {
        return technicianPassword;
    }

    public void setTechnicianPassword(String technicianPassword) {
        this.technicianPassword = technicianPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TechniciansEntity that = (TechniciansEntity) o;

        if (technicianId != that.technicianId) return false;
        if (technicianName != null ? !technicianName.equals(that.technicianName) : that.technicianName != null)
            return false;
        if (technicianSurname != null ? !technicianSurname.equals(that.technicianSurname) : that.technicianSurname != null)
            return false;
        if (technicianPassword != null ? !technicianPassword.equals(that.technicianPassword) : that.technicianPassword != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = technicianId;
        result = 31 * result + (technicianName != null ? technicianName.hashCode() : 0);
        result = 31 * result + (technicianSurname != null ? technicianSurname.hashCode() : 0);
        result = 31 * result + (technicianPassword != null ? technicianPassword.hashCode() : 0);
        return result;
    }
}
