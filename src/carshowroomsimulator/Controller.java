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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.*;

import static javafx.application.Platform.exit;

public class Controller {
    public TextField searchTextField;
    public Button shoppingCartButton;
    private CarShowroomContainer container;
    private static List<Vehicle> basketModelList = new ArrayList<>();
    private String brand, model, showroomName;
    private Double price;
    private int yearOfProduction, amount;
    private String selectedShowroom;
    private boolean isReserved = false;
    StringBuilder builder = new StringBuilder();
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private TableView<TableModel> carsTableView = new TableView<>();
    @FXML
    private TableColumn<TableModel, String> brandNameColumn = new TableColumn<>("Brand");
    @FXML
    private TableColumn<TableModel, String> modelNameColumn = new TableColumn<>("Model");
    @FXML
    private TableColumn<TableModel, Double> priceColumn = new TableColumn<>("Price");
    @FXML
    private TableColumn<TableModel, Integer> yearOfProductionColumn = new TableColumn<>("Year");
    @FXML
    private TableColumn<TableModel, String> showroomNameColumn = new TableColumn<>("Showroom");
    @FXML
    private TableColumn<TableModel, Boolean> isReservedColumn = new TableColumn<>("Reserved");
    private ObservableList<TableModel> modelList = FXCollections.observableArrayList();
    @FXML
    Tooltip tableTooltip;
    DataGenerator dataGenerator;

    public void setData(DataGenerator dataGenerator) {

        cityComboBox.getItems().add("Any");

        container = new CarShowroomContainer(dataGenerator.getCarShowroomLinkedHashMap());
        for (Map.Entry<String, CarShowroom> entry : container.getShowroomMap().entrySet()) {
            showroomName = entry.getValue().getShowroomName();
            cityComboBox.getItems().add(showroomName);
        }

        loadAllShowrooms();
        brandNameColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        modelNameColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        yearOfProductionColumn.setCellValueFactory(cellData -> cellData.getValue().yearOfProductionProperty().asObject());
        showroomNameColumn.setCellValueFactory(cellData -> cellData.getValue().showroomNameProperty());
        isReservedColumn.setCellValueFactory(cellData -> cellData.getValue().isReservedProperty().asObject());

        carsTableView.getColumns().clear();
        carsTableView.getColumns().addAll(brandNameColumn, modelNameColumn, priceColumn, yearOfProductionColumn, showroomNameColumn, isReservedColumn);

    }

    public void changeTableView() {
        carsTableView.getItems().clear();
        for (Map.Entry<String, CarShowroom> entry : container.getShowroomMap().entrySet()) {
            List<Vehicle> carsList = entry.getValue().getCarList();
            showroomName = entry.getValue().getShowroomName();

            for (Vehicle v : carsList) {
                if (selectedShowroom.equals("Any")) {
                    brand = v.getBrand();
                    model = v.getModel();
                    price = v.getPrice();
                    yearOfProduction = v.getYearOfProduction();
                    isReserved = v.isReserved();
                    modelList.add(new TableModel(brand, model, price, yearOfProduction, showroomName, isReserved));
                    carsTableView.setItems(modelList);
                }
                if (selectedShowroom.equals(showroomName)) {
                    brand = v.getBrand();
                    model = v.getModel();
                    price = v.getPrice();
                    yearOfProduction = v.getYearOfProduction();
                    isReserved = v.isReserved();
                    modelList.add(new TableModel(brand, model, price, yearOfProduction, showroomName, isReserved));
                    carsTableView.setItems(modelList);
                }

            }

        }

    }

    @FXML
    public void handleCityComboBox() {
        selectedShowroom = cityComboBox.getValue();
        changeTableView();
    }

