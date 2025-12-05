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

    public void setTotalFlightHours(double v) {
        totalFlightHours.set(v);
        updateFormattedHours();
    }

    public DoubleProperty totalFlightHoursProperty() { return totalFlightHours; }

    public LocalDate getLastMaintenanceDate() { return lastMaintenanceDate.get(); }
    public void setLastMaintenanceDate(LocalDate d) { lastMaintenanceDate.set(d); }
    public ObjectProperty<LocalDate> lastMaintenanceDateProperty() { return lastMaintenanceDate; }

    public String getFormattedFlightHours() { return formattedFlightHours.get(); }
    public void setFormattedFlightHours(String v) { formattedFlightHours.set(v); }
    public StringProperty formattedFlightHoursProperty() { return formattedFlightHours; }

    @JsonProperty("aircraftCode")
    public String getAircraftCodeJson() { return getAircraftCode(); }

    @JsonProperty("aircraftCode")
    public void setAircraftCodeJson(String v) { setAircraftCode(v); }

    @JsonProperty("totalFlightHours")
    public void setTotalFlightHoursJson(double v) { setTotalFlightHours(v); }

    @JsonProperty("lastMaintenanceDate")
    public void setMaintenanceDateJson(LocalDate d) { setLastMaintenanceDate(d); }

    @JsonProperty("status")
    public void setStatusJson(String s) { setStatus(s); }


    private void updateFormattedHours() {
        double hours = getTotalFlightHours();

        int h = (int) hours;
        int m = (int) Math.round((hours - h) * 60);

        formattedFlightHours.set(h + " ч " + String.format("%02d", m) + " мин");
    }

    public StringProperty formattedHoursProperty() {
        return formattedFlightHours;
    }

    @Override
    public String toString() {
        return getAircraftCode() + " — " + getModel();
    }
}
