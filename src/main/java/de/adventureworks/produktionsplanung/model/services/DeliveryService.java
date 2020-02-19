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
import java.time.format.DateTimeParseException;
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
        if (deliveryID.equals("none")) {
            return null;
        }
        BusinessDay arrivalDay = getArrivalDateToDeliveryID(deliveryID);

        List<LogisticsObject> receivedDeliveries = arrivalDay.getReceivedDeliveries();

        LogisticsObject returningLogisticObject = null;

        for (LogisticsObject lo : receivedDeliveries) {
            if (lo.getId().equals(deliveryID)) {
                return lo;
            }
        }
        return returningLogisticObject;

    }

    public BusinessDay getArrivalDateToDeliveryID(String deliveryID) {

        deliveryID = deliveryID.trim();
        String arrivalDateString = deliveryID.substring(0, 10);
        LocalDate arrivalDate = null;
        try {
            arrivalDate = LocalDate.parse(arrivalDateString);
        } catch (DateTimeParseException p) {



            int ende = -1;
            int anfang = -1;
            ende = arrivalDateString.length() ;
            anfang = arrivalDateString.length() - 4;
            System.out.println("here");
            int jahr = Integer.parseInt(arrivalDateString.substring(anfang, ende));
            ende = arrivalDateString.length() - 5;
            anfang = arrivalDateString.length() - 7;
            System.out.println("here");
            int month = Integer.parseInt(arrivalDateString.substring(anfang, ende));
            ende = arrivalDateString.length() - 8;
            anfang = arrivalDateString.length() - 10;
            System.out.println("here");
            int tag = Integer.parseInt(arrivalDateString.substring(anfang, ende));
            arrivalDate = LocalDate.of(jahr, month, tag);
        }
        BusinessDay arrivalDay = dataBean.getBusinessDay(arrivalDate);

        return arrivalDay;
    }


    public LogisticsObject getLoByID(String deliveryID) {
        for (Map.Entry bd : dataBean.getBusinessDays().entrySet()) {
            BusinessDay dayToCheck = (BusinessDay) bd.getValue();

            for (LogisticsObject received : dayToCheck.getReceivedDeliveries()) {
                if (received.getId() == deliveryID) {
                    return received;
                }
            }
        }
        return null;
    }

}
