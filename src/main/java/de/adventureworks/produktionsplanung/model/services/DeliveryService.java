package de.adventureworks.produktionsplanung.model.services;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryService {

    @Autowired
    DataBean dataBean;

    public List<BusinessDay> getBusinessDayToDeliveryID(int deliveryID){

        List<BusinessDay> businessDays = new ArrayList<>();

        for(Map.Entry bd : dataBean.getBusinessDays().entrySet()){
            BusinessDay dayToCheck = (BusinessDay) bd.getValue();

            for(LogisticsObject received : dayToCheck.getReceivedDeliveries()){
                if(received.getId() == deliveryID){
                    businessDays.add((BusinessDay)bd.getValue());
                }
            }
            for(LogisticsObject sent : dayToCheck.getSentDeliveries()){
                if(sent.getId() == deliveryID){
                    businessDays.add((BusinessDay)bd.getValue());
                }
            }
        }
        return businessDays;
    }

    public void setNewDelivery(List<LogisticsObject> logisticsObjectList, int deliveryID, Map<Component,Integer> compMap){
        int sumAmount = 0;
        for(LogisticsObject delivery :logisticsObjectList){
            sumAmount = 0;
            if(delivery.getId() == deliveryID){
                delivery.setComponents(compMap);
            }
            for(Map.Entry entry : delivery.getComponents().entrySet()){
                sumAmount += (Integer) entry.getValue();
            }
            delivery.setSumAmount(sumAmount);
        }

    }

}
