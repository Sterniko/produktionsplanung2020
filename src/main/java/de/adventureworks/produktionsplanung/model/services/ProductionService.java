package de.adventureworks.produktionsplanung.model.services;


import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.controller.production.model.ProductionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductionService {

    //Class var
    private double[] monthPercentArr;
    private double[] productionArr;
    private double[] bikesPercentArr;
    private double[] memoryBikes;
    private int bikesPA;

    @Autowired
    private ProductionModel productionModel;
    private BusinessCalendar businessCalendar;

    //Getter & Setter
    public double[] getProductionArr() {
        return productionArr;
    }

    public int getBikesPA() {
        return bikesPA;
    }


    /**
     * Constructor ProductionService could get different bikesPA or other %distribution of bikes or month
     */
    public ProductionService(){
        this.monthPercentArr = new double[12];
        this.productionArr = new double[12*8];
        this.bikesPercentArr = new double[8];
        this.memoryBikes = new double[8];
        this.bikesPA = 185000;

        this.businessCalendar = new BusinessCalendar();

        getPlannedProductionData();
    }

    /**
     * calculates the amount of bikes to produce per day and sets Planned Production for BusinessDay
     * Sets PP for BD
     * @param date to get Production for
     */
    public void setProductionForDay(LocalDate date){

        //TODO: CHECK FOR BESTELLUNGEN !!!

        Map<LocalDate, BusinessDay> businessDays = this.productionModel.getBusinessDays();
        BusinessDay bd = businessDays.get(date);

        //TODO:Map von BD benutzen
        if(this.businessCalendar.isWorkingDay(date)) {

            int month = date.getMonthValue();
            int workingDays = this.businessCalendar.getWorkingDaysOutOfMonthAndYear(month, date.getYear());
            int counter = 0;


            //Starte im Januar bei 0, Feb 8, März 16, etc jeweils 8 bikes pro Monat im ProductionArr
            if (month > 1) {
                counter = ((month - 1) * 8);
            }

            //Allrounder
            double prodAllrounder = this.productionArr[counter] / workingDays;
            this.memoryBikes[0] += prodAllrounder % 1;
            //Competition
            double prodCompetition = this.productionArr[counter + 1] / workingDays;
            this.memoryBikes[1] += prodCompetition % 1;
            //Downhill
            double prodDownhill = this.productionArr[counter + 2] / workingDays;
            this.memoryBikes[2] += prodDownhill % 1;
            //Extremen
            double prodExtreme = this.productionArr[counter + 3] / workingDays;
            this.memoryBikes[3] += prodExtreme % 1;
            //Freeride
            double prodFreeride = this.productionArr[counter + 4] / workingDays;
            this.memoryBikes[4] += prodFreeride % 1;
            //Marathon
            double prodMarathon = this.productionArr[counter + 5] / workingDays;
            this.memoryBikes[5] += prodMarathon % 1;
            //Performance
            double prodPerformance = this.productionArr[counter + 6] / workingDays;
            this.memoryBikes[6] += prodPerformance % 1;
            //Trail
            double prodTrail = this.productionArr[counter + 7] / workingDays;
            this.memoryBikes[7] += prodTrail % 1;


            int amountAllrounder = (int) this.productionArr[counter] / workingDays;
            int amountCompetition = (int) this.productionArr[counter + 1] / workingDays;
            int amountDownhill = (int) this.productionArr[counter + 2] / workingDays;
            int amountExtreme = (int) this.productionArr[counter + 3] / workingDays;
            int amountFreeride = (int) this.productionArr[counter + 4] / workingDays;
            int amountMarathon = (int) this.productionArr[counter + 5] / workingDays;
            int amountPerformance = (int) this.productionArr[counter + 6] / workingDays;
            int amountTrail = (int) this.productionArr[counter + 7] / workingDays;


            //IF memoryBikes >= 1  +1 zu amount des bikes
            for (int i = 0; i < this.memoryBikes.length; i++) {
                if (this.memoryBikes[i] >= 1) {
                    switch (i) {
                        case 0:
                            amountAllrounder += 1;
                            break;
                        case 1:
                            amountCompetition += 1;
                            break;
                        case 2:
                            amountDownhill += 1;
                            break;
                        case 3:
                            amountExtreme += 1;
                            break;
                        case 4:
                            amountFreeride += 1;
                            break;
                        case 5:
                            amountMarathon += 1;
                            break;
                        case 6:
                            amountPerformance += 1;
                            break;
                        case 7:
                            amountTrail += 1;
                            break;

                    }
                    this.memoryBikes[i] -= 1;
                }
            }


            Map<Bike, Integer> productionMap = new HashMap<>();
            for (Bike bike : this.productionModel.getBikes()) {
                switch (bike.getName()) {
                    case ("MTBAllrounder"):
                        productionMap.put(bike, amountAllrounder);
                        break;
                    case ("MTBCompetition"):
                        productionMap.put(bike, amountCompetition);
                        break;
                    case ("MTBDownhill"):
                        productionMap.put(bike, amountDownhill);
                        break;
                    case ("MTBExtreme"):
                        productionMap.put(bike, amountExtreme);
                        break;
                    case ("MTBFreeride"):
                        productionMap.put(bike, amountFreeride);
                        break;
                    case ("MTBMarathon"):
                        productionMap.put(bike, amountMarathon);
                        break;
                    case ("MTBPerformance"):
                        productionMap.put(bike, amountPerformance);
                        break;
                    case ("MTBTrail"):
                        productionMap.put(bike, amountTrail);
                        break;
                }
            }
            bd.setPlannedProduction(productionMap);
        }
        //Falls kein Arbeitstag ist Produktion je bike = 0
        else{
            Map<Bike, Integer> productionMap = new HashMap<>();
            for (Bike bike : this.productionModel.getBikes()) {
                productionMap.put(bike, 0);
            }
            bd.setPlannedProduction(productionMap);
        }
    }

    /**
     * Checks if enough Components are in the Warehouse to produce for Planned Production
     * Sets ActualProduction
     * Deletes Components for Production out of Warehouse
     * @param bd to check
     * @return
     */
    public void checkComponentsForDay(BusinessDay bd){
        int amountPlannedProduction;
        int amountWareHouse = 0;
        int i ;
        int minComp ;
        Bike bike;
        boolean enoughComp;

        Map<Bike,Integer> plannedProduction = bd.getPlannedProduction();
        Map<Bike,Integer> actualProduction = new HashMap<>();
        Map<Bike,Integer> additionalProduction = new HashMap<>();
        Map<Component,Integer> newWareHouseStock = new HashMap<>();

        //Für alle Bikes die PlannedProduction des Tages angucken
        for(Map.Entry entry : plannedProduction.entrySet()){

            ArrayList<Component> componentList = new ArrayList<>();
            Map<Component,Integer> amountComponent = new HashMap<>();

            bike = (Bike) entry.getKey();
            amountPlannedProduction =  (Integer)entry.getValue();

            //Componenten des Bikes
            componentList.add(bike.getFork());
            componentList.add(bike.getFrame());
            componentList.add(bike.getSaddle());

            //Hilfsvar zum zählen, ob alle Componenten ausreichend vorhanden sind!
            i = 0;
            //Hilfsvar um das Minimum zu bestimmen falls mindestens 1 comp nicht ausreichend vorhanden ist
            minComp = Integer.MAX_VALUE;
            enoughComp = true;

            //Alle componenten des Warehouse angucken
            for(Map.Entry comp : bd.getWarehouseStock().entrySet()){

                amountWareHouse = (Integer) comp.getValue();
                newWareHouseStock.put((Component) comp.getKey(),amountWareHouse);

                if(componentList.contains(comp.getKey())){

                    //Speichere WH stand der Componente falls unterschiedliche Lagerstände vorhanden sind
                    amountComponent.put((Component) comp.getKey(),amountWareHouse);
                    i++;
                    //nicht ausreichend im Lager -> speichere Minimum Lagerstand der Componenten des Bikes
                    if(amountPlannedProduction > amountWareHouse){
                        if(minComp > amountWareHouse){
                            enoughComp = false;
                            minComp = amountWareHouse;
                        }
                    }
                    if(i == 3){
                        //alle 3 Componenten sind ausreichend im Lager
                        if(enoughComp) {
                            actualProduction.put(bike, amountPlannedProduction);
                            additionalProduction.put(bike, 0);
                            bd.setActualProduction(actualProduction);
                            bd.setAdditionalProduction(additionalProduction);

                            for(Map.Entry c : amountComponent.entrySet()){
                                amountWareHouse = (Integer)c.getValue() - amountPlannedProduction;
                                newWareHouseStock.put((Component) c.getKey(), amountWareHouse);
                            }
                        }
                        //min 1 Componente nicht ausreichend im Lager
                        else {

                            actualProduction.put(bike, minComp);
                            additionalProduction.put(bike, minComp - amountPlannedProduction);

                            bd.setAdditionalProduction(additionalProduction);
                            bd.setActualProduction(actualProduction);

                            //falls unterschiedliche Lagerstände vorhanden waren ..
                            for(Map.Entry c : amountComponent.entrySet()){
                                amountWareHouse = (Integer)c.getValue() - minComp;
                                newWareHouseStock.put((Component) c.getKey(), amountWareHouse);
                            }
                        }

                    }
                }
            }
            bd.setWarehouseStock(newWareHouseStock);
        }
    }

    /**
     * calculates the Production and saves it in productionArr
     * first 8 Jan Bikes -> danach  8-16 Feb Bikes etc..
     */
    public void calculateRegularProduction(){
        int k = 0;
        for(Double monthDistribution : this.monthPercentArr ){
            for(Double bikesDistribution : this.bikesPercentArr){
                this.productionArr[k] = monthDistribution * bikesDistribution;
                k++;
            }
        }
    }

    /**
     * sets default values of distribution
     */
    private void getPlannedProductionData(){
        //Allrounder
        this.bikesPercentArr[0] = 0.3;
        //Competition
        this.bikesPercentArr[1] = 0.15;
        //Downhill
        this.bikesPercentArr[2] = 0.1;
        //Extreme
        this.bikesPercentArr[3] = 0.07;
        //Freeride
        this.bikesPercentArr[4] = 0.05;
        //Marathon
        this.bikesPercentArr[5] = 0.08;
        //Performance
        this.bikesPercentArr[6] = 0.12;
        //Trail
        this.bikesPercentArr[7] = 0.13;

        //Jan - Dez
        this.monthPercentArr[0] = bikesPA * 0.04;
        this.monthPercentArr[1] = bikesPA * 0.06;
        this.monthPercentArr[2] = bikesPA * 0.1;
        this.monthPercentArr[3] = bikesPA * 0.16;
        this.monthPercentArr[4] = bikesPA * 0.14;
        this.monthPercentArr[5] = bikesPA * 0.13;
        this.monthPercentArr[6] = bikesPA * 0.12;
        this.monthPercentArr[7] = bikesPA * 0.09;
        this.monthPercentArr[8] = bikesPA * 0.06;
        this.monthPercentArr[9] = bikesPA * 0.03;
        this.monthPercentArr[10] = bikesPA * 0.04;
        this.monthPercentArr[11] = bikesPA * 0.03;
    }
}
