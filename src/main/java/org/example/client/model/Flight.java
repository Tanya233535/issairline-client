package org.example.client.model;

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
    private final StringProperty routeDuration = new SimpleStringProperty();

    private ObjectProperty<Aircraft> aircraft = new SimpleObjectProperty<>();


    public long getId() { return id.get(); }
    public void setId(long v) { id.set(v); }
    public LongProperty idProperty() { return id; }

    public String getFlightNo() { return flightNo.get(); }
    public void setFlightNo(String v) { flightNo.set(v); }
    public StringProperty flightNoProperty() { return flightNo; }

    public LocalDateTime getScheduledDeparture() { return scheduledDeparture.get(); }
    public void setScheduledDeparture(LocalDateTime v) { scheduledDeparture.set(v); }
    public ObjectProperty<LocalDateTime> scheduledDepartureProperty() { return scheduledDeparture; }

    public LocalDateTime getScheduledArrival() { return scheduledArrival.get(); }
    public void setScheduledArrival(LocalDateTime v) { scheduledArrival.set(v); }
    public ObjectProperty<LocalDateTime> scheduledArrivalProperty() { return scheduledArrival; }

    public LocalDateTime getActualDeparture() { return actualDeparture.get(); }
    public void setActualDeparture(LocalDateTime v) { actualDeparture.set(v); }
    public ObjectProperty<LocalDateTime> actualDepartureProperty() { return actualDeparture; }

    public LocalDateTime getActualArrival() { return actualArrival.get(); }
    public void setActualArrival(LocalDateTime v) { actualArrival.set(v); }
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

    public Aircraft getAircraft() { return aircraft.get(); }
    public void setAircraft(Aircraft v) { aircraft.set(v); }
    public ObjectProperty<Aircraft> aircraftProperty() { return aircraft; }

    public String getRouteDuration() { return routeDuration.get(); }
    public void setRouteDuration(String v) { routeDuration.set(v); }
    public StringProperty routeDurationProperty() { return routeDuration; }
}
