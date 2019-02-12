package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.Data;
import de.adventureworks.produktionsplanung.model.entities.bike.*;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Customer;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

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
        List<Component> frameList = new ArrayList<>();

        supplierList.get(0);
        frameList.add(new Frame("7005DB", supplierList.get(0)));
        frameList.add(new Frame("7005TB", supplierList.get(0)));
        frameList.add(new Frame ("Monocoque", supplierList.get(0)));

        data.setComponents(frameList);
        List<Component> saddleList = new ArrayList<>();
        saddleList.add(new Saddle("Fizik Tundra", supplierList.get(2)));
        saddleList.add(new Saddle("Race Line", supplierList.get(2)));
        saddleList.add(new Saddle("Spark", supplierList.get(2)));
        saddleList.add(new Saddle("Speed Line",  supplierList.get(2)));
        data.setComponents(saddleList);

        List<Component> forkList = new ArrayList<>();
        forkList.add(new Fork("Fox32 F100", supplierList.get(1)));
        forkList.add(new Fork("Fox32 F80",  supplierList.get(1)));
        forkList.add(new Fork("Fox Talas140", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox Reba", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox Recon351", supplierList.get(1)));
        forkList.add(new Fork("Rock Schox ReconSL", supplierList.get(1)));
        forkList.add(new Fork("SR Suntour Raidon", supplierList.get(1)));
        data.setComponents(forkList);
        //Bikes
        List<Bike> bikeList = new ArrayList<>();

        bikeList.add(new Bike("MTBAllrounder",  frameList.get(0), forkList.get(0), saddleList.get(2)));
        bikeList.add(new Bike("MTBCompetition",  frameList.get(2), forkList.get(2), saddleList.get(3)));
        bikeList.add(new Bike("MTBDownhill", frameList.get(1), forkList.get(4), saddleList.get(0)));
        bikeList.add(new Bike("MTBExtreme", frameList.get(2), forkList.get(3), saddleList.get(2)));
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
        for(int i = 0 ; i < 5 ; i++){
            BusinessDay bd = new BusinessDay(LocalDate.now().plusDays(i), null, null,null,null,null,null,null,null);
            businessDayMap.put(bd.getDate(), bd);
            bd.setWarehouseStock(wareHouseStockMap);
        }
        data.setBusinessDays(businessDayMap);

        Map<LocalDate,BusinessDay> bdMap = data.getBusinessDays();

        // Robert testing stuff für warehouse nullpointer
        BusinessDay bd = bdMap.get(LocalDate.now());
        ArrayList<LogisticsObject> loList = new ArrayList<>();
        LogisticsObject logisticsObject = new LogisticsObject(supplierList.get(1));
        HashMap<Component, Integer> componentIntegerHashMap = new HashMap<>();
        logisticsObject.setComponents(componentIntegerHashMap);
        HashMap<Supplier, LogisticsObject> loMap = new HashMap<>();
        loMap.put(supplierList.get(1), logisticsObject);
        bd.setPendingSupplierAmount(loMap);
        bd.setSentDeliveries(loList);




       /* BusinessDay bday = bdMap.get(LocalDate.now());
        Map<Component,Integer> wareHouseStockMap2 = new HashMap<>();
        for(Component c : forkList){
            wareHouseStockMap2.put(c,250);
        }
        for(Component c : saddleList){
            wareHouseStockMap2.put(c,200);
        }
        for(Component c : frameList){
            wareHouseStockMap2.put(c,300);
        }
        bday.setWarehouseStock(wareHouseStockMap2);
        bdMap.put(LocalDate.now(), bday);
        data.setBusinessDays(bdMap);
        BusinessDay bda2y = bdMap.get(LocalDate.now());*/
        //addExampleWarehouse(data);
        addExampleShip(data);

    }


    public Data getData() {
        return data;
    }

    private static void addExampleWarehouse(Data data) {
        List<Component> components = new ArrayList<>();
        components.add(new Frame("Frame a", null));
        components.add(new Frame("Frame b", null));
        components.add(new Frame("Frame c", null));
        data.setComponents(components);
        data.setBusinessDays(new HashMap<>());



        LocalDate date = LocalDate.of(2019, 2, 5);
        List<BusinessWeek> weeks = new LinkedList<>();
        for (int i = 0; i < 5; i++) {

            BusinessWeek week= new BusinessWeek();
            List<BusinessDay> singelWeek= new LinkedList<>();
            for(int j = 0; j <7;j++){
                BusinessDay businessDay = new BusinessDay();
                businessDay.setDate(date);

                Map<Component, Integer> wareHouseStock = new HashMap<>();

                wareHouseStock.put(components.get(0),(int)(Math.random()*10)+1);
                wareHouseStock.put(components.get(1),(int)(Math.random()*10)+1);
                wareHouseStock.put(components.get(2),(int)(Math.random()*10)+1);

                businessDay.setWarehouseStock(wareHouseStock);

                singelWeek.add(businessDay);

                data.getBusinessDays().put(date, businessDay);

                date= date.plusDays(1);
            }
            week.setDays(singelWeek);
            weeks.add(week);
        }
        data.setBusinessWeeks(weeks);

    }


    private static void addExampleShip(Data data){


        LocalDate dep1 = LocalDate.of(2019,7,23);
        LocalDate dep2 = LocalDate.of(2017,4,3);
        LocalDate dep3 = LocalDate.of(2018,10,19);
        LocalDate arr1= LocalDate.now().plusDays(233);
        LocalDate arr2= LocalDate.now().plusDays(23);
        LocalDate arr3= LocalDate.now().plusDays(33);



        Ship a = new Ship("ASIA",dep1, arr1);
        Ship b = new Ship("Q-MARRY",dep2,arr2);
        Ship c = new Ship("Schiff",dep3,arr3);

        List<Ship> ships = new ArrayList<>();
        ships.add(a);
        ships.add(b);
        ships.add(c);
        data.setShips(ships);
    }
}
