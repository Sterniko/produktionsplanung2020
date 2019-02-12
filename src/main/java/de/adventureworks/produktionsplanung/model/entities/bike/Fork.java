package de.adventureworks.produktionsplanung.model.entities.bike;

import de.adventureworks.produktionsplanung.model.entities.external.Supplier;

public class Fork extends  Component {

    public Fork() {
    }

    public Fork(String name, Supplier supplier){
        super(name, supplier);
    }
}
