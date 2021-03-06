package de.adventureworks.produktionsplanung.model.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adventureworks.produktionsplanung.model.entities.bike.*;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JSONService {

    public static void main(String args[]) {

        ArrayList<Bike> bikeArrayList = JSONService.getBike();
        ArrayList<Frame> frameArrayList = JSONService.getFrame();
        ArrayList<Saddle> saddleArrayList = JSONService.getSaddle();
        ArrayList<Fork> forkArrayList = JSONService.getFork();
        ArrayList<Ship> shipArrayList = JSONService.getShips();
        ArrayList<Supplier> supplierArrayList = JSONService.getSupplier();
        List<BusinessDay> bdList = JSONService.getBusinessDays();
        HashMap<LocalDate, HashMap<Country, Boolean>> holidayMap = JSONService.getHoliday();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // mapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/bikes.json"), bikeArrayList);
            // mapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/frame.json"), frameArrayList);
            // mapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/saddle.json"), saddleArrayList);
            // mapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/fork.json"), forkArrayList);
            // mapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/supplier.json"), supplierArrayList);
            // mapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/holidays.json"), holidayMap);
            // mapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/businessDays.json"), bdList);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("JSONs/ships.json"), shipArrayList);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private static ArrayList<Bike> getBike() {

        List<Supplier> supplierList = JSONService.getSupplier();
        List<Frame> frameList = JSONService.getFrame();
        List<Saddle> saddleList = JSONService.getSaddle();
        List<Fork> forkList = JSONService.getFork();
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

        List<Supplier> supplierList = JSONService.getSupplier();

        ArrayList<Frame> frameList = new ArrayList<>();

        frameList.add(new Frame("7005DB", supplierList.get(0)));
        frameList.add(new Frame("7005TB", supplierList.get(0)));
        frameList.add(new Frame("Monocoque", supplierList.get(0)));

        return frameList;
    }

    private static ArrayList<Saddle> getSaddle() {

        List<Supplier> supplierList = JSONService.getSupplier();

        ArrayList<Saddle> saddleList = new ArrayList<>();
        saddleList.add(new Saddle("Fizik Tundra", supplierList.get(2)));
        saddleList.add(new Saddle("Race Line", supplierList.get(2)));
        saddleList.add(new Saddle("Spark", supplierList.get(2)));
        saddleList.add(new Saddle("Speed Line", supplierList.get(2)));

        return saddleList;
    }

    private static ArrayList<Fork> getFork() {

        List<Supplier> supplierList = JSONService.getSupplier();

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

        supplierList.add(new Supplier("Rahmenlieferant AG", 10, 7, Country.GERMANY, null));
        supplierList.add(new Supplier("Spanish Gables United", 75, 14, Country.SPAIN, null));
        supplierList.add(new Supplier("Saddles of China Society", 500, 49, Country.CHINA, null));

        supplierList.get(0).setComponents(frameList);
        supplierList.get(1).setComponents(forkList);
        supplierList.get(2).setComponents(saddleList);

        return supplierList;
    }

    public static HashMap<LocalDate, HashMap<Country, Boolean>> getHoliday() {


        HashMap<LocalDate, HashMap<Country, Boolean>> dateMap = new HashMap<>();
        LocalDate newYear = LocalDate.of(2020, 11, 1);
        for (int i = 0; i < 500; i++) {
            HashMap<Country, Boolean> holidayMap = new HashMap<>();
            for (Country c : Country.values()) {
                holidayMap.put(c, false);
            }
            dateMap.put(newYear.plusDays(i), holidayMap);
        }


        // USA
        JSONService.insertHoliday(LocalDate.of(2020, 12, 25), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 11, 22), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 11, 12), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 11, 11), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 1, 1), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 1, 21), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 2, 18), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 5, 27), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 7, 4), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 9, 2), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 10, 14), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 11, 11), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 11, 28), Country.USA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 12, 25), Country.USA, dateMap);

        // Frankreich
        JSONService.insertHoliday(LocalDate.of(2020, 11, 1), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 11, 11), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 12, 25), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 1, 1), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 4, 22), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 5, 1), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 5, 8), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 5, 30), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 6, 10), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 7, 14), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 8, 15), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 11, 1), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 11, 11), Country.FRANCE, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 12, 25), Country.FRANCE, dateMap);

        // China

        JSONService.insertHoliday(LocalDate.of(2021, 1, 1), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 2, 4), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 2, 5), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 2, 6), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 4, 5), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 4, 6), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 4, 7), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 6, 7), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 9, 13), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 10, 1), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 10, 2), Country.CHINA, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 10, 3), Country.CHINA, dateMap);

        // Spanien

        JSONService.insertHoliday(LocalDate.of(2020, 11, 1), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 12, 6), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 12, 8), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 12, 9), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 12, 25), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 1, 1), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 1, 6), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 4, 19), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 5, 1), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 8, 15), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 10, 12), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 11, 1), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 12, 6), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 12, 8), Country.SPAIN, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 12, 25), Country.SPAIN, dateMap);

        //Germany
        JSONService.insertHoliday(LocalDate.of(2020, 12, 25), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2020, 12, 26), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 1, 1), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 1, 6), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 4, 19), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 4, 21), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 4, 22), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 5, 1), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 5, 30), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 6, 10), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 10, 3), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 6, 20), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 12, 25), Country.GERMANY, dateMap);
        JSONService.insertHoliday(LocalDate.of(2021, 12, 26), Country.GERMANY, dateMap);

        LocalDate firstSunday = LocalDate.of(2020, 11, 8);
        for (int i = 0; i < 426; i += 7) {
            JSONService.insertHoliday(firstSunday.plusDays(i), Country.GERMANY, dateMap);
        }

        return dateMap;
    }

    private static ArrayList<Ship> getShips() {
        ArrayList<Ship> shipList = new ArrayList<>();
        shipList.add(new Ship("MSC PRODUKTIONSPLANUNG", LocalDate.of(2020, 11, 16), LocalDate.of(2020, 12, 14)));
        shipList.add(new Ship("APL VANDA 0", LocalDate.of(2020, 11, 16), LocalDate.of(2020, 12, 14)));
        shipList.add(new Ship("TAURUS 0", LocalDate.of(2020, 11, 30), LocalDate.of(2020, 12, 27)));
        shipList.add(new Ship("TITAN 0", LocalDate.of(2020, 12, 2), LocalDate.of(2021, 1, 1)));
        shipList.add(new Ship("EPPA ADAMAH", LocalDate.of(2020, 12, 4), LocalDate.of(2021, 1, 6)));
        shipList.add(new Ship("MSC HARTMANN", LocalDate.of(2020, 12, 11), LocalDate.of(2021, 1, 12)));
        shipList.add(new Ship("TEXAS TRIUMPH 0", LocalDate.of(2020, 12, 25), LocalDate.of(2021, 1, 25)));
        shipList.add(new Ship("EVER GENIUS 0", LocalDate.of(2021, 1, 4), LocalDate.of(2021, 2, 3)));
        shipList.add(new Ship("TOLEDO TRIUMPH 0", LocalDate.of(2021, 1, 11), LocalDate.of(2021, 2, 10)));
        shipList.add(new Ship("CMA CGM ZHENG HE", LocalDate.of(2021, 1, 16), LocalDate.of(2021, 2, 14)));
        shipList.add(new Ship("TAURUS", LocalDate.of(2021, 1, 21), LocalDate.of(2021, 2, 18)));
        shipList.add(new Ship("EVER GENIUS", LocalDate.of(2021, 1, 22), LocalDate.of(2021, 2, 19)));
        shipList.add(new Ship("APL VANDA", LocalDate.of(2021, 1, 22), LocalDate.of(2021, 2, 20)));
        shipList.add(new Ship("CMA CGM ANTOINE DE SAINT EXUPE", LocalDate.of(2021, 1, 23), LocalDate.of(2021, 2, 21)));
        shipList.add(new Ship("EVER GRADE", LocalDate.of(2021, 1, 31), LocalDate.of(2021, 2, 27)));
        shipList.add(new Ship("CMA CGM MAGELLAN", LocalDate.of(2021, 1, 30), LocalDate.of(2021, 2, 27)));
        shipList.add(new Ship("CMA CGM LOUIS BLERIOT", LocalDate.of(2021, 2, 2), LocalDate.of(2021, 3, 1)));
        shipList.add(new Ship("TOLEDO TRIUMPH", LocalDate.of(2021, 2, 4), LocalDate.of(2021, 3, 6)));
        shipList.add(new Ship("TITAN", LocalDate.of(2021, 2, 5), LocalDate.of(2021, 3, 4)));
        shipList.add(new Ship("APL CHANGI", LocalDate.of(2021, 2, 8), LocalDate.of(2021, 3, 8)));
        shipList.add(new Ship("CMA CGM VASCO DE GAMA", LocalDate.of(2021, 2, 7), LocalDate.of(2021, 3, 8)));
        shipList.add(new Ship("COSCO SHIPPING DENALI", LocalDate.of(2021, 2, 14), LocalDate.of(2021, 3, 15)));
        shipList.add(new Ship("EVER GOODS", LocalDate.of(2021, 2, 11), LocalDate.of(2021, 3, 12)));
        shipList.add(new Ship("CMA CGM JEAN MERMOZ", LocalDate.of(2021, 2, 14), LocalDate.of(2021, 3, 14)));
        shipList.add(new Ship("TOKYO TRIUMPH", LocalDate.of(2021, 2, 18), LocalDate.of(2021, 3, 19)));
        shipList.add(new Ship("CMA CGM GEORG FORSTER", LocalDate.of(2021, 2, 19), LocalDate.of(2021, 3, 20)));
        shipList.add(new Ship("APL RAFFLES", LocalDate.of(2021, 3, 5), LocalDate.of(2021, 4, 3)));
        shipList.add(new Ship("COSCO SHIPPING RHINE", LocalDate.of(2021, 2, 24), LocalDate.of(2021, 3, 24)));
        shipList.add(new Ship("EVER GOLDEN", LocalDate.of(2021, 2, 25), LocalDate.of(2021, 3, 27)));
        shipList.add(new Ship("CMA CGM CHRISTOPHE COLOMB", LocalDate.of(2021, 2, 26), LocalDate.of(2021, 3, 27)));
        shipList.add(new Ship("CMA CGM BOUGAINVILLE", LocalDate.of(2021, 2, 27), LocalDate.of(2021, 3, 28)));
        shipList.add(new Ship("COSCO SHIPPING ANDES", LocalDate.of(2021, 3, 3), LocalDate.of(2021, 4, 1)));
        shipList.add(new Ship("TEXAS TRIUMPH", LocalDate.of(2021, 3, 3), LocalDate.of(2021, 4, 2)));
        shipList.add(new Ship("CMA CGM ALEXANDER VON HUMBOLDT", LocalDate.of(2021, 3, 6), LocalDate.of(2021, 4, 4)));
        shipList.add(new Ship("CMA CGM CORTE REAL", LocalDate.of(2021, 3, 12), LocalDate.of(2021, 4, 10)));
        shipList.add(new Ship("THALASSA TYHI", LocalDate.of(2021, 3, 10), LocalDate.of(2021, 4, 8)));
        shipList.add(new Ship("EVER GIFTED", LocalDate.of(2021, 3, 11), LocalDate.of(2021, 4, 9)));
        shipList.add(new Ship("CMA CGM BENJAMIN FRANKLIN", LocalDate.of(2021, 3, 13), LocalDate.of(2021, 4, 11)));
        shipList.add(new Ship("THALASSA ELPIDA", LocalDate.of(2021, 3, 17), LocalDate.of(2021, 4, 15)));
        shipList.add(new Ship("THALASSA DOXA", LocalDate.of(2021, 3, 18), LocalDate.of(2021, 4, 16)));
        shipList.add(new Ship("APL TEMASEK", LocalDate.of(2021, 3, 20), LocalDate.of(2021, 4, 18)));
        shipList.add(new Ship("CMA CGM AMERIGO VESPUCCI", LocalDate.of(2021, 3, 19), LocalDate.of(2021, 4, 17)));
        shipList.add(new Ship("EVER GIVEN", LocalDate.of(2021, 3, 25), LocalDate.of(2021, 4, 23)));
        shipList.add(new Ship("CMA CGM KERGUELEN", LocalDate.of(2021, 3, 27), LocalDate.of(2021, 4, 25)));
        shipList.add(new Ship("CMA CGM MARCO POLO", LocalDate.of(2021, 3, 26), LocalDate.of(2021, 4, 24)));
        shipList.add(new Ship("THALASSA NIKI", LocalDate.of(2021, 4, 1), LocalDate.of(2021, 4, 30)));
        shipList.add(new Ship("CMA CGM JULES VERNE", LocalDate.of(2021, 4, 3), LocalDate.of(2021, 5, 4)));
        shipList.add(new Ship("CMA CGM LAPEROUSE", LocalDate.of(2021, 4, 2), LocalDate.of(2021, 5, 1)));
        shipList.add(new Ship("EVER GENIUS II", LocalDate.of(2021, 4, 8), LocalDate.of(2021, 5, 10)));
        shipList.add(new Ship("CMA CGM ZHENG HE II", LocalDate.of(2021, 4, 10), LocalDate.of(2021, 5, 9)));
        shipList.add(new Ship("APL VANDA", LocalDate.of(2021, 4, 9), LocalDate.of(2021, 5, 8)));
        shipList.add(new Ship("EVER GRADE II", LocalDate.of(2021, 4, 15), LocalDate.of(2021, 5, 14)));
        shipList.add(new Ship("CMA CGM ANTOINE DE SAINT EXUPE", LocalDate.of(2021, 4, 17), LocalDate.of(2021, 5, 16)));
        shipList.add(new Ship("CMA CGM MAGELLAN II", LocalDate.of(2021, 4, 16), LocalDate.of(2021, 5, 15)));
        shipList.add(new Ship("TITAN II", LocalDate.of(2021, 4, 22), LocalDate.of(2021, 5, 20)));
        shipList.add(new Ship("CMA CGM LOUIS BLERIOT II", LocalDate.of(2021, 4, 24), LocalDate.of(2021, 5, 25)));
        shipList.add(new Ship("EVER GOODS II", LocalDate.of(2021, 4, 29), LocalDate.of(2021, 5, 28)));
        shipList.add(new Ship("CMA CGM VASCO DE GAMA II", LocalDate.of(2021, 5, 1), LocalDate.of(2021, 5, 30)));
        shipList.add(new Ship("APL FULLERTON II", LocalDate.of(2021, 4, 30), LocalDate.of(2021, 5, 29)));
        shipList.add(new Ship("CMA CGM JEAN MERMOZ II", LocalDate.of(2021, 5, 8), LocalDate.of(2021, 6, 6)));
        shipList.add(new Ship("CMA CGM GEORG FORSTER II", LocalDate.of(2021, 5, 7), LocalDate.of(2021, 6, 5)));
        shipList.add(new Ship("EVER GOLDEN II", LocalDate.of(2021, 5, 13), LocalDate.of(2021, 6, 11)));
        shipList.add(new Ship("TEXAS TRIUMPH II", LocalDate.of(2021, 5, 20), LocalDate.of(2021, 6, 18)));
        shipList.add(new Ship("CMA CGM BOUGAINVILLE II", LocalDate.of(2021, 5, 22), LocalDate.of(2021, 6, 20)));
        shipList.add(new Ship("CMA CGM ALEXANDER VON HUMBOLD II", LocalDate.of(2021, 5, 29), LocalDate.of(2021, 6, 27)));
        shipList.add(new Ship("EVER GRADE III", LocalDate.of(2021, 5, 31), LocalDate.of(2021, 6, 27)));
        shipList.add(new Ship("CMA CGM MAGELLAN III", LocalDate.of(2021, 5, 30), LocalDate.of(2021, 6, 27)));
        shipList.add(new Ship("CMA CGM LOUIS BLERIOT III", LocalDate.of(2021, 6, 2), LocalDate.of(2021, 7, 1)));
        shipList.add(new Ship("TOLEDO TRIUMPH III", LocalDate.of(2021, 6, 4), LocalDate.of(2021, 7, 6)));
        shipList.add(new Ship("TITAN III", LocalDate.of(2021, 6, 5), LocalDate.of(2021, 7, 4)));
        shipList.add(new Ship("APL CHANGI III", LocalDate.of(2021, 6, 8), LocalDate.of(2021, 7, 8)));
        shipList.add(new Ship("CMA CGM VASCO DE GAMA III", LocalDate.of(2021, 6, 7), LocalDate.of(2021, 7, 8)));
        shipList.add(new Ship("COSCO SHIPPING DENALI III", LocalDate.of(2021, 6, 14), LocalDate.of(2021, 7, 15)));
        shipList.add(new Ship("EVER GOODS III", LocalDate.of(2021, 2, 11), LocalDate.of(2021, 7, 12)));
        shipList.add(new Ship("CMA CGM JEAN MERMOZ III", LocalDate.of(2021, 6, 14), LocalDate.of(2021, 7, 14)));
        shipList.add(new Ship("TOKYO TRIUMPH III", LocalDate.of(2021, 6, 18), LocalDate.of(2021, 7, 19)));
        shipList.add(new Ship("CMA CGM GEORG FORSTER III", LocalDate.of(2021, 6, 19), LocalDate.of(2021, 7, 20)));
        shipList.add(new Ship("APL RAFFLES III", LocalDate.of(2021, 7, 5), LocalDate.of(2021, 4, 7)));
        shipList.add(new Ship("COSCO SHIPPING RHINE III", LocalDate.of(2021, 6, 24), LocalDate.of(2021, 7, 24)));
        shipList.add(new Ship("EVER GOLDEN III", LocalDate.of(2021, 6, 25), LocalDate.of(2021, 7, 27)));
        shipList.add(new Ship("CMA CGM CHRISTOPHE COLOMB III", LocalDate.of(2021, 6, 26), LocalDate.of(2021, 7, 27)));
        shipList.add(new Ship("CMA CGM BOUGAINVILLE III", LocalDate.of(2021, 6, 27), LocalDate.of(2021, 7, 28)));
        shipList.add(new Ship("COSCO SHIPPING ANDES III", LocalDate.of(2021, 7, 3), LocalDate.of(2021, 8, 1)));
        shipList.add(new Ship("TEXAS TRIUMPH III", LocalDate.of(2021, 7, 3), LocalDate.of(2021, 8, 2)));
        shipList.add(new Ship("CMA CGM ALEXANDER VON HUMBOLDT III", LocalDate.of(2021, 7, 6), LocalDate.of(2021, 8, 4)));
        shipList.add(new Ship("CMA CGM CORTE REAL III", LocalDate.of(2021, 7, 12), LocalDate.of(2021, 8, 10)));
        shipList.add(new Ship("THALASSA TYHI III", LocalDate.of(2021, 7, 10), LocalDate.of(2021, 8, 8)));
        shipList.add(new Ship("EVER GIFTED III", LocalDate.of(2021, 7, 11), LocalDate.of(2021, 8, 9)));
        shipList.add(new Ship("CMA CGM BENJAMIN FRANKLIN III", LocalDate.of(2021, 7, 13), LocalDate.of(2021, 8, 11)));
        shipList.add(new Ship("THALASSA ELPIDA III", LocalDate.of(2021, 7, 17), LocalDate.of(2021, 8, 15)));
        shipList.add(new Ship("THALASSA DOXA III", LocalDate.of(2021, 7, 18), LocalDate.of(2021, 8, 16)));
        shipList.add(new Ship("APL TEMASEK III", LocalDate.of(2021, 7, 20), LocalDate.of(2021, 8, 18)));
        shipList.add(new Ship("CMA CGM AMERIGO VESPUCCI III", LocalDate.of(2021, 7, 19), LocalDate.of(2021, 8, 17)));
        shipList.add(new Ship("EVER GIVEN III", LocalDate.of(2021, 7, 25), LocalDate.of(2021, 8, 23)));
        shipList.add(new Ship("CMA CGM KERGUELEN III", LocalDate.of(2021, 7, 27), LocalDate.of(2021, 8, 25)));
        shipList.add(new Ship("CMA CGM MARCO POLO III", LocalDate.of(2021, 7, 26), LocalDate.of(2021, 8, 24)));
        shipList.add(new Ship("THALASSA NIKI III", LocalDate.of(2021, 8, 1), LocalDate.of(2021, 9, 30)));
        shipList.add(new Ship("CMA CGM JULES VERNE III", LocalDate.of(2021, 8, 3), LocalDate.of(2021, 9, 4)));
        shipList.add(new Ship("CMA CGM LAPEROUSE III", LocalDate.of(2021, 8, 2), LocalDate.of(2021, 9, 1)));
        shipList.add(new Ship("EVER GENIUS IIII", LocalDate.of(2021, 8, 8), LocalDate.of(2021, 9, 10)));
        shipList.add(new Ship("CMA CGM ZHENG HE IV", LocalDate.of(2021, 8, 10), LocalDate.of(2021, 9, 9)));
        shipList.add(new Ship("APL VANDA III", LocalDate.of(2021, 8, 9), LocalDate.of(2021, 9, 8)));
        shipList.add(new Ship("EVER GRADE IV", LocalDate.of(2021, 8, 15), LocalDate.of(2021, 9, 14)));
        shipList.add(new Ship("CMA CGM ANTOINE DE SAINT EXUPE III", LocalDate.of(2021, 8, 17), LocalDate.of(2021, 9, 16)));
        shipList.add(new Ship("CMA CGM MAGELLAN IV", LocalDate.of(2021, 8, 16), LocalDate.of(2021, 9, 15)));
        shipList.add(new Ship("TITAN IV", LocalDate.of(2021, 8, 22), LocalDate.of(2021, 9, 20)));
        shipList.add(new Ship("CMA CGM LOUIS BLERIOT IV", LocalDate.of(2021, 8, 24), LocalDate.of(2021, 9, 25)));
        shipList.add(new Ship("EVER GOODS IV", LocalDate.of(2021, 8, 29), LocalDate.of(2021, 9, 28)));
        shipList.add(new Ship("CMA CGM VASCO DE GAMA IV", LocalDate.of(2021, 9, 1), LocalDate.of(2021, 9, 30)));
        shipList.add(new Ship("APL FULLERTON IV", LocalDate.of(2021, 8, 30), LocalDate.of(2021, 9, 29)));
        shipList.add(new Ship("CMA CGM JEAN MERMOZ IV", LocalDate.of(2021, 9, 8), LocalDate.of(2021, 10, 6)));
        shipList.add(new Ship("CMA CGM GEORG FORSTER IV", LocalDate.of(2021, 9, 7), LocalDate.of(2021, 10, 5)));
        shipList.add(new Ship("EVER GOLDEN IV", LocalDate.of(2021, 9, 13), LocalDate.of(2021, 10, 11)));
        shipList.add(new Ship("TEXAS TRIUMPH IV", LocalDate.of(2021, 9, 20), LocalDate.of(2021, 10, 18)));
        shipList.add(new Ship("CMA CGM BOUGAINVILLE IV", LocalDate.of(2021, 9, 22), LocalDate.of(2021, 10, 20)));
        shipList.add(new Ship("CMA CGM ALEXANDER VON HUMBOLD IV", LocalDate.of(2021, 9, 29), LocalDate.of(2021, 10, 27)));
        shipList.add(new Ship("CMA CGM MARCO POLO V", LocalDate.of(2021, 9, 26), LocalDate.of(2021, 10, 24)));
        shipList.add(new Ship("THALASSA NIKI V", LocalDate.of(2021, 10, 1), LocalDate.of(2021, 10, 30)));
        shipList.add(new Ship("CMA CGM JULES VERNE V", LocalDate.of(2021, 10, 3), LocalDate.of(2021, 11, 4)));
        shipList.add(new Ship("CMA CGM LAPEROUSE V", LocalDate.of(2021, 10, 2), LocalDate.of(2021, 11, 1)));
        shipList.add(new Ship("EVER GENIUS VI", LocalDate.of(2021, 10, 8), LocalDate.of(2021, 11, 10)));
        shipList.add(new Ship("CMA CGM ZHENG HE VI", LocalDate.of(2021, 10, 10), LocalDate.of(2021, 11, 9)));
        shipList.add(new Ship("APL VANDA V", LocalDate.of(2021, 10, 9), LocalDate.of(2021, 11, 8)));
        shipList.add(new Ship("EVER GRADE VI", LocalDate.of(2021, 10, 15), LocalDate.of(2021, 11, 14)));
        shipList.add(new Ship("CMA CGM ANTOINE DE SAINT EXUPE V", LocalDate.of(2021, 10, 17), LocalDate.of(2021, 11, 16)));
        shipList.add(new Ship("CMA CGM MAGELLAN VI", LocalDate.of(2021, 10, 16), LocalDate.of(2021, 11, 15)));
        shipList.add(new Ship("TITAN VI", LocalDate.of(2021, 10, 22), LocalDate.of(2021, 11, 20)));
        shipList.add(new Ship("CMA CGM LOUIS BLERIOT VI", LocalDate.of(2021, 10, 24), LocalDate.of(2021, 11, 25)));
        shipList.add(new Ship("EVER GOODS VI", LocalDate.of(2021, 10, 29), LocalDate.of(2021, 11, 28)));
        shipList.add(new Ship("CMA CGM VASCO DE GAMA VI", LocalDate.of(2021, 11, 1), LocalDate.of(2021, 11, 30)));
        shipList.add(new Ship("APL FULLERTON VI", LocalDate.of(2021, 11, 30), LocalDate.of(2021, 11, 29)));
        shipList.add(new Ship("CMA CGM JEAN MERMOZ VI", LocalDate.of(2021, 11, 8), LocalDate.of(2021, 12, 6)));
        shipList.add(new Ship("CMA CGM GEORG FORSTER VI", LocalDate.of(2021, 11, 7), LocalDate.of(2021, 12, 5)));
        shipList.add(new Ship("EVER GOLDEN VI", LocalDate.of(2021, 11, 13), LocalDate.of(2021, 12, 11)));
        shipList.add(new Ship("TEXAS TRIUMPH VI", LocalDate.of(2021, 11, 20), LocalDate.of(2021, 12, 18)));
        shipList.add(new Ship("CMA CGM BOUGAINVILLE VI", LocalDate.of(2021, 11, 22), LocalDate.of(2021, 12, 20)));
        shipList.add(new Ship("CMA CGM ALEXANDER VON HUMBOLD VI", LocalDate.of(2021, 11, 29), LocalDate.of(2021, 12, 27)));
        shipList.add(new Ship("TITAN VII", LocalDate.of(2021, 12, 1), LocalDate.of(2021, 12, 31)));
        shipList.add(new Ship("CMA CGM LOUIS BLERIOT VII", LocalDate.of(2021, 12, 5), LocalDate.of(2022, 1, 4)));
        shipList.add(new Ship("EVER GOODS VII", LocalDate.of(2021, 12, 12), LocalDate.of(2022, 1, 13)));
        shipList.add(new Ship("CMA CGM VASCO DE GAMA VII", LocalDate.of(2021, 12, 20), LocalDate.of(2022, 1, 20)));
        shipList.add(new Ship("APL FULLERTON VII", LocalDate.of(2021, 12, 31), LocalDate.of(2022, 1, 31)));


        return shipList;


    }


    private static void insertHoliday(LocalDate localDate, Country country, HashMap<LocalDate, HashMap<Country, Boolean>> map) {
        HashMap<Country, Boolean> holidayMap = map.get(localDate);
        holidayMap.put(country, true);
        //map.put(localDate,holidayMap);
    }


    public static List<BusinessDay> getBusinessDays() {
        List<BusinessDay> bdList = new ArrayList<>();
        LocalDate ld = LocalDate.of(2020, 11, 1);
        for (int i = 0; i < 500; i++) {
            BusinessDay businessDay = new BusinessDay(ld.plusDays(i), new HashMap<Supplier, LogisticsObject>(), new ArrayList<LogisticsObject>(), new ArrayList<LogisticsObject>(), new HashMap<Country, Boolean>(), new HashMap<Bike, Integer>(), new HashMap<Bike, Integer>(), new HashMap<Bike, Integer>(), new HashMap<Component, Integer>());
            bdList.add(businessDay);
        }
        return bdList;

    }


}


