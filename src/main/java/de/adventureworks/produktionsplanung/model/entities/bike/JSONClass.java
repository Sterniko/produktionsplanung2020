package de.adventureworks.produktionsplanung.model.entities.bike;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;


public class JSONClass {

    public static void main(String args[]) {

        ArrayList<Bike> bikeArrayList = JSONClass.getBike();
        ArrayList<Frame> frameArrayList = JSONClass.getFrame();
        ArrayList<Saddle> saddleArrayList = JSONClass.getSaddle();
        ArrayList<Fork> forkArrayList = JSONClass.getFork();
        ArrayList<Supplier> supplierArrayList = JSONClass.getSupplier();
        List<BusinessDay> bdList = JSONClass.getBusinessDays();
        HashMap<LocalDate, HashMap<Country, Boolean>> holidayMap = JSONClass.getHoliday();
        ObjectMapper mapper = new ObjectMapper();


        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("bikes.json"), bikeArrayList);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("frame.json"), frameArrayList);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("saddle.json"), saddleArrayList);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("fork.json"), forkArrayList);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("supplier.json"), supplierArrayList);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("holidays.json"), holidayMap);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("businessDays.json"), bdList);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private static ArrayList<Bike> getBike() {

        List<Supplier> supplierList = JSONClass.getSupplier();
        List<Frame> frameList = JSONClass.getFrame();
        List<Saddle> saddleList = JSONClass.getSaddle();
        List<Fork> forkList = JSONClass.getFork();
        //Bikes
        ArrayList<Bike> bikeList = new ArrayList<>();

        bikeList.add(new Bike("MTBAllrounder", frameList.get(0), forkList.get(0), saddleList.get(2)));
        bikeList.add(new Bike("MTBCompetition", frameList.get(2), forkList.get(2), saddleList.get(3)));
        bikeList.add(new Bike("MTBDownhill", frameList.get(1), forkList.get(4), saddleList.get(0)));
        bikeList.add(new Bike("MTBExtreme", frameList.get(2), forkList.get(3), saddleList.get(2)));
        bikeList.add(new Bike("MTBFreeride", frameList.get(1), forkList.get(1), saddleList.get(0)));
        bikeList.add(new Bike("MTBMarathon", frameList.get(0), forkList.get(5), saddleList.get(1)));
        bikeList.add(new Bike("MTBPerformance", frameList.get(1), forkList.get(3), saddleList.get(0)));
        bikeList.add(new Bike("MTBTrail", frameList.get(2), forkList.get(6), saddleList.get(3)));

        return bikeList;
    }

    private static ArrayList<Frame> getFrame() {

        List<Supplier> supplierList = JSONClass.getSupplier();

        ArrayList<Frame> frameList = new ArrayList<>();

        frameList.add(new Frame("7005DB", supplierList.get(0)));
        frameList.add(new Frame("7005TB", supplierList.get(0)));
        frameList.add(new Frame("Monocoque", supplierList.get(0)));

        return frameList;
    }

    private static ArrayList<Saddle> getSaddle() {

        List<Supplier> supplierList = JSONClass.getSupplier();

        ArrayList<Saddle> saddleList = new ArrayList<>();
        saddleList.add(new Saddle("Fizik Tundra", supplierList.get(2)));
        saddleList.add(new Saddle("Race Line", supplierList.get(2)));
        saddleList.add(new Saddle("Spark", supplierList.get(2)));
        saddleList.add(new Saddle("Speed Line", supplierList.get(2)));

        return saddleList;
    }

    private static ArrayList<Fork> getFork() {

        List<Supplier> supplierList = JSONClass.getSupplier();

        ArrayList<Fork> forkList = new ArrayList<>();
        forkList.add(new Fork("Fox32 F100", supplierList.get(1)));
        forkList.add(new Fork("Fox32 F80", supplierList.get(1)));
        forkList.add(new Fork("Fox Talas140", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox Reba", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox Recon351", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox ReconSL", supplierList.get(1)));
        forkList.add(new Fork("SR Suntour Raidon", supplierList.get(1)));

        return forkList;
    }

    public static ArrayList<Supplier> getSupplier() {

        List<Fork> forkList = new ArrayList<>();

        forkList.add(new Fork("Fox32 F100", null));
        forkList.add(new Fork("Fox32 F80", null));
        forkList.add(new Fork("Fox Talas140", null));
        forkList.add(new Fork("Rock Schox Reba", null));
        forkList.add(new Fork("Rock Schox Recon351", null));
        forkList.add(new Fork("Rock Schox ReconSL", null));
        forkList.add(new Fork("SR Suntour Raidon", null));

        List<Frame> frameList = new ArrayList<>();

        frameList.add(new Frame("7005DB", null));
        frameList.add(new Frame("7005TB", null));
        frameList.add(new Frame("Monocoque", null));

        List<Saddle> saddleList = new ArrayList<>();

        saddleList.add(new Saddle("Fizik Tundra", null));
        saddleList.add(new Saddle("Race Line", null));
        saddleList.add(new Saddle("Spark", null));
        saddleList.add(new Saddle("Speed Line", null));


        ArrayList<Supplier> supplierList = new ArrayList<>();

        supplierList.add(new Supplier("WernerRahmenGMBH", 10, 7, Country.GERMANY, null));
        supplierList.add(new Supplier("Tenedores de Zaragoza", 75, 14, Country.SPAIN, null));
        supplierList.add(new Supplier("DengwongSaddles", 500, 49, Country.CHINA, null));

        supplierList.get(0).setComponents(frameList);
        supplierList.get(1).setComponents(forkList);
        supplierList.get(2).setComponents(saddleList);

        return supplierList;
    }

    private static HashMap<LocalDate, HashMap<Country, Boolean>> getHoliday() {


        HashMap<LocalDate, HashMap<Country, Boolean>> dateMap = new HashMap<>();
        LocalDate newYear = LocalDate.of(2019, 1, 1);
        for (int i = 0; i < 365; i++) {
            HashMap<Country, Boolean> holidayMap = new HashMap<>();
            dateMap.put(newYear.plusDays(i), holidayMap);

        }


        // USA
        JSONClass.insertHoliday(LocalDate.of(2019, 1, 1), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 1, 21), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 2, 18), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 5, 27), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 7, 4), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 9, 2), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 10, 14), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 11, 11), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 11, 28), Country.USA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 12, 25), Country.USA, dateMap);

        // Frankreich

        JSONClass.insertHoliday(LocalDate.of(2019, 1, 1), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 4, 22), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 5, 1), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 5, 8), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 5, 30), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 6, 10), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 7, 14), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 8, 15), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 11, 1), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 11, 11), Country.FRANCE, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 12, 25), Country.FRANCE, dateMap);

        // China

        JSONClass.insertHoliday(LocalDate.of(2019, 1, 1), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 2, 4), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 2, 5), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 2, 6), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 4, 5), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 4, 6), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 4, 7), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 6, 7), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 9, 13), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 10, 1), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 10, 2), Country.CHINA, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 10, 3), Country.CHINA, dateMap);

        // Spanien

        JSONClass.insertHoliday(LocalDate.of(2019, 1, 1), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 1, 6), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 4, 19), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 5, 1), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 8, 15), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 10, 12), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 11, 1), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 12, 6), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 12, 8), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 12, 9), Country.SPAIN, dateMap);
        JSONClass.insertHoliday(LocalDate.of(2019, 12, 25), Country.SPAIN, dateMap);

        return dateMap;
    }

    private static void insertHoliday(LocalDate localDate, Country country, HashMap<LocalDate, HashMap<Country, Boolean>> map) {
        HashMap<Country, Boolean> holidayMap = map.get(localDate);
        holidayMap.put(country, true);
    }

    public static List<BusinessDay> getBusinessDays() {
        List<BusinessDay> bdList = new ArrayList<>();
        LocalDate ld = LocalDate.of(2019, 1, 1);
        for (int i = 0; i < 450; i++) {
            BusinessDay businessDay = new BusinessDay(ld.plusDays(i), new HashMap<Supplier, LogisticsObject>(), new ArrayList<LogisticsObject>(), new ArrayList<LogisticsObject>(), new HashMap<Country, Boolean>(), new HashMap<Bike, Integer>(), new HashMap<Bike, Integer>(), new HashMap<Bike, Integer>(), new HashMap<Component, Integer>());
            bdList.add(businessDay);
        }
        return bdList;

    }


}


