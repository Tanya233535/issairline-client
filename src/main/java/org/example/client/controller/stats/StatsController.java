package org.example.client.controller.stats;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import org.example.client.api.FlightApi;
import org.example.client.api.UserApi;
import org.example.client.model.Flight;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StatsController {

    @FXML private PieChart routePieChart;
    @FXML private BarChart<String, Number> flightsBarChart;
    @FXML private LineChart<String, Number> passengerGrowthChart;
    @FXML private PieChart canceledPieChart;

    @FXML private Label avgLoadLabel;
    @FXML private Label avgPassengersLabel;
    @FXML private Label cancelRateLabel;
    @FXML private Label efficiencyLabel;
    @FXML private Label totalUsersLabel;

    private List<Flight> flights;

    @FXML
    private void initialize() {
        try {
            flights = FlightApi.getAll();
            loadRouteDistribution();
            loadFlightsPerRoute();
            loadPassengerGrowth();
            loadCanceledShare();
            loadAveragesAndKPIs();
            loadUserStats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUserStats() {
        try {
            int count = UserApi.getCount();
            totalUsersLabel.setText(String.valueOf(count));
        } catch (Exception e) {
            totalUsersLabel.setText("0");
            e.printStackTrace();
        }
    }

    private void loadRouteDistribution() {

        Map<String, Long> map = flights.stream()
                .collect(Collectors.groupingBy(
                        f -> f.getDepartureAirport() + " → " + f.getArrivalAirport(),
                        Collectors.counting()
                ));

        map.forEach((route, count) ->
                routePieChart.getData().add(new PieChart.Data(route, count))
        );
    }

    private void loadFlightsPerRoute() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Рейсы по маршрутам");

        Map<String, Long> map = flights.stream()
                .collect(Collectors.groupingBy(
                        f -> f.getDepartureAirport() + " → " + f.getArrivalAirport(),
                        Collectors.counting()
                ));

        map.forEach((route, count) ->
                series.getData().add(new XYChart.Data<>(route, count))
        );

        flightsBarChart.getData().add(series);
    }

    private void loadPassengerGrowth() {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
        Map<String, Long> monthly = new TreeMap<>();

        for (Flight f : flights) {
            if (f.getScheduledDeparture() != null &&
                    !f.getStatus().equalsIgnoreCase("CANCELLED")) {

                String month = f.getScheduledDeparture().format(fmt);
                monthly.merge(month, (long) f.getPassengerCount(), Long::sum);
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Пассажиры по месяцам");

        monthly.forEach((month, count) ->
                series.getData().add(new XYChart.Data<>(month, count))
        );

        passengerGrowthChart.getData().add(series);
    }

    private void loadCanceledShare() {

        long cancelled = flights.stream()
                .filter(f -> f.getStatus().equalsIgnoreCase("CANCELLED"))
                .count();

        long total = flights.size();
        long completed = total - cancelled;

        canceledPieChart.getData().addAll(
                new PieChart.Data("Отменённые", cancelled),
                new PieChart.Data("Выполненные", completed)
        );
    }

    private void loadAveragesAndKPIs() {

        double avgP = flights.stream()
                .mapToInt(Flight::getPassengerCount)
                .average()
                .orElse(0);
        avgPassengersLabel.setText(String.format("%.2f", avgP));

        double avgLoad = flights.stream()
                .filter(f -> f.getAircraft() != null)
                .mapToDouble(f -> (double) f.getPassengerCount() / f.getAircraft().getCapacity() * 100)
                .average()
                .orElse(0);
        avgLoadLabel.setText(String.format("%.2f%%", avgLoad));

        long cancelled = flights.stream()
                .filter(f -> f.getStatus().equalsIgnoreCase("CANCELLED"))
                .count();
        double cancelRate = (double) cancelled / flights.size() * 100;
        cancelRateLabel.setText(String.format("%.2f%%", cancelRate));

        Map<String, Double> hours = new HashMap<>();
        for (Flight f : flights) {
            if (f.getAircraft() != null &&
                    f.getStatus().equalsIgnoreCase("ARRIVED")) {

                double h = Duration.between(
                        f.getScheduledDeparture(),
                        f.getScheduledArrival()
                ).toHours();

                hours.merge(f.getAircraft().getAircraftCode(), h, Double::sum);
            }
        }
    }
}
