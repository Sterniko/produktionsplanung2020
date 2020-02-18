package de.adventureworks.produktionsplanung.model.services.init;

import de.adventureworks.produktionsplanung.model.services.productionTrial.ProductionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class ProductionInitService {

    @Autowired
    ProductionService2 productionService2;

    @PostConstruct
    public void initProductionStarter() {
        productionService2.simulateWholeProduction(2021);
    }
}
