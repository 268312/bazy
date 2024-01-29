package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "device", schema = "projekt", catalog = "")
public class DeviceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "deviceID")
    private int deviceId;
    @Basic
    @Column(name = "device_name")
    private String deviceName;
    @Basic
    @Column(name = "device_type")
    private String deviceType;
    @Basic
    @Column(name = "production_date")
    private Date productionDate;
    @Basic
    @Column(name = "last_service")
    private Date lastService;
    @Basic
    @Column(name = "status")
    private Object status;
    @Basic
    @Column(name = "service_validity_period")
    private Integer serviceValidityPeriod;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getLastService() {
        return lastService;
    }

    public void setLastService(Date lastService) {
        this.lastService = lastService;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getServiceValidityPeriod() {
        return serviceValidityPeriod;
    }

    public void setServiceValidityPeriod(Integer serviceValidityPeriod) {
        this.serviceValidityPeriod = serviceValidityPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceEntity that = (DeviceEntity) o;

        if (deviceId != that.deviceId) return false;
        if (deviceName != null ? !deviceName.equals(that.deviceName) : that.deviceName != null) return false;
        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (productionDate != null ? !productionDate.equals(that.productionDate) : that.productionDate != null)
            return false;
        if (lastService != null ? !lastService.equals(that.lastService) : that.lastService != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (serviceValidityPeriod != null ? !serviceValidityPeriod.equals(that.serviceValidityPeriod) : that.serviceValidityPeriod != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deviceId;
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (productionDate != null ? productionDate.hashCode() : 0);
        result = 31 * result + (lastService != null ? lastService.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (serviceValidityPeriod != null ? serviceValidityPeriod.hashCode() : 0);
        return result;
    }
}
