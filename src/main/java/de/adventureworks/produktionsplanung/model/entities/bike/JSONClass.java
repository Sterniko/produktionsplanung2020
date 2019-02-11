package de.adventureworks.produktionsplanung.model.entities.bike;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;

public class JSONClass {

    public static void main(String args[]) {

        ArrayList<Bike> bikeArrayList = JSONClass.getBike();
        ObjectMapper mapper = new ObjectMapper();


        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("result.json"), bikeArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static ArrayList<Bike> getBike() {

        List<Supplier > supplierList = new ArrayList<>();

        supplierList.add(new Supplier("WernerRahmenGMBH", 10, 7, Country.GERMANY, null ));
        supplierList.add(new Supplier("Tenedores de Zaragoza", 75, 14, Country.SPAIN, null));
        supplierList.add(new Supplier("DengwongSaddles", 500, 49, Country.CHINA, null));
        List<Component> frameList = new ArrayList<>();

        supplierList.get(0);
        frameList.add(new Frame("7005DB", supplierList.get(0)));
        frameList.add(new Frame("7005TB", supplierList.get(0)));
        frameList.add(new Frame ("Monocoque", supplierList.get(0)));

        List<Component> saddleList = new ArrayList<>();
        saddleList.add(new Saddle("Fizik Tundra", supplierList.get(2)));
        saddleList.add(new Saddle("Race Line", supplierList.get(2)));
        saddleList.add(new Saddle("Spark", supplierList.get(2)));
        saddleList.add(new Saddle("Speed Line",  supplierList.get(2)));

        List<Component> forkList = new ArrayList<>();
        forkList.add(new Fork("Fox32 F100", supplierList.get(1)));
        forkList.add(new Fork("Fox32 F80",  supplierList.get(1)));
        forkList.add(new Fork("Fox Talas140", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox Reba", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox Recon351", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox ReconSL", supplierList.get(1)));
        forkList.add(new Fork("SR Suntour Raidon", supplierList.get(1)));
        //Bikes
        ArrayList<Bike> bikeList = new ArrayList<>();

        bikeList.add(new Bike("MTBAllrounder",  frameList.get(0), forkList.get(0), saddleList.get(2)));
        bikeList.add(new Bike("MTBCompetition",  frameList.get(2), forkList.get(2), saddleList.get(3)));
        bikeList.add(new Bike("MTBDownhill", frameList.get(1), forkList.get(4), saddleList.get(0)));
        bikeList.add(new Bike("MTBExtreme", frameList.get(2), forkList.get(3), saddleList.get(2)));
        bikeList.add(new Bike("MTBFreeride", frameList.get(1), forkList.get(1), saddleList.get(0)));
        bikeList.add(new Bike("MTBMarathon", frameList.get(0), forkList.get(5), saddleList.get(1)));
        bikeList.add(new Bike("MTBPerformance", frameList.get(1), forkList.get(3), saddleList.get(0)));
        bikeList.add(new Bike("MTBTrail", frameList.get(2), forkList.get(6), saddleList.get(3)));

        return bikeList;
    }
}


