package de.adventureworks.produktionsplanung.model.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

@Service
public class DataInitService {

    private Data data;

    @PostConstruct
    public void init() {
        data = new Data();
        ObjectMapper mapper = new ObjectMapper();
        try {

            List<Supplier> supplierList = mapper.readValue(new File("supplier.json"), new TypeReference<List<Supplier>>() {
            });
            data.setSuppliers(supplierList);

            List<Frame> frameList = mapper.readValue(new File("frame.json"), new TypeReference<List<Frame>>() {
            });
            data.setComponents(frameList);

            List<Saddle> saddleList = mapper.readValue(new File("saddle.json"), new TypeReference<List<Saddle>>() {
            });
            data.setComponents(saddleList);

            ArrayList<Fork> forkList = mapper.readValue(new File("fork.json"), new TypeReference<List<Fork>>() {
            });
            data.setComponents(forkList);

            ArrayList<Bike> bikeList = mapper.readValue(new File("bikes.json"), new TypeReference<List<Bike>>() {
            });
            data.setBikes(bikeList);

            List<BusinessDay> businessDayList = JSONClass.getBusinessDays();
            Map<LocalDate, BusinessDay> businessDays = new HashMap<>();
            for (BusinessDay bd : businessDayList) {
                businessDays.put(bd.getDate(), bd);
            }

            // TestZwecke
            Map<Component, Integer> wareHouseStockMap = new HashMap<>();

            for (Component c : forkList) {
                wareHouseStockMap.put(c, 100);
            }
            for (Component c : saddleList) {
                wareHouseStockMap.put(c, 100);
            }
            for (Component c : frameList) {
                wareHouseStockMap.put(c, 100);
            }
            for (int i = 0; i < 5; i++) {
                BusinessDay bd = new BusinessDay(LocalDate.now().plusDays(i), null, null, null, null, null, null, null, null);
                businessDays.put(bd.getDate(), bd);
                bd.setWarehouseStock(wareHouseStockMap);
            }





            data.setBusinessDays(businessDays);


            /*ArrayList<BusinessDay> bdList = mapper.readValue(new File("businessDays.json"), new TypeReference<List<BusinessDay>>() {
            });
            Map<LocalDate, BusinessDay> businessDays = new HashMap<>();
            for (BusinessDay bd : bdList) {
                businessDays.put(bd.getDate(), bd);
            }
            */



            Customer customer1 = new Customer("Metro AG", Country.GERMANY);
            List<Customer> customers = new ArrayList<Customer>();
            customers.add(customer1);

            data.setCustomers(customers);
            Map<LocalDate, BusinessDay> businessDayMap = new HashMap<>();
            
            Map<LocalDate, BusinessDay> bdMap = data.getBusinessDays();


            BusinessDay bday = bdMap.get(LocalDate.now());
            Map<Component, Integer> wareHouseStockMap2 = new HashMap<>();
            for (Component c : forkList) {
                wareHouseStockMap2.put(c, 250);
            }
            for (Component c : saddleList) {
                wareHouseStockMap2.put(c, 200);
            }
            for (Component c : frameList) {
                wareHouseStockMap2.put(c, 300);
            }
            bday.setWarehouseStock(wareHouseStockMap2);
            bdMap.put(LocalDate.now(), bday);
            data.setBusinessDays(bdMap);
            BusinessDay bda2y = bdMap.get(LocalDate.now());
            //addExampleWarehouse(data);
            addExampleShip(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            BusinessWeek week = new BusinessWeek();
            List<BusinessDay> singelWeek = new LinkedList<>();
            for (int j = 0; j < 7; j++) {
                BusinessDay businessDay = new BusinessDay();
                businessDay.setDate(date);

                Map<Component, Integer> wareHouseStock = new HashMap<>();

                wareHouseStock.put(components.get(0), (int) (Math.random() * 10) + 1);
                wareHouseStock.put(components.get(1), (int) (Math.random() * 10) + 1);
                wareHouseStock.put(components.get(2), (int) (Math.random() * 10) + 1);

                businessDay.setWarehouseStock(wareHouseStock);

                singelWeek.add(businessDay);

                data.getBusinessDays().put(date, businessDay);

                date = date.plusDays(1);
            }
            week.setDays(singelWeek);
            weeks.add(week);
        }
        data.setBusinessWeeks(weeks);

    }


    private static void addExampleShip(Data data) {


        LocalDate dep1 = LocalDate.of(2019, 7, 23);
        LocalDate dep2 = LocalDate.of(2017, 4, 3);
        LocalDate dep3 = LocalDate.of(2018, 10, 19);
        LocalDate arr1 = LocalDate.now().plusDays(233);
        LocalDate arr2 = LocalDate.now().plusDays(23);
        LocalDate arr3 = LocalDate.now().plusDays(33);


        Ship a = new Ship("ASIA", dep1, arr1);
        Ship b = new Ship("Q-MARRY", dep2, arr2);
        Ship c = new Ship("Schiff", dep3, arr3);

        List<Ship> ships = new ArrayList<>();
        ships.add(a);
        ships.add(b);
        ships.add(c);
        data.setShips(ships);
    }
}
