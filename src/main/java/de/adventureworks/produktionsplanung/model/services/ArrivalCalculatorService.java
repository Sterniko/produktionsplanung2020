package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ArrivalCalculatorService {

    private ShipService shipService;
    private DataBean dataBean;

    public ArrivalCalculatorService(ShipService shipService, DataBean dataBean) {
        this.shipService = shipService;
        this.dataBean = dataBean;
    }

    public LocalDate calculateDeliveryFrom(LocalDate start, Country country) {
        switch (country) {
            case CHINA:
                LocalDate startDateWithWorkingDays = addWorkingDays(start, Country.CHINA, 2);
                LocalDate arrival = shipService.getNextShip(startDateWithWorkingDays).getArrival();
                return addWorkingDays(arrival, Country.CHINA, 2);
            case GERMANY:
                return addWorkingDays(start, Country.GERMANY, 3);
            case SPAIN:
                return start.plusDays(9);
            default:
                return null;
        }
    }

    public LocalDate calculateLatestPossibleSendDate(LocalDate arriveDate, Country country) {
        switch (country) {
            case CHINA:
                LocalDate resultCHINA = subtractWorkingDays(arriveDate, Country.CHINA, 2);
                resultCHINA = resultCHINA.plusDays(30);
                return subtractWorkingDays(resultCHINA, Country.CHINA, 2);
            case GERMANY:
                return subtractWorkingDays(arriveDate, Country.GERMANY, 3);
            case USA:
                LocalDate resultUSA = subtractWorkingDays(arriveDate, Country.USA, 2);
                resultUSA = resultUSA.plusDays(14);
                return subtractWorkingDays(resultUSA, Country.USA, 2);
            case SPAIN:
                return arriveDate.minusDays(9);
            case FRANCE:
                return subtractWorkingDays(arriveDate, Country.FRANCE, 5);
            case AUSTRIA:
                return subtractWorkingDays(arriveDate, Country.AUSTRIA, 4);
            case SWITZERLAND:
                return subtractWorkingDays(arriveDate, Country.SWITZERLAND, 4);
            default:
                return null;
        }
    }


    private LocalDate subtractWorkingDays(LocalDate date, Country country, int amount) {
        LocalDate today = date;
        BusinessDay bd;
        for (int i = 0; i < amount; ) {
            bd = dataBean.getBusinessDay(today);
            if (!bd.getWorkingDays().get(country)) {
                i++;
            }
            today = today.minusDays(1);
        }
        return today;
    }

    private LocalDate addWorkingDays(LocalDate date, Country country, int amount) {
        LocalDate today = date;
        BusinessDay bd;
        for (int i = 0; i < amount; ) {
            bd = dataBean.getBusinessDay(today);
            if (!bd.getWorkingDays().get(country)) {
                i++;
            }
            today = today.plusDays(1);
        }
        return today;
    }

}
