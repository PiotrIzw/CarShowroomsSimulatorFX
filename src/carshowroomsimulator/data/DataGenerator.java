package carshowroomsimulator.data;

import carshowroomsimulator.model.CarShowroom;
import carshowroomsimulator.model.CarShowroomContainer;
import carshowroomsimulator.model.ItemCondition;
import carshowroomsimulator.model.Vehicle;

import java.util.LinkedHashMap;


public final class DataGenerator {

    private static DataGenerator instance = null;
    private final CarShowroomContainer showrooms;
    private final LinkedHashMap<String, CarShowroom> carShowroomLinkedHashMap = new LinkedHashMap<>();

    public static DataGenerator getInstance() {
        if(instance != null){
            System.err.println("Instance already exists!");
        }
        if (instance == null) {
            instance = new DataGenerator();
            return instance;
        }
        return null;
    }

    public CarShowroomContainer getShowrooms() {
        return showrooms;
    }

    public LinkedHashMap<String, CarShowroom> getCarShowroomLinkedHashMap() {
        return carShowroomLinkedHashMap;
    }

    DataGenerator(){
        Vehicle car1 = new Vehicle("Fiat", "Uno", ItemCondition.NEW, 40000.0, 2020, 180, 1.6, 1, false);
        Vehicle car2 = new Vehicle("Mercedes", "W213", ItemCondition.USED, 20000.0, 2005, 180000, 2.6, 1, false);
        Vehicle car3 = new Vehicle("Toyota", "Supra", ItemCondition.DAMAGED, 30000.0, 2000, 65000, 4.2, 1, true);
        Vehicle car4 = new Vehicle("Ford", "Focus", ItemCondition.USED, 3000.0, 2000, 365000, 1.4, 1, false);
        Vehicle car5 = new Vehicle("Opel", "Corsa", ItemCondition.USED, 900.0, 1995, 155000, 1.2, 1, false);
        Vehicle car6 = new Vehicle("Scoda", "Felicia", ItemCondition.DAMAGED, 30000.0, 2000, 65000, 4.2, 1, false);


        showrooms = new CarShowroomContainer(carShowroomLinkedHashMap);

        CarShowroom carShowroom1 = showrooms.addCenter("Cars Showroom 1", 5);
        CarShowroom carShowroom2 = showrooms.addCenter("Cars Showroom 2", 4);
        CarShowroom carShowroom3 = showrooms.addCenter("Cars Showroom 3", 30);
        CarShowroom carShowroom4 = showrooms.addCenter("Cars Showroom 4", 100);
        CarShowroom carShowroom5 = showrooms.addCenter("Cars Showroom 5", 1);
        CarShowroom carShowroom6 = showrooms.addCenter("Cars Showroom 6", 20);

//        carShowroom1.addProduct(car1);
//        carShowroom1.addProduct(car2);
//        carShowroom1.addProduct(car3);
//        carShowroom1.addProduct(car4);
//        carShowroom1.addProduct(car5);
//
//        carShowroom2.addProduct(car1);
//        carShowroom2.addProduct(car1);
//        carShowroom2.addProduct(car1);
//        carShowroom2.addProduct(car5);
//        carShowroom2.addProduct(car4);
//
//        carShowroom3.addProduct(car1);
//        carShowroom3.addProduct(car2);
//        carShowroom3.addProduct(car3);
//        carShowroom3.addProduct(car4);
//        carShowroom3.addProduct(car5);
//
//        carShowroom4.addProduct(car6);
//        carShowroom4.addProduct(car6);
//
//        carShowroom5.addProduct(car2);
//
//        carShowroom6.addProduct(car1);
//        carShowroom6.addProduct(car4);
//        carShowroom6.addProduct(car5);
//        carShowroom6.addProduct(car2);
    }
}
