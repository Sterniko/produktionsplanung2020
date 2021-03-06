package de.adventureworks.produktionsplanung.model.services.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adventureworks.produktionsplanung.model.Data;
import de.adventureworks.produktionsplanung.model.entities.bike.*;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.events.IEvent;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Customer;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import de.adventureworks.produktionsplanung.model.services.BusinessCalendar;
import de.adventureworks.produktionsplanung.model.services.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class DataInitService {

    private Data data;

    @Autowired
    BusinessCalendar businessCalendar;


    @PostConstruct
    public void init() {
        data = new Data();
        ObjectMapper mapper = new ObjectMapper();
        try {

            List<Supplier> supplierList = mapper.readValue(new File("JSONs/supplier.json"), new TypeReference<List<Supplier>>() {
            });
            data.setSuppliers(supplierList);

            List<Frame> frameList = mapper.readValue(new File("JSONs/frame.json"), new TypeReference<List<Frame>>() {
            });
            data.setComponents(frameList);

            List<Saddle> saddleList = mapper.readValue(new File("JSONs/saddle.json"), new TypeReference<List<Saddle>>() {
            });
            data.updateComponent(saddleList);

            ArrayList<Fork> forkList = (ArrayList<Fork>) mapper.readValue(new File("JSONs/fork.json"), new TypeReference<List<Fork>>() {
            });
            data.updateComponent(forkList);

            ArrayList<Bike> bikeList = (ArrayList<Bike>) mapper.readValue(new File("JSONs/bikes.json"), new TypeReference<List<Bike>>() {
            });
            data.setBikes(bikeList);

            mapper.registerModule(new JavaTimeModule());

            ArrayList<Ship> shipList = (ArrayList<Ship>) mapper.readValue(new File("JSONs/ships.json"), new TypeReference<List<Ship>>() {
            });
            data.setShips(shipList);

            ArrayList<Integer> shiftList = new ArrayList<>();

            shiftList.add(7);
            shiftList.add(14);
            shiftList.add(21);

            data.setShifts(shiftList);

            Map<Bike, Double> bikeProductionShares = new HashMap<>();

            for (Bike bike : bikeList) {
                switch (bike.getName()) {
                    case ("MTBAllrounder"):
                        bikeProductionShares.put(bike, 0.3);
                        break;
                    case ("MTBCompetition"):
                        bikeProductionShares.put(bike, 0.15);
                        break;
                    case ("MTBDownhill"):
                        bikeProductionShares.put(bike, 0.1);
                        break;
                    case ("MTBExtreme"):
                        bikeProductionShares.put(bike, 0.07);
                        break;
                    case ("MTBFreeride"):
                        bikeProductionShares.put(bike, 0.05);
                        break;
                    case ("MTBMarathon"):
                        bikeProductionShares.put(bike, 0.08);
                        break;
                    case ("MTBPerformance"):
                        bikeProductionShares.put(bike, 0.12);
                        break;
                    case ("MTBTrail"):

                        bikeProductionShares.put(bike, 0.13);
                        break;
                }
            }

            data.setBikeProductionShares(bikeProductionShares);

            Map<Integer, Double> monthlyProductionShares = new HashMap<>();

            double[] monthPercentArr = new double[12];

            monthPercentArr[0] = 0.04;
            monthPercentArr[1] = 0.06;
            monthPercentArr[2] = 0.1;
            monthPercentArr[3] = 0.16;
            monthPercentArr[4] = 0.14;
            monthPercentArr[5] = 0.13;
            monthPercentArr[6] = 0.12;
            monthPercentArr[7] = 0.09;
            monthPercentArr[8] = 0.06;
            monthPercentArr[9] = 0.03;
            monthPercentArr[10] = 0.04;
            monthPercentArr[11] = 0.03;

            for (int i = 1; i < 13; i++) {
                monthlyProductionShares.put(i, monthPercentArr[i - 1]);
            }

            data.setMonthlyProductionShares(monthlyProductionShares);

            data.setHourlyCapacity(65);

            data.setYearlyProduction(185000);
            List<BusinessDay> businessDayList = JSONService.getBusinessDays();

            Map<Supplier, List<Component>> supplierListMap = new HashMap<>();

            for(Component component : data.getComponents()) {
                if(!supplierListMap.containsKey(component.getSupplier())) {
                    supplierListMap.put(component.getSupplier(), new ArrayList<Component>());
                }
                    supplierListMap.get(component.getSupplier()).add(component);


            }
            for (Supplier s : supplierList) {
                s.setComponents(supplierListMap.get(s));
            }
            
            int logisticObjectCounter = 0;

            Map<LocalDate, BusinessDay> businessDays = new HashMap<>();
            HashMap<LocalDate, HashMap<Country, Boolean>> workingDaysMap = JSONService.getHoliday();
            for (BusinessDay bd : businessDayList) {
                LocalDate ld = bd.getDate();

                // Pending supplier amount
                Map<Supplier, LogisticsObject> pendingSupplierAmount = new HashMap<>();
                for (Component c : data.getComponents()) {
                    Supplier s = c.getSupplier();
                    if (pendingSupplierAmount.get(s) == null) {
                        LogisticsObject lo = new LogisticsObject(s, 0, null);
                        logisticObjectCounter++;
                        Map<Component, Integer> componentMap = new HashMap<>();
                        componentMap.put(c, 0);
                        lo.setComponents(componentMap);
                        pendingSupplierAmount.put(s, lo);
                    }

                }

                // working days
                HashMap<Country, Boolean> workingDays = workingDaysMap.get(ld);

                // production maps
                Map<Bike, Integer> plannedProduction = new HashMap<>();
                Map<Bike, Integer> additonalProduction = new HashMap<>();
                Map<Bike, Integer> actualProduction = new HashMap<>();
                for (Bike bike : data.getBikes()) {
                    plannedProduction.put(bike, 0);
                    additonalProduction.put(bike, 0);
                    actualProduction.put(bike, 0);
                }

                //warehousestock
                Map<Component, Integer> warehouseStock = new HashMap<>();
                for (Component c : data.getComponents()) {
                    warehouseStock.put(c, 0);
                }

                List<IEvent> eventList = new ArrayList<>();

                //bd maps setzen
                bd.setWorkingDays(workingDays);
                bd.setPendingSupplierAmount(pendingSupplierAmount);
                bd.setPlannedProduction(plannedProduction);
                bd.setAdditionalProduction(additonalProduction);
                bd.setActualProduction(actualProduction);
                bd.setWarehouseStock(warehouseStock);
                bd.setEventList(eventList);


                //bd in bd map wichsen
                businessDays.put(bd.getDate(), bd);
            }

            data.setBusinessDays(businessDays);

            List<BusinessWeek> businessWeekList = new ArrayList<>();
            //Set BusinessWeeks
/*
            for (int i = 1; i < 53; i++) {
                List<BusinessDay> businessDayToWeekList = new ArrayList<>();

                BusinessWeek bw = new BusinessWeek();
                for (BusinessDay bd : businessDayList) {

                    int week = this.businessCalendar.getCalendarWeekFromDate(bd.getDate());
                    //TODO : JAHRE -> 2021 KW 1 .. 2022 KW 1 ... in BW speichern ?!
                    if (week == i) {
                        if (bd.getDate().getYear() == 2021 && bd.getDate().getMonthValue() == 1) {
                            bw.setYear(2021);
                            businessDayToWeekList.add(bd);

                        } else if (bd.getDate().getYear() == 2021 && week != 1) {
                            bw.setYear(2021);
                            businessDayToWeekList.add(bd);
                        }


                    }
                    bw.setCalendarWeek(i);
                }
                bw.setDays(businessDayToWeekList);
                businessWeekList.add(bw);

            }
*/

            LocalDate firstDayOfYear = LocalDate.of(2021, 1, 1);
            LocalDate nextYearfirstDay = LocalDate.of(2022, 1, 1);

            int weekCounter = 1;
            BusinessWeek businessWeek = new BusinessWeek(weekCounter);
            Map<LocalDate,BusinessDay> businessDayMap = data.getBusinessDays();


            for (LocalDate date = firstDayOfYear; date.isBefore(nextYearfirstDay); date = date.plusDays(1)) {
                BusinessDay currentBusinessDay = businessDayMap.get(date);

                businessWeek.getDays().add(currentBusinessDay);
                currentBusinessDay.setBusinessWeek(businessWeek);

                if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {//save and init new BusinessWeek
                    businessWeekList.add(businessWeek);
                    weekCounter++;
                    businessWeek = new BusinessWeek(weekCounter);
                }

            }
            if (!businessWeek.getDays().isEmpty()) {
                businessWeekList.add(businessWeek);
            }


            data.setBusinessWeeks(businessWeekList);

            /*
            HashMap<Component,Integer> delivery = new HashMap<>();
            delivery.put(data.getComponents().get(1),50);
            delivery.put(data.getComponents().get(2),50);
            LogisticsObject lo = new LogisticsObject(supplierList.get(1),100,delivery,1);
            LogisticsObject lo2 = new LogisticsObject(supplierList.get(1),100,delivery,2);
            List<LogisticsObject> send = new ArrayList<>();
            List<LogisticsObject> send2 = new ArrayList<>();
            lo.setArrivalDate(LocalDate.now());
            lo2.setArrivalDate(LocalDate.now());
            lo.setDepartureDate(LocalDate.now().plusDays(9));
            lo2.setDepartureDate(LocalDate.now().plusDays(9));

            send.add(lo);

            businessDays.get(LocalDate.now()).setSentDeliveries(send);
            businessDays.get(LocalDate.now().plusDays(9)).setReceivedDeliveries(send);
            send2.add(lo2);

            businessDays.get(LocalDate.now().plusDays(1)).setSentDeliveries(send2);
            List<LogisticsObject> send3 = new ArrayList<>();
            send3.add(lo);
            send3.add(lo2);
            businessDays.get(LocalDate.now().plusDays(9)).setReceivedDeliveries(send3);
            //   businessDayList.get(1).setReceivedDeliveries();
            //    orderService = new OrderService();
            //   orderService.placeOrder(businessDayList.get(100));
            */

             /*ArrayList<BusinessDay> bdList = mapper.readValue(new File("businessDays.json"), new TypeReference<List<BusinessDay>>() {
             });
             Map<LocalDate, BusinessDay> businessDays = new HashMap<>();
             for (BusinessDay bd : bdList) {
                 businessDays.put(bd.getDate(), bd);
             }
             */

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

            //addExampleWarehouse(data);


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


        LocalDate date = LocalDate.of(2021, 2, 5);
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


        LocalDate dep1 = LocalDate.of(2021, 7, 23);
        LocalDate dep2 = LocalDate.of(2017, 4, 3);
        LocalDate dep3 = LocalDate.of(2020, 10, 19);
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