package carshowroomsimulator;

import carshowroomsimulator.data.DataGenerator;
import carshowroomsimulator.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Window;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ShoppingCart{

    public Label shoppingCartLabel;
    private CarShowroomContainer container;
    private String brand, model, showroomName;
    private Double price;
    private int amount;
    private String selectedShowroom;
    private boolean isReserved = false;
    public Button goBackButton;
    private DataGenerator dataGenerator;
    private Window window;
    private static List<Vehicle> basketCars = new ArrayList<>();
    private List<Vehicle> basketCarsObj = new ArrayList<>();
    @FXML
    public TableView<ShoppingCartTableModel> shoppingCartTableView = new TableView<>();
    @FXML
    private TableColumn<ShoppingCartTableModel, String> brandColumn = new TableColumn<>("Brand");
    @FXML
    private TableColumn<ShoppingCartTableModel, String> modelColumn = new TableColumn<>("Model");
    @FXML
    private TableColumn<ShoppingCartTableModel, Double> priceColumn = new TableColumn<>("Price");
    @FXML
    private TableColumn<ShoppingCartTableModel, Integer> amountColumn = new TableColumn<>("Amount");

    private List<Vehicle> vehicleList = new ArrayList<>();
    private ObservableList<ShoppingCartTableModel> modelList = FXCollections.observableArrayList();


    public void handleShowingPrimaryStage(ActionEvent actionEvent) throws Exception {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("objects.bin"))) {

            outputStream.writeObject(vehicleList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        goBackButton.getScene().getWindow().hide();

    }
    @FXML
    protected void initialize() {
        openData();
        vehicleList.addAll(Controller.getBasket());
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        shoppingCartTableView.getColumns().addAll(brandColumn, modelColumn, amountColumn, priceColumn);

        for(Vehicle v : vehicleList){
            modelList.addAll(new ShoppingCartTableModel(v.getBrand(), v.getModel(), v.getAmount(), v.getPrice()));
        }
        shoppingCartTableView.setItems(modelList);
    }

    public void handleRemovingFromShoppingCart(ActionEvent actionEvent) {
        ShoppingCartTableModel temp = shoppingCartTableView.getSelectionModel().getSelectedItem();
        for (int i = 0, vehicleListSize = vehicleList.size(); i < vehicleListSize; i++) {
            Vehicle v = vehicleList.get(i);
            if (temp.getBrand().equals(v.getBrand())) {
                vehicleList.remove(v);
                modelList.remove(temp);
            }
        }

    }


    public void openData(){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("objects.bin"))) {
            vehicleList = (List<Vehicle>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
