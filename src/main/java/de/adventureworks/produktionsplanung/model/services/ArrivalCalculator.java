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
public class ArrivalCalculator {

    /**
        Methode welche den LocalDate errechnet abhäning von duration und Land wann die Lieferung ankommt

        @param sendDate das Datum an wann die Lieferung abgeschickt wird
        @param duration die Dauer wie lange die Lieferung dauert (Steht im Supplier)
        @param country das Land aus dem die Lieferung kommt (Steht im Supplier)
     */
    public static LocalDate calculate(LocalDate sendDate, int duration, Country country, DataBean dataBean ) {


        //TODO manchmal Nullpointer Exception
        //TODO Ger hinzufügen
        LocalDate temp = sendDate;
        while (duration > 0) {

            BusinessDay bd = dataBean.getBusinessDay(temp);


            if (Country.CHINA == country) {
                if (temp.getDayOfYear() - sendDate.getDayOfYear() < 2) {
                    if (!bd.getWorkingDays().get(country)) {
                        duration--;
                    }
                } else if (temp.getDayOfYear() - sendDate.getDayOfYear() > 32) {
                    if (!bd.getWorkingDays().get(Country.GERMANY)) {
                        duration--;
                    }
                } else {
                    duration--;
                }

            } else if (Country.USA == country) {
                if (temp.getDayOfYear() - sendDate.getDayOfYear() < 2) {
                    if (!bd.getWorkingDays().get(country)) {
                        duration--;
                    }
                } else if (temp.getDayOfYear() - sendDate.getDayOfYear() > 16) {
                    if (!bd.getWorkingDays().get(Country.GERMANY)) {
                        duration--;
                    }
                } else {
                    duration--;
                }
            } else if (Country.SPAIN == country) {
                return temp.plusDays(duration);
            } else {
                if (!bd.getWorkingDays().get(country)) {
                    duration--;
                }
            }
            temp = temp.plusDays(1);
        }
        return temp;
    }
}