package es.deusto.spq.server.jdo;

import javax.jdo.annotations.*;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Booking {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    private String travelerUsername;
    private String hostUsername;
    private Long residenceId;
    private String startDate;
    private String endDate;

    public Booking(String travelerUsername, String hostUsername, Long residenceId, String startDate, String endDate) {
        this.travelerUsername = travelerUsername;
        this.hostUsername = hostUsername;
        this.residenceId = residenceId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String gettravelerUsername() {
        return travelerUsername;
    }

    public void settravelerUsername(String travelerUsername) {
        this.travelerUsername = travelerUsername;
    }

    public String gethostUsername() {
        return hostUsername;
    }

    public void sethostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    public Long getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Long residenceId) {
        this.residenceId = residenceId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", travelerUsername=" + travelerUsername +
                ", hostUsername=" + hostUsername +
                ", residenceId=" + residenceId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

}
