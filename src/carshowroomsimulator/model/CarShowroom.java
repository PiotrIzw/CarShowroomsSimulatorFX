package carshowroomsimulator.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CarShowroom {

    private final String showroomName;
    private final List<Vehicle> carList;
    private final int maxShowroomCapacity;

    public CarShowroom(String showroomName, List<Vehicle> carList, int maxShowroomCapacity) {
        this.showroomName = showroomName;
        this.carList = carList;
        this.maxShowroomCapacity = maxShowroomCapacity;
    }

    public int getMaxShowroomCapacity() {
        return maxShowroomCapacity;
    }

    public List<Vehicle> getCarList() {
        return carList;
    }

    public String getShowroomName() {
        return showroomName;
    }

    public void addProduct(Vehicle product) {
        if (carList.size() < maxShowroomCapacity) {
            if (carList.size() == 0) {
                carList.add(product);
            } else {
                for (Vehicle v : carList) {
                    if (v.compareTo(product) == 0) {
                        v.changeVehicleAmountByOne(true);
                        break;
                    } else {
                        carList.add(product);
                        break;
                    }
                }
            }
        } else
            System.err.println("Exceeded the Showroom Capacity");


    }

    public void getProduct(Vehicle product) {
        for (Vehicle v : carList) {
            if (v.compareTo(product) == 0) {
                if (v.getAmount() > 1) {
                    v.changeVehicleAmountByOne(false);
                    break;
                } else {
                    carList.remove(v);
                    break;
                }
            }
        }
    }

    public void removeProduct(Vehicle product) {
        carList.remove(product);
    }

    public void removeProduct(String carBrand) {
        carList.remove(search(carBrand));
    }

    public Vehicle search(String productName) {
//
//        return Collections.max(carList, new Comparator<Vehicle>() {
//            @Override
//            public int compare(Vehicle v1, Vehicle v2) {
//                return v1.getBrand().compareTo(productName);
//            }
//        });

        for(Vehicle v : carList){
            if(v.getBrand().contains(productName)){
                return v;
            }
        }
        return null;
    }

    public Vehicle searchPartial(String partOfProductName) {
        for (Vehicle v : carList) {
            if (v.getBrand().startsWith(partOfProductName)) {
                return v;
            }
        }
        return null;
    }

    public int countByCondition(ItemCondition condition) {
        int numberOfProducts = 0;
        for (Vehicle v : carList) {
            if (v.getCondition().equals(condition)) {
                numberOfProducts++;
            }
        }
        return numberOfProducts;
    }

    public void summary() {
        for (Vehicle v : carList) {
            v.print();
        }
    }

    public List<Vehicle> sortByName() {
        //carList.sort((o1, o2) -> o1.getBrand().compareTo(o2.getBrand()));
        Collections.sort(carList);
//      carList.sort(new Comparator<Vehicle>() {
//            @Override
//            public int compare(Vehicle o1, Vehicle o2) {
//                return o1.getBrand().compareTo(o2.getBrand());
//            }
//        });
        return carList;

    }

    public List<Vehicle> sortByAmount() {
        Collections.sort(carList, new Vehicle());
//        carList.sort(new Comparator<>() {
//            @Override
//            public int compare(Vehicle o1, Vehicle o2) {
//                return Integer.compare(o1.getAmount(), o2.getAmount());
//            }
//        });
//        Collections.reverse(carList);
        return carList;
    }

    public void reserveCar(Vehicle product) {
        for (Vehicle v : carList) {
            if (v.compareTo(product) == 0) {
                v.setReserved(true);
            }
        }
    }


    public Vehicle max() {
        return Collections.max(carList, Comparator.comparingInt(Vehicle::getAmount));
    }
}
