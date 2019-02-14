package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.*;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.time.LocalDate;
import java.util.HashMap;
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
            //TODO dem Warehouse bzw Produktion bescheid geben dass wir sachen entwenden und neu bestellen
            return true;
        } else {


            return false;
            /* LocalDate earlierstSaddleArrival = acs.calculateDeliveryFrom(today, Country.CHINA);
            LocalDate latestShipmentDate = acs.calculateLatestPossibleSendDate(arrivalDate, country);

            if(!earlierstSaddleArrival.isAfter(latestShipmentDate)){
                //ODO platz in der Production
                return true;
            }else{
                return false;
            }
            */
        }
    }


}
