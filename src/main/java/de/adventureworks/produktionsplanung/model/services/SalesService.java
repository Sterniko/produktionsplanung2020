package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.events.CustomerOrderEvent;
import de.adventureworks.produktionsplanung.model.entities.events.IEvent;
import de.adventureworks.produktionsplanung.model.entities.events.PlaceCustomerOrderEvent;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class SalesService {

    private DataBean dataBean;

    @Autowired
    private ProductionEngagementService pES;

    public SalesService(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public boolean checkIfOrderPossible(LocalDate today, LocalDate arrivalDate, Country country,  Map<Bike, Integer> bikes ,boolean hasPrio) {
        ArrivalCalculatorService acs = new ArrivalCalculatorService(new ShipService(dataBean), dataBean);
        LocalDate latestPossibleSentDate = acs.calculateLatestPossibleSendDate(arrivalDate, country);
        if (pES.placeCustomrOrder(today, latestPossibleSentDate,bikes, hasPrio)) {
            return true;
        } else {
            return false;
        }
    }


    public void startEvent(boolean prio, Map<Bike, Integer> order, LocalDate dueDate, LocalDate businessDay) {
        BusinessDay businessDay1 = dataBean.getBusinessDay(businessDay);
        CustomerOrderEvent placeCustomerOrderEvent = new CustomerOrderEvent(prio, order, dueDate);
        List<IEvent> eventList = businessDay1.getEventList();
        eventList.add(placeCustomerOrderEvent);
    }


}
