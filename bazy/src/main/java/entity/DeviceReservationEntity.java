package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "device_reservation", schema = "projekt", catalog = "")
public class DeviceReservationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reservationID")
    private int reservationId;
    @Basic
    @Column(name = "date_from")
    private Date dateFrom;
    @Basic
    @Column(name = "date_to")
    private Date dateTo;
    @Basic
    @Column(name = "reservated_device")
    private Integer reservatedDevice;
    @Basic
    @Column(name = "reserving_person")
    private String reservingPerson;
    @ManyToOne
    @JoinColumn(name = "reservated_device", referencedColumnName = "deviceID")
    private DeviceEntity deviceByReservatedDevice;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getReservatedDevice() {
        return reservatedDevice;
    }

    public void setReservatedDevice(Integer reservatedDevice) {
        this.reservatedDevice = reservatedDevice;
    }

    public String getReservingPerson() {
        return reservingPerson;
    }

    public void setReservingPerson(String reservingPerson) {
        this.reservingPerson = reservingPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceReservationEntity that = (DeviceReservationEntity) o;

        if (reservationId != that.reservationId) return false;
        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null) return false;
        if (dateTo != null ? !dateTo.equals(that.dateTo) : that.dateTo != null) return false;
        if (reservatedDevice != null ? !reservatedDevice.equals(that.reservatedDevice) : that.reservatedDevice != null)
            return false;
        if (reservingPerson != null ? !reservingPerson.equals(that.reservingPerson) : that.reservingPerson != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (reservatedDevice != null ? reservatedDevice.hashCode() : 0);
        result = 31 * result + (reservingPerson != null ? reservingPerson.hashCode() : 0);
        return result;
    }

    public DeviceEntity getDeviceByReservatedDevice() {
        return deviceByReservatedDevice;
    }

    public void setDeviceByReservatedDevice(DeviceEntity deviceByReservatedDevice) {
        this.deviceByReservatedDevice = deviceByReservatedDevice;
    }
}
