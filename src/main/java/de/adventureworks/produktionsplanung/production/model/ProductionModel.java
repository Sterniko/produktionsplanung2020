package de.adventureworks.produktionsplanung.production.model;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ProductionModel {
    @Autowired
    DataBean dataBean;

    public ProductionModel(){

    }

    public Map<LocalDate, BusinessDay> getBusinessDays(){
        return ((DataBean) this.dataBean).getBusinessDays();
    }
    public List<Bike> getBikes(){
        return (((DataBean) this.dataBean).getBikes());
    }

}
