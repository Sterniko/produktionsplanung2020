package de.adventureworks.produktionsplanung.controller.sales;

import de.adventureworks.produktionsplanung.model.entities.external.Country;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SalesRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate customerDeliveryDate;
    private Country country;
    private String bike;
    private int amount;


    public SalesRequest() {
    }

    public LocalDate getCustomerDeliveryDate() {
        return customerDeliveryDate;
    }

    public void setCustomerDeliveryDate(LocalDate customerDeliveryDate) {
        this.customerDeliveryDate = customerDeliveryDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getBike() {
        return bike;
    }

    public void setBike(String bike) {
        this.bike = bike;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SalesRequest{" +
                "customerDeliveryDate=" + customerDeliveryDate +
                ", country=" + country +
                ", bike=" + bike +
                ", amount=" + amount +
                '}';
    }
}
