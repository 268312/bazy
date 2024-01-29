package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors", schema = "projekt", catalog = "")
public class DoctorsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "doctorID")
    private int doctorId;
    @Basic
    @Column(name = "doctor_name")
    private String doctorName;
    @Basic
    @Column(name = "doctor_surname")
    private String doctorSurname;
    @Basic
    @Column(name = "doctor_password")
    private String doctorPassword;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSurname() {
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public void loginDoctor(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorsEntity that = (DoctorsEntity) o;

        if (doctorId != that.doctorId) return false;
        if (doctorName != null ? !doctorName.equals(that.doctorName) : that.doctorName != null) return false;
        if (doctorSurname != null ? !doctorSurname.equals(that.doctorSurname) : that.doctorSurname != null)
            return false;
        if (doctorPassword != null ? !doctorPassword.equals(that.doctorPassword) : that.doctorPassword != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = doctorId;
        result = 31 * result + (doctorName != null ? doctorName.hashCode() : 0);
        result = 31 * result + (doctorSurname != null ? doctorSurname.hashCode() : 0);
        result = 31 * result + (doctorPassword != null ? doctorPassword.hashCode() : 0);
        return result;
    }
}
