package de.adventureworks.produktionsplanung.controller.deliveries;


public class DeliveryIdRequest {

    private int id;

    public DeliveryIdRequest() {
    }

    public DeliveryIdRequest(int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "MarketingRequest{" +
                "id" + id +
                '}';
    }

}


