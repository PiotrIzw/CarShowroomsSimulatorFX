package carshowroomsimulator.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableModel {
    private StringProperty brand = new SimpleStringProperty();
    private StringProperty model = new SimpleStringProperty();
    private DoubleProperty price;
    private IntegerProperty yearOfProduction;
    private StringProperty showroomName;
    private BooleanProperty isReserved;

    public TableModel(String brand, String model, Double price, int yearOfProduction, String showroomName, boolean isReserved) {
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.price = new SimpleDoubleProperty(price);
        this.yearOfProduction = new SimpleIntegerProperty(yearOfProduction);
        this.showroomName = new SimpleStringProperty(showroomName);
        this.isReserved = new SimpleBooleanProperty(isReserved);
    }

    public boolean isIsReserved() {
        return isReserved.get();
    }

    public BooleanProperty isReservedProperty() {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved.set(isReserved);
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction.set(yearOfProduction);
    }

    public void setShowroomName(String showroomName) {
        this.showroomName.set(showroomName);
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getYearOfProduction() {
        return yearOfProduction.get();
    }

    public IntegerProperty yearOfProductionProperty() {
        return yearOfProduction;
    }

    public String getShowroomName() {
        return showroomName.get();
    }

    public StringProperty showroomNameProperty() {
        return showroomName;
    }
}
