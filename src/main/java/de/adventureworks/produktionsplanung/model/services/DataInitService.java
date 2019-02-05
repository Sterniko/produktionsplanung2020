package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.Data;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Customer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataInitService {

    private Data data;

    @PostConstruct
    public void init() {
        data = new Data();

        Customer customer1 = new Customer("Metro AG", Country.GERMANY);
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(customer1);

        data.setCustomers(customers);
    }

    public Data getData() {
        return data;
    }
}
