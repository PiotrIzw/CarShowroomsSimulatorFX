package carshowroomsimulator.model;
import carshowroomsimulator.model.CarShowroom;
import carshowroomsimulator.model.ItemCondition;
import carshowroomsimulator.model.Vehicle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {

    private static final String COMMA_DELIMITER = ",";
    private static final int BRAND = 0;
    private static final int MODEL = 1;
    private static final int CONDITION = 2;
    private static final int PRICE = 3;
    private static final int YEAR = 4;
    private static final int MILEAGE = 5;
    private static final int ENGINE = 6;
    private static final int AMOUNT = 7;
    private static final int IS_RESERVED = 8;

    public static List<Vehicle> readCsvFile(CarShowroom carShowroom){

        String fileName = carShowroom.getShowroomName() + ".csv";
        BufferedReader fileReader = null;

        try {

            List<Vehicle> list = new ArrayList<>();

            String line = "";

            fileReader = new BufferedReader(new FileReader(fileName));

            fileReader.readLine();

            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {

                   Vehicle v = new Vehicle(String.valueOf(tokens[BRAND]),
                            String.valueOf(tokens[MODEL]),
                            ItemCondition.NEW,
                            Double.parseDouble(tokens[PRICE]),
                            Integer.parseInt(tokens[YEAR]),
                            Double.parseDouble(tokens[MILEAGE]),
                            Double.parseDouble(tokens[ENGINE]),
                            Integer.parseInt(tokens[AMOUNT]),
                            Boolean.parseBoolean(tokens[IS_RESERVED])
                            );


                   list.add(v);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
