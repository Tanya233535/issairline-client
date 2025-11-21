package org.example.client.model;

import javafx.beans.property.*;

public class Aircraft {

    private final StringProperty aircraftCode = new SimpleStringProperty();
    private final StringProperty model = new SimpleStringProperty();
    private final IntegerProperty manufactureYear = new SimpleIntegerProperty();
    private final IntegerProperty capacity = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final DoubleProperty totalFlightHours = new SimpleDoubleProperty();

    public Aircraft() {}


    public String getAircraftCode() {
        return aircraftCode.get();
    }

    public void setAircraftCode(String value) {
        aircraftCode.set(value);
    }

    public StringProperty aircraftCodeProperty() {
        return aircraftCode;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String value) {
        model.set(value);
    }

    public StringProperty modelProperty() {
        return model;
    }

    public int getManufactureYear() {
        return manufactureYear.get();
    }

    public void setManufactureYear(int value) {
        manufactureYear.set(value);
    }

    public IntegerProperty manufactureYearProperty() {
        return manufactureYear;
    }

    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int value) {
        capacity.set(value);
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public double getTotalFlightHours() {
        return totalFlightHours.get();
    }

    public void setTotalFlightHours(double value) {
        totalFlightHours.set(value);
    }

    public DoubleProperty totalFlightHoursProperty() {
        return totalFlightHours;
    }

    public StringProperty formattedHoursProperty() {
        double hours = totalFlightHours.get();
        int h = (int) hours;
        int m = (int) Math.round((hours - h) * 60);
        return new SimpleStringProperty(h + " ч " + String.format("%02d", m) + " мин");
    }
}
