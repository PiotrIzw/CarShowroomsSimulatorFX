package carshowroomsimulator.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingCartTableModel{
    private StringProperty brand = new SimpleStringProperty();
    private StringProperty model = new SimpleStringProperty();
    private DoubleProperty price;
    private IntegerProperty amount;

    public ShoppingCartTableModel(String brand, String model, int amount, Double price) {
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }
}
