package org.example.client.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Flight {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty flightNo = new SimpleStringProperty();
    private ObjectProperty<LocalDateTime> scheduledDeparture = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> scheduledArrival = new SimpleObjectProperty<>();
    private StringProperty departureAirport = new SimpleStringProperty();
    private StringProperty arrivalAirport = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();

    private ObjectProperty<Aircraft> aircraft = new SimpleObjectProperty<>();

    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    public String getFlightNo() { return flightNo.get(); }
    public void setFlightNo(String v) { flightNo.set(v); }
    public StringProperty flightNoProperty() { return flightNo; }

    public LocalDateTime getScheduledDeparture() { return scheduledDeparture.get(); }
    public void setScheduledDeparture(LocalDateTime t) { scheduledDeparture.set(t); }
    public ObjectProperty<LocalDateTime> scheduledDepartureProperty() { return scheduledDeparture; }

    public LocalDateTime getScheduledArrival() { return scheduledArrival.get(); }
    public void setScheduledArrival(LocalDateTime t) { scheduledArrival.set(t); }
    public ObjectProperty<LocalDateTime> scheduledArrivalProperty() { return scheduledArrival; }

    public String getDepartureAirport() { return departureAirport.get(); }
    public void setDepartureAirport(String s) { departureAirport.set(s); }
    public StringProperty departureAirportProperty() { return departureAirport; }

    public String getArrivalAirport() { return arrivalAirport.get(); }
    public void setArrivalAirport(String s) { arrivalAirport.set(s); }
    public StringProperty arrivalAirportProperty() { return arrivalAirport; }

    public String getStatus() { return status.get(); }
    public void setStatus(String s) { status.set(s); }
    public StringProperty statusProperty() { return status; }

    public Aircraft getAircraft() { return aircraft.get(); }
    public void setAircraft(Aircraft a) { aircraft.set(a); }
    public ObjectProperty<Aircraft> aircraftProperty() { return aircraft; }
}
