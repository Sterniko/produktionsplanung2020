package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.external.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProductionEngagementService {

    @Autowired
    DataBean dataBean;

    @Autowired
    ProductionService productionService;

    public ProductionEngagementService() {

    }

    public void calculateProductionSinceDay(BusinessDay businessDay) {
        List<BusinessDay> businessDays = getAllBusinessDaysFrom(businessDay);
        for (BusinessDay bd: businessDays) {
            productionService.setProductionForDay(bd.getDate());
        }
    }

    public List<BusinessDay> getAllBusinessDaysFrom(BusinessDay paramBd) {

        List<BusinessDay> businessDays = new ArrayList<>(dataBean.getBusinessDays().values());
        Collections.sort(businessDays);

        for (BusinessDay bd: businessDays) {
            if (paramBd.compareTo(bd) == 0) {
                break;
            }
            businessDays.remove(bd);
        }

        return businessDays;

    }

    //placeDate today finishProdutionDate = arrivalBeimKunden - lieferZeit
    public boolean placeCustomrOrder(LocalDate placeDate, LocalDate finishProductionDate, Map<Bike, Integer> bikes,boolean hasPrio){
        return true;
    }


    public void deleteShip(Ship ship, LocalDate deleteDay){

    }
    public void delayShip(Ship ship, LocalDate deleteDay, LocalDate newArrival){

    }
    public void setWarehouseEntries(LocalDate today, Map<Component, Integer> stockForDay){

    }
    public void changeProductionWeek(LocalDate today, BusinessWeek week, Map<Bike,Integer> newProduction){

    }



}
