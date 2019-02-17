package de.adventureworks.produktionsplanung.model.services.productionTrial.eventHandle;

public class DeliveryNotFoundException extends Exception {

    public DeliveryNotFoundException(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    private String deliveryId;

    public String getDeliveryId() {
        return deliveryId;
    }
}
