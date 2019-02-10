package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.Data;
import de.adventureworks.produktionsplanung.model.entities.bike.*;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Customer;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataInitService {

    private Data data;

    @PostConstruct
    public void init() {
        data = new Data();

        List<Supplier> supplierList = new ArrayList<>();

        supplierList.add(new Supplier("WernerRahmenGMBH", 10, 7, Country.GERMANY, null ));
        supplierList.add(new Supplier("Tenedores de Zaragoza", 75, 14, Country.SPAIN, null));
        supplierList.add(new Supplier("DengwongSaddles", 500, 49, Country.CHINA, null));
        data.setSuppliers(supplierList);
        List<Frame> frameList = new ArrayList<>();

        supplierList.get(0);
        frameList.add(new Frame("7005DB", supplierList.get(0)));
        frameList.add(new Frame("7005TB", supplierList.get(0)));
        frameList.add(new Frame ("Monocoque", supplierList.get(0)));
        data.setFrames(frameList);

        List<Saddle> saddleList = new ArrayList<>();
        saddleList.add(new Saddle("Fizik Tundra", supplierList.get(2)));
        saddleList.add(new Saddle("Race Line", supplierList.get(2)));
        saddleList.add(new Saddle("Spark", supplierList.get(2)));
        saddleList.add(new Saddle("Speed Line",  supplierList.get(2)));
        data.setSaddles(saddleList);

        List<Fork> forkList = new ArrayList<>();
        forkList.add(new Fork("Fox32 F100", supplierList.get(1)));
        forkList.add(new Fork("Fox32 F80",  supplierList.get(1)));
        forkList.add(new Fork("Fox Talas140", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox Reba", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox Recon351", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox ReconSl", supplierList.get(1)));
        forkList.add(new Fork("SR Suntour Raidon", supplierList.get(1)));
        data.setForks(forkList);

        //Bikes
        List<Bike> bikeList = new ArrayList<>();

        bikeList.add(new Bike("MTBAllrounder",  frameList.get(0), forkList.get(0), saddleList.get(2)));
        bikeList.add(new Bike("MTBCompetition",  frameList.get(2), forkList.get(2), saddleList.get(3)));
        bikeList.add(new Bike("MTBDownhill", frameList.get(0), forkList.get(4), saddleList.get(0)));
        bikeList.add(new Bike("MTBExtreme", frameList.get(2), forkList.get(0), saddleList.get(2)));
        bikeList.add(new Bike("MTBFreeride", frameList.get(1), forkList.get(1), saddleList.get(0)));
        bikeList.add(new Bike("MTBMarathon", frameList.get(0), forkList.get(5), saddleList.get(1)));
        bikeList.add(new Bike("MTBPerformance", frameList.get(1), forkList.get(3), saddleList.get(0)));
        bikeList.add(new Bike("MTBTrail", frameList.get(2), forkList.get(6), saddleList.get(3)));
        data.setBikes(bikeList);


        Map<Component,Integer> wareHouseStockMap = new HashMap<>();

        for(Component c : forkList){
            wareHouseStockMap.put(c,100);
        }
        for(Component c : saddleList){
            wareHouseStockMap.put(c,100);
        }
        for(Component c : frameList){
            wareHouseStockMap.put(c,100);
        }

        Customer customer1 = new Customer("Metro AG", Country.GERMANY);
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(customer1);

        data.setCustomers(customers);
        Map<LocalDate,BusinessDay> businessDayMap = new HashMap<>();
        for(int i = 0 ; i < 100 ; i++){
            BusinessDay bd = new BusinessDay(LocalDate.now().plusDays(i), null, null,null,null,null,null,null,null);
            businessDayMap.put(bd.getDate(), bd);
            bd.setWarehouseStock(wareHouseStockMap);
        }
        data.setBusinessDays(businessDayMap);
    }

    public Data getData() {
        return data;
    }
}
