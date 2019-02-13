package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import de.adventureworks.produktionsplanung.model.entities.logistics.LogisticsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ArrivalCalculater {

    @Autowired
    static DataBean databean;


    public static LocalDate calculate(LocalDate sendDate, int leadTime, Country country) {


        LocalDate temp = sendDate;
        while (leadTime > 0) {

            BusinessDay bd = databean.getBusinessDay(temp);


            if (Country.CHINA == country) {
                if (temp.getDayOfYear() - sendDate.getDayOfYear() < 2) {
                    if (!bd.getWorkingDays().get(country)) {
                        leadTime--;
                    }
                } else if (temp.getDayOfYear() - sendDate.getDayOfYear() > 32) {
                    if (!bd.getWorkingDays().get(Country.GERMANY)) {
                        leadTime--;
                    }
                } else {
                    leadTime--;
                }

            } else if (Country.USA == country) {
                if (temp.getDayOfYear() - sendDate.getDayOfYear() < 2) {
                    if (!bd.getWorkingDays().get(country)) {
                        leadTime--;
                    }
                } else if (temp.getDayOfYear() - sendDate.getDayOfYear() > 16) {
                    if (!bd.getWorkingDays().get(Country.GERMANY)) {
                        leadTime--;
                    }
                } else {
                    leadTime--;
                }
            } else if (Country.SPAIN == country) {
                return temp.plusDays(leadTime);
            } else {
                if (!bd.getWorkingDays().get(country)) {
                    leadTime--;
                }
                temp = temp.plusDays(1);
            }
        }
        return temp;
    }
}