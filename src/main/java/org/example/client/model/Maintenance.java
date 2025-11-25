package org.example.client.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Maintenance {

    private LongProperty id = new SimpleLongProperty();
    private ObjectProperty<Aircraft> aircraft = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> maintenanceDate = new SimpleObjectProperty<>();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty engineerName = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private ObjectProperty<LocalDate> nextDueDate = new SimpleObjectProperty<>();
    private StringProperty status = new SimpleStringProperty();

    public long getId() { return id.get(); }
    public void setId(long v) { id.set(v); }
    public LongProperty idProperty() { return id; }

    public Aircraft getAircraft() { return aircraft.get(); }
    public void setAircraft(Aircraft v) { aircraft.set(v); }
    public ObjectProperty<Aircraft> aircraftProperty() { return aircraft; }

    public LocalDate getMaintenanceDate() { return maintenanceDate.get(); }
    public void setMaintenanceDate(LocalDate v) { maintenanceDate.set(v); }
    public ObjectProperty<LocalDate> maintenanceDateProperty() { return maintenanceDate; }

    public String getType() { return type.get(); }
    public void setType(String v) { type.set(v); }
    public StringProperty typeProperty() { return type; }

    public String getEngineerName() { return engineerName.get(); }
    public void setEngineerName(String v) { engineerName.set(v); }
    public StringProperty engineerNameProperty() { return engineerName; }

    public String getDescription() { return description.get(); }
    public void setDescription(String v) { description.set(v); }
    public StringProperty descriptionProperty() { return description; }

    public LocalDate getNextDueDate() { return nextDueDate.get(); }
    public void setNextDueDate(LocalDate v) { nextDueDate.set(v); }
    public ObjectProperty<LocalDate> nextDueDateProperty() { return nextDueDate; }

    public String getStatus() { return status.get(); }
    public void setStatus(String v) { status.set(v); }
    public StringProperty statusProperty() { return status; }
}
