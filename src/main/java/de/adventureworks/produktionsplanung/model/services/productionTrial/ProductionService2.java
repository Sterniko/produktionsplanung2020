package de.adventureworks.produktionsplanung.model.services.productionTrial;

import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductionService2 {

    @Autowired
    private DataBean dataBean;

    public void simulateInitialProduction(Map<Bike, Double> bikeProductionShareMap, double[] monthPercentArr, int bikesPA) {

    }


}
