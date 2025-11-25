package org.example.client.model;

import javafx.beans.property.*;
import org.example.client.model.Flight;

public class Passenger {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty middleName = new SimpleStringProperty();
    private StringProperty passportNumber = new SimpleStringProperty();
    private StringProperty ticketNumber = new SimpleStringProperty();
    private StringProperty seat = new SimpleStringProperty();
    private ObjectProperty<Flight> flight = new SimpleObjectProperty<>();


    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String v) { lastName.set(v); }
    public StringProperty lastNameProperty() { return lastName; }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String v) { firstName.set(v); }
    public StringProperty firstNameProperty() { return firstName; }

    public String getMiddleName() { return middleName.get(); }
    public void setMiddleName(String v) { middleName.set(v); }
    public StringProperty middleNameProperty() { return middleName; }

    public String getPassportNumber() { return passportNumber.get(); }
    public void setPassportNumber(String v) { passportNumber.set(v); }
    public StringProperty passportNumberProperty() { return passportNumber; }

    public String getTicketNumber() { return ticketNumber.get(); }
    public void setTicketNumber(String v) { ticketNumber.set(v); }
    public StringProperty ticketNumberProperty() { return ticketNumber; }

    public String getSeat() { return seat.get(); }
    public void setSeat(String v) { seat.set(v); }
    public StringProperty seatProperty() { return seat; }

    public Flight getFlight() { return flight.get(); }
    public void setFlight(Flight f) { flight.set(f); }
    public ObjectProperty<Flight> flightProperty() { return flight; }
}