    @FXML
    public TableModel handleMouseClickOnTable() {
        return carsTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void handleCarBooking() {
        TableModel selectedItem = handleMouseClickOnTable();
        CarShowroom selectedShowroom = container.findShowroomByName(selectedItem.getShowroomName());
        Vehicle selectedCar = container.getShowroomMap().get(selectedShowroom.getShowroomName()).search(selectedItem.getBrand());
        if (selectedCar.isReserved()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("You can't do that!");
            errorAlert.setContentText("Selected car is actually reserved.");
            errorAlert.showAndWait();
        }
        container.getShowroomMap().get(selectedShowroom.getShowroomName()).reserveCar(selectedCar);
        changeTableView();
        System.out.println(selectedCar.isReserved());

    }

    @FXML
    public void handleMouseHoverOnTable() {

        carsTableView.setRowFactory(tableView -> {
            final TableRow<TableModel> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                final TableModel tableModel = row.getItem();

                if (row.isHover() && tableModel != null) {
                    CarShowroom selectedShowroom = container.findShowroomByName(tableModel.getShowroomName());
                    Vehicle selectedCar = container.getShowroomMap().get(selectedShowroom.getShowroomName()).search(tableModel.getBrand());

                    tableTooltip.setText(
                            "Engine capacity: " + selectedCar.getEngineCapacity() + System.lineSeparator() +
                                    "Amount: " + selectedCar.getAmount() + System.lineSeparator() +
                                    "Condition: " + selectedCar.getCondition() + System.lineSeparator() +
                                    "Mileage: " + selectedCar.getMileage()
                    );
                } else {
                    tableTooltip.setText("Available cars table.");
                }
            });

            return row;
        });
    }

    @FXML
    public void handleBuyCar() {
        TableModel selectedItem = handleMouseClickOnTable();
        CarShowroom selectedShowroom = container.findShowroomByName(selectedItem.getShowroomName());
        Vehicle selectedCar = container.getShowroomMap().get(selectedShowroom.getShowroomName()).search(selectedItem.getBrand());

        if (selectedCar.isReserved()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("You can't do that!");
            errorAlert.setContentText("Selected car is actually reserved.");
            errorAlert.showAndWait();
        } else {
            container.getShowroomMap().get(selectedShowroom.getShowroomName()).getProduct(selectedCar);
            changeTableView();
        }


    }


    public void handleSearchCar(KeyEvent keyEvent) {

        builder.append(searchTextField.getText());

        if (keyEvent.getCode() == KeyCode.BACK_SPACE)
            builder.setLength(Math.max(builder.length() - 1, 0));

        carsTableView.getItems().clear();
        for (Map.Entry<String, CarShowroom> entry : container.getShowroomMap().entrySet()) {
            List<Vehicle> carsList = entry.getValue().getCarList();
            showroomName = entry.getValue().getShowroomName();
            for (Vehicle v : carsList) {
                if (v.getBrand().startsWith(builder.toString())) {
                    brand = v.getBrand();
                    model = v.getModel();
                    price = v.getPrice();
                    yearOfProduction = v.getYearOfProduction();
                    isReserved = v.isReserved();
                    modelList.add(new TableModel(brand, model, price, yearOfProduction, showroomName, isReserved));
                    carsTableView.setItems(modelList);
                }

            }
        }
        builder.delete(0, builder.length());
    }

    public void handleShoppingCart(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shoppingCart.fxml"));
        Stage stage = new Stage();
        stage.initOwner(shoppingCartButton.getScene().getWindow());
        stage.setScene(new Scene((Parent) loader.load()));
        stage.showAndWait();

    }


    public DataGenerator getDataGenerator() {
        return dataGenerator;
    }

    public void setDataGenerator(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public void handleAddingToShoppingCart(ActionEvent actionEvent) {
        TableModel selectedItem = handleMouseClickOnTable();
        CarShowroom selectedShowroom = container.findShowroomByName(selectedItem.getShowroomName());

        Vehicle selectedCar = container.getShowroomMap().get(selectedShowroom.getShowroomName()).search(selectedItem.getBrand());
        brand = selectedCar.getBrand();
        model = selectedCar.getModel();
        amount = selectedCar.getAmount();
        price = selectedCar.getPrice();
        basketModelList.add(selectedCar);
    }


    public static List<Vehicle> getBasket() {
        return basketModelList;
    }

    public void handleSavingShowroomToCSV(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Car Showrooms Simulator");
        alert.setHeaderText("Save showrooms data to CSV?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("Save selected showroom");
        ButtonType buttonTypeTwo = new ButtonType("Save all");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            CarShowroom selectedShowroom = container.findShowroomByName(cityComboBox.getValue());
            CSVFileWriter.writeCsvFile(selectedShowroom);
        } else if (result.get() == buttonTypeTwo) {
            saveAllShowrooms();
        } else {
            alert.close();
        }
    }

    public void handleLoadingDataToShowroom(ActionEvent actionEvent) throws FileNotFoundException {
        CarShowroom selectedShowroom = container.findShowroomByName(cityComboBox.getValue());
        selectedShowroom.getCarList().clear();
        selectedShowroom.getCarList().addAll(Objects.requireNonNull(CSVFileReader.readCsvFile(selectedShowroom)));
        changeTableView();
    }

    public void handleExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Car Showrooms Simulator");
        alert.setHeaderText("Do you want to save selected Showroom data to file?");
        alert.setContentText("Choose yes or no.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            CarShowroom selectedShowroom = container.findShowroomByName(cityComboBox.getValue());
            CSVFileWriter.writeCsvFile(selectedShowroom);
        }

        exit();
    }

    public void saveAllShowrooms() {
        for (Map.Entry<String, CarShowroom> entry : container.getShowroomMap().entrySet()) {
            CarShowroom showroom = container.findShowroomByName(entry.getValue().getShowroomName());
            CSVFileWriter.writeCsvFile(showroom);
        }
    }

    public void loadAllShowrooms() {
        for (Map.Entry<String, CarShowroom> entry : container.getShowroomMap().entrySet()) {
            CarShowroom showroom = container.findShowroomByName(entry.getValue().getShowroomName());
            try {
                showroom.getCarList().addAll(CSVFileReader.readCsvFile(showroom));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Car Showrooms Simulator");
                alert.setHeaderText("Error while loading data to showrooms!");
                alert.setContentText("File " + showroom.getShowroomName() +".csv not found!");
                alert.showAndWait();
            }
        }
    }
}