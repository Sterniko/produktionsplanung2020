package de.adventureworks.produktionsplanung.production.model;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ProductionModel {
    ApplicationContext ctx;
    Object dataBean;

    public ProductionModel(ApplicationContext ctx){
        this.ctx = ctx;
        this.dataBean = this.ctx.getBean("dataBean");
    }
    public Map<LocalDate, BusinessDay> getBusinessDays(){
        return ((DataBean) this.dataBean).getBusinessDays();
    }
    public List<Bike> getBikes(){
        return (((DataBean) this.dataBean).getBikes());
    }
}
