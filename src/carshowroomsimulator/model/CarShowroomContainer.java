package carshowroomsimulator.model;

import java.util.*;

public class CarShowroomContainer{

    private final Map<String, CarShowroom> showroomMap;
    public CarShowroomContainer(Map<String, CarShowroom> showroomMap) {
        this.showroomMap = showroomMap;
    }

    public Map<String, CarShowroom> getShowroomMap() {
        return showroomMap;
    }

    public CarShowroom addCenter(String showroomName, int showroomCapacity) {
        CarShowroom showroom = new CarShowroom(showroomName, new ArrayList<>(), showroomCapacity);
        showroomMap.put(showroomName, showroom);
        return showroom;
    }

    public void removeCenter(String showroomName) {
        showroomMap.remove(showroomName);
    }

    public List<CarShowroom> findEmpty() {
        List<CarShowroom> emptyCarsList = new ArrayList<>();
        for (Map.Entry<String, CarShowroom> entry : showroomMap.entrySet()) {
            if (entry.getValue().getCarList().size() == 0) {
                emptyCarsList.add(entry.getValue());
            }
        }
        return emptyCarsList;
    }


    public static LinkedHashMap<String, CarShowroom> sortByValue(LinkedHashMap<String, CarShowroom> hm)
    {
        List<Map.Entry<String, CarShowroom> > list =
                new LinkedList<Map.Entry<String, CarShowroom> >(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, CarShowroom> >() {
            public int compare(Map.Entry<String, CarShowroom> o1,
                               Map.Entry<String, CarShowroom> o2)
            {
                return (o1.getValue().getMaxShowroomCapacity()) - (o2.getValue().getMaxShowroomCapacity());
            }
        });

        LinkedHashMap<String, CarShowroom> temp = new LinkedHashMap<String, CarShowroom>();
        for (Map.Entry<String, CarShowroom> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    public CarShowroom findShowroomByName(String name) {

        for (Map.Entry<String, CarShowroom> entry : showroomMap.entrySet()) {
            if (entry.getValue().getShowroomName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void summary() {
        for (Map.Entry<String, CarShowroom> entry : showroomMap.entrySet()) {
            double showroomCapacity = entry.getValue().getMaxShowroomCapacity();
            double showroomFilling = entry.getValue().getCarList().size();
            String showroomName = entry.getValue().getShowroomName();
            double percentageFilling = (showroomFilling / showroomCapacity) * 100;
            System.out.println("Showroom Name: " + showroomName + ", Percentage Filling: " + percentageFilling + "%");

        }
    }


}
