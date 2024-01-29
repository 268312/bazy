package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "service_history", schema = "projekt", catalog = "")
public class ServiceHistoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "serviceID")
    private int serviceId;
    @Basic
    @Column(name = "service_date")
    private Date serviceDate;
    @Basic
    @Column(name = "serviced_device")
    private Integer servicedDevice;
    @Basic
    @Column(name = "technician")
    private String technician;
    @Basic
    @Column(name = "service_description")
    private String serviceDescription;
    @ManyToOne
    @JoinColumn(name = "serviced_device", referencedColumnName = "deviceID")
    private DeviceEntity deviceByServicedDevice;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Integer getServicedDevice() {
        return servicedDevice;
    }

    public void setServicedDevice(Integer servicedDevice) {
        this.servicedDevice = servicedDevice;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceHistoryEntity that = (ServiceHistoryEntity) o;

        if (serviceId != that.serviceId) return false;
        if (serviceDate != null ? !serviceDate.equals(that.serviceDate) : that.serviceDate != null) return false;
        if (servicedDevice != null ? !servicedDevice.equals(that.servicedDevice) : that.servicedDevice != null)
            return false;
        if (technician != null ? !technician.equals(that.technician) : that.technician != null) return false;
        if (serviceDescription != null ? !serviceDescription.equals(that.serviceDescription) : that.serviceDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceId;
        result = 31 * result + (serviceDate != null ? serviceDate.hashCode() : 0);
        result = 31 * result + (servicedDevice != null ? servicedDevice.hashCode() : 0);
        result = 31 * result + (technician != null ? technician.hashCode() : 0);
        result = 31 * result + (serviceDescription != null ? serviceDescription.hashCode() : 0);
        return result;
    }

    public DeviceEntity getDeviceByServicedDevice() {
        return deviceByServicedDevice;
    }

    public void setDeviceByServicedDevice(DeviceEntity deviceByServicedDevice) {
        this.deviceByServicedDevice = deviceByServicedDevice;
    }
}
