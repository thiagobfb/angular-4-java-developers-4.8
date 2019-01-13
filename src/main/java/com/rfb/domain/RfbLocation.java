package com.rfb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RfbLocation.
 */
@Entity
@Table(name = "rfb_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RfbLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "run_day_of_week")
    private Integer runDayOfWeek;

    @OneToMany(mappedBy = "rfbLocation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RfbEvent> rfbEvents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public RfbLocation locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getRunDayOfWeek() {
        return runDayOfWeek;
    }

    public RfbLocation runDayOfWeek(Integer runDayOfWeek) {
        this.runDayOfWeek = runDayOfWeek;
        return this;
    }

    public void setRunDayOfWeek(Integer runDayOfWeek) {
        this.runDayOfWeek = runDayOfWeek;
    }

    public Set<RfbEvent> getRfbEvents() {
        return rfbEvents;
    }

    public RfbLocation rfbEvents(Set<RfbEvent> rfbEvents) {
        this.rfbEvents = rfbEvents;
        return this;
    }

    public RfbLocation addRfbEvent(RfbEvent rfbEvent) {
        this.rfbEvents.add(rfbEvent);
        rfbEvent.setRfbLocation(this);
        return this;
    }

    public RfbLocation removeRfbEvent(RfbEvent rfbEvent) {
        this.rfbEvents.remove(rfbEvent);
        rfbEvent.setRfbLocation(null);
        return this;
    }

    public void setRfbEvents(Set<RfbEvent> rfbEvents) {
        this.rfbEvents = rfbEvents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RfbLocation rfbLocation = (RfbLocation) o;
        if (rfbLocation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rfbLocation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RfbLocation{" +
            "id=" + getId() +
            ", locationName='" + getLocationName() + "'" +
            ", runDayOfWeek=" + getRunDayOfWeek() +
            "}";
    }
}
