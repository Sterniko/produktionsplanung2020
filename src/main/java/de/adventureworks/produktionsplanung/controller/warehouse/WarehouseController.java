package de.adventureworks.produktionsplanung.controller.warehouse;

//import com.sun.tools.corba.se.idl.constExpr.Or;
import de.adventureworks.produktionsplanung.controller.util.RequestMapper;
import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.services.OrderService;
import de.adventureworks.produktionsplanung.model.services.ProductionEngagementService;
import de.adventureworks.produktionsplanung.model.services.SortService;
import de.adventureworks.produktionsplanung.model.services.WarehouseService;
import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class WarehouseController {

    @Autowired
    private DataBean dataBean;

    @Autowired
    private SortService sortService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ProductionService2 productionService2;



    public WarehouseController() {

    }

    @RequestMapping("/warehouse")
    public String getBusinessWeeks(Model model) {

        List<BusinessDay> businessDayList = dataBean.getBusinnesDayListFromDate(LocalDate.of(2018,12,27));
        businessDayList = sortService.sortBusinessDayList(businessDayList);



        model.addAttribute("businessWeeks", dataBean.getBusinessWeeks());
        model.addAttribute("businessDays", businessDayList);
        model.addAttribute("components", dataBean.getComponents());
        model.addAttribute("warehouseRequest", new WarehouseRequest());
        return "warehouse";
    }

/*
    @RequestMapping(value = "/warehouse", method = RequestMethod.POST)
    public void updateComponentStock(LocalDate date, Map<Component, Integer> newStock) {

    }
*/

    @RequestMapping(value = "/warehouse", method = RequestMethod.POST)
    public String updateComponentStock(WarehouseRequest warehouseRequest) {
        System.out.println(warehouseRequest);

        LocalDate date = warehouseRequest.getDate();
        BusinessDay businessDay = dataBean.getBusinessDay(date);
        Map<Component, Integer> warehouseStock = RequestMapper.mapComponentStringMap(
                warehouseRequest.getWarehouseMap(), dataBean.getComponents());

        warehouseService.startEvent(businessDay, warehouseStock);
        productionService2.simulateWholeProduction();


        return "redirect:/warehouse";

    }


}
