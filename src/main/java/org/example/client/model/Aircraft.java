package org.example.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public Aircraft() {
        totalFlightHours.addListener((obs, oldV, newV) -> updateFormattedHours());
    }

    @JsonProperty("aircraftCode")
    public String getAircraftCode() { return aircraftCode.get(); }
    @JsonProperty("aircraftCode")
    public void setAircraftCode(String code) { this.aircraftCode.set(code); }
    public StringProperty aircraftCodeProperty() { return aircraftCode; }

    public String getModel() { return model.get(); }
    public void setModel(String model) { this.model.set(model); }
    public StringProperty modelProperty() { return model; }

    public int getManufactureYear() { return manufactureYear.get(); }
    public void setManufactureYear(int year) { this.manufactureYear.set(year); }
    public IntegerProperty manufactureYearProperty() { return manufactureYear; }

    public int getCapacity() { return capacity.get(); }
    public void setCapacity(int capacity) { this.capacity.set(capacity); }
    public IntegerProperty capacityProperty() { return capacity; }

    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
    public StringProperty statusProperty() { return status; }

    public double getTotalFlightHours() { return totalFlightHours.get(); }
    @JsonProperty("totalFlightHours")
    public void setTotalFlightHours(double hours) {
        this.totalFlightHours.set(hours);
        updateFormattedHours();
    }
    public DoubleProperty totalFlightHoursProperty() { return totalFlightHours; }

    public LocalDate getLastMaintenanceDate() { return lastMaintenanceDate.get(); }
    @JsonProperty("lastMaintenanceDate")
    public void setLastMaintenanceDate(LocalDate date) { this.lastMaintenanceDate.set(date); }
    public ObjectProperty<LocalDate> lastMaintenanceDateProperty() { return lastMaintenanceDate; }

    public String getFormattedFlightHours() { return formattedFlightHours.get(); }
    public void setFormattedFlightHours(String v) { formattedFlightHours.set(v); }
    public StringProperty formattedHoursProperty() { return formattedFlightHours; }

    private void updateFormattedHours() {
        double hours = getTotalFlightHours();
        int h = (int) hours;
        int m = (int) Math.round((hours - h) * 60);
        formattedFlightHours.set(h + " ч " + String.format("%02d", m) + " мин");
    }

    @Override
    public String toString() { return getAircraftCode() + " — " + getModel(); }
}
