package org.example.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Flight {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty flightNo = new SimpleStringProperty();
    private ObjectProperty<LocalDateTime> scheduledDeparture = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> scheduledArrival = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> actualDeparture = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> actualArrival = new SimpleObjectProperty<>();
    private StringProperty departureAirport = new SimpleStringProperty();
    private StringProperty arrivalAirport = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();
    private StringProperty routeDuration = new SimpleStringProperty();
    private ObjectProperty<Aircraft> aircraft = new SimpleObjectProperty<>();
    private IntegerProperty passengerCount = new SimpleIntegerProperty();

    @JsonProperty("id")
    public long getId() { return id.get(); }
    @JsonProperty("id")
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    public String getFlightNo() { return flightNo.get(); }
    public void setFlightNo(String flightNo) { this.flightNo.set(flightNo); }
    public StringProperty flightNoProperty() { return flightNo; }

    public LocalDateTime getScheduledDeparture() { return scheduledDeparture.get(); }
    public void setScheduledDeparture(LocalDateTime dt) { scheduledDeparture.set(dt); }
    public ObjectProperty<LocalDateTime> scheduledDepartureProperty() { return scheduledDeparture; }

    public LocalDateTime getScheduledArrival() { return scheduledArrival.get(); }
    public void setScheduledArrival(LocalDateTime dt) { scheduledArrival.set(dt); }
    public ObjectProperty<LocalDateTime> scheduledArrivalProperty() { return scheduledArrival; }

    public LocalDateTime getActualDeparture() { return actualDeparture.get(); }
    public void setActualDeparture(LocalDateTime dt) { actualDeparture.set(dt); }
    public ObjectProperty<LocalDateTime> actualDepartureProperty() { return actualDeparture; }

    public LocalDateTime getActualArrival() { return actualArrival.get(); }
    public void setActualArrival(LocalDateTime dt) { actualArrival.set(dt); }
    public ObjectProperty<LocalDateTime> actualArrivalProperty() { return actualArrival; }

    public String getDepartureAirport() { return departureAirport.get(); }
    public void setDepartureAirport(String v) { departureAirport.set(v); }
    public StringProperty departureAirportProperty() { return departureAirport; }

    public String getArrivalAirport() { return arrivalAirport.get(); }
    public void setArrivalAirport(String v) { arrivalAirport.set(v); }
    public StringProperty arrivalAirportProperty() { return arrivalAirport; }

    public String getStatus() { return status.get(); }
    public void setStatus(String v) { status.set(v); }
    public StringProperty statusProperty() { return status; }

    public String getRouteDuration() { return routeDuration.get(); }
    public void setRouteDuration(String v) { routeDuration.set(v); }
    public StringProperty routeDurationProperty() { return routeDuration; }

    public Aircraft getAircraft() { return aircraft.get(); }
    @JsonProperty("aircraft")
    public void setAircraft(Aircraft a) { aircraft.set(a); }
    public ObjectProperty<Aircraft> aircraftProperty() { return aircraft; }

    public int getPassengerCount() { return passengerCount.get(); }
    public void setPassengerCount(int v) { passengerCount.set(v); }
    public IntegerProperty passengerCountProperty() { return passengerCount; }

    @Override
    public String toString() { return flightNo.get(); }

    // --- JSON helpers: serialize aircraftCode so server receives it ---
    @JsonProperty("aircraftCode")
    public String getAircraftCode() {
        Aircraft a = aircraft.get();
        return (a == null) ? null : a.getAircraftCode();
    }

    @JsonProperty("aircraftCode")
    public void setAircraftFromCode(String code) {
        if (code == null) {
            aircraft.set(null);
        } else {
            Aircraft a = new Aircraft();
            a.setAircraftCode(code);
            aircraft.set(a);
        }
    }
}
