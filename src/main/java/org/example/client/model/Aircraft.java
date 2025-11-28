package org.example.client.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Aircraft {

    private final StringProperty aircraftCode = new SimpleStringProperty();
    private final StringProperty model = new SimpleStringProperty();
    private final IntegerProperty manufactureYear = new SimpleIntegerProperty();
    private final IntegerProperty capacity = new SimpleIntegerProperty();

    private final StringProperty status = new SimpleStringProperty();

    private final DoubleProperty totalFlightHours = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDate> lastMaintenanceDate = new SimpleObjectProperty<>();
    private final StringProperty formattedFlightHours = new SimpleStringProperty();

    public Aircraft() {}

    public String getAircraftCode() { return aircraftCode.get(); }
    public void setAircraftCode(String v) { aircraftCode.set(v); }
    public StringProperty aircraftCodeProperty() { return aircraftCode; }

    public String getModel() { return model.get(); }
    public void setModel(String v) { model.set(v); }
    public StringProperty modelProperty() { return model; }

    public int getManufactureYear() { return manufactureYear.get(); }
    public void setManufactureYear(int v) { manufactureYear.set(v); }
    public IntegerProperty manufactureYearProperty() { return manufactureYear; }

    public int getCapacity() { return capacity.get(); }
    public void setCapacity(int v) { capacity.set(v); }
    public IntegerProperty capacityProperty() { return capacity; }

    public String getStatus() { return status.get(); }
    public void setStatus(String v) { status.set(v); }
    public StringProperty statusProperty() { return status; }

    public double getTotalFlightHours() { return totalFlightHours.get(); }
    public void setTotalFlightHours(double v) { totalFlightHours.set(v); }
    public DoubleProperty totalFlightHoursProperty() { return totalFlightHours; }

    public LocalDate getLastMaintenanceDate() { return lastMaintenanceDate.get(); }
    public void setLastMaintenanceDate(LocalDate d) { lastMaintenanceDate.set(d); }
    public ObjectProperty<LocalDate> lastMaintenanceDateProperty() { return lastMaintenanceDate; }

    public String getFormattedFlightHours() { return formattedFlightHours.get(); }
    public void setFormattedFlightHours(String v) { formattedFlightHours.set(v); }
    public StringProperty formattedFlightHoursProperty() { return formattedFlightHours; }

    public StringProperty formattedHoursProperty() {
        double hours = totalFlightHours.get();
        int h = (int) hours;
        int m = (int) Math.round((hours - h) * 60);
        return new SimpleStringProperty(h + " ч " + String.format("%02d", m) + " мин");
    }

    @Override
    public String toString() {
        return getAircraftCode() + " — " + getModel();
    }
}
