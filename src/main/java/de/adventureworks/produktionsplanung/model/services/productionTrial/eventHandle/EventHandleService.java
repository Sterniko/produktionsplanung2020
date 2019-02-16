package de.adventureworks.produktionsplanung.model.services.productionTrial.eventHandle;


import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventHandleService {


    @Autowired
    private DataBean dataBean;

    public EventHandleService() {
    }

    public void handleEvent(IEvent event) {

        if (event instanceof ShipDeleteEvent) {

        } else if (event instanceof DeliveryChangeEvent) {

        } else if (event instanceof PlaceCustomerOrderEvent) {

        } else if (event instanceof ProductionIncreaseEvent) {

        }

    }
}
