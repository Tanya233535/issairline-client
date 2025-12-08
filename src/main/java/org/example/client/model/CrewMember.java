package org.example.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;

public class CrewMember {

    private LongProperty memberId = new SimpleLongProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty middleName = new SimpleStringProperty();
    private StringProperty role = new SimpleStringProperty();
    private StringProperty qualification = new SimpleStringProperty();
    private IntegerProperty experienceYears = new SimpleIntegerProperty();
    private ObjectProperty<Flight> flight = new SimpleObjectProperty<>();

    @JsonProperty("memberId")
    public long getMemberId() { return memberId.get(); }
    @JsonProperty("memberId")
    public void setMemberId(long v) { memberId.set(v); }
    public LongProperty memberIdProperty() { return memberId; }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String v) { lastName.set(v); }
    public StringProperty lastNameProperty() { return lastName; }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String v) { firstName.set(v); }
    public StringProperty firstNameProperty() { return firstName; }

    public String getMiddleName() { return middleName.get(); }
    public void setMiddleName(String v) { middleName.set(v); }
    public StringProperty middleNameProperty() { return middleName; }

    public String getRole() { return role.get(); }
    public void setRole(String v) { role.set(v); }
    public StringProperty roleProperty() { return role; }

    public String getQualification() { return qualification.get(); }
    public void setQualification(String v) { qualification.set(v); }
    public StringProperty qualificationProperty() { return qualification; }

    public int getExperienceYears() { return experienceYears.get(); }
    public void setExperienceYears(int v) { experienceYears.set(v); }
    public IntegerProperty experienceYearsProperty() { return experienceYears; }

    @JsonProperty("flightId")
    public Long getFlightId() {
        Flight f = flight.get();
        return (f == null) ? null : f.getId();
    }

    @JsonProperty("flightId")
    public void setFlightFromId(long flightId) {
        Flight newFlight = new Flight();
        newFlight.setId(flightId);
        flight.set(newFlight);
    }

    public Flight getFlight() { return flight.get(); }
    @JsonProperty("flight")
    public void setFlight(Flight f) { flight.set(f); }
    public ObjectProperty<Flight> flightProperty() { return flight; }
}
