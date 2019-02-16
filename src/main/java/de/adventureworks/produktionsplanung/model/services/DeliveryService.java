package de.adventureworks.produktionsplanung.model.services;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.events.DeliveryChangeEvent;
import de.adventureworks.produktionsplanung.model.entities.events.IEvent;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryService {

    @Autowired
    DataBean dataBean;

    public void startEvent(String id, BusinessDay businessDay, Map<Component, Integer> compMap) {
        DeliveryChangeEvent deliveryChangeEvent = new DeliveryChangeEvent(id, compMap);
        List<IEvent> eventList = businessDay.getEventList();
        eventList.add(deliveryChangeEvent);
    }

    public LogisticsObject getDeliveryToDeliveryID(String deliveryID) {
        if(deliveryID.equals("none")){return null;}
        BusinessDay arrivalDay = getArrivalDateToDeliveryID(deliveryID);

        List<LogisticsObject> receivedDeliveries = arrivalDay.getReceivedDeliveries();

        LogisticsObject returningLogisticObject = null;

        for (LogisticsObject lo : receivedDeliveries) {
            if (lo.getId().equals(deliveryID)) {
                returningLogisticObject = lo;
            }
        }
        return returningLogisticObject;

    }

    public BusinessDay getArrivalDateToDeliveryID(String deliveryID) {

        String arrivalDateString = deliveryID.substring(0, 10);
        LocalDate arrivalDate = LocalDate.parse(arrivalDateString);
        BusinessDay arrivalDay = dataBean.getBusinessDay(arrivalDate);

        return arrivalDay;
    }


    public LogisticsObject getLoByID(String deliveryID) {
        for(Map.Entry bd : dataBean.getBusinessDays().entrySet()){
            BusinessDay dayToCheck = (BusinessDay) bd.getValue();

            for(LogisticsObject received : dayToCheck.getReceivedDeliveries()){
                if(received.getId() == deliveryID){
                    return received;
                }
            }
        }
        return null;
    }

}
