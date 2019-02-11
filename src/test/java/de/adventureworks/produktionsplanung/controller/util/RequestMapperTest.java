package de.adventureworks.produktionsplanung.controller.util;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;
import de.adventureworks.produktionsplanung.model.entities.bike.Frame;
import de.adventureworks.produktionsplanung.model.entities.bike.Saddle;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.entities.external.Supplier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestMapperTest {

    Map<String,Integer> componentStringMap;

    Map<Component,Integer> componentMap;

    List<Component> components;

    Component comp1;
    Integer c1Amount;

    @Before
    public void init() {
        String c1Name = "comp1";
        String c2Name = "comp2";
        String c3Name = "comp3";
        c1Amount = 200;
        Integer c2Amount = 500;
        Integer c3Amount = 2;

        componentStringMap = new HashMap<>();
        componentStringMap.put(c1Name, c1Amount);
        componentStringMap.put(c2Name, c2Amount);
        componentStringMap.put(c3Name, c3Amount);

        Supplier s1 = new Supplier("supp1",44, 3, Country.CHINA);
        Supplier s2 = new Supplier("supp2",44, 3, Country.SPAIN);


        comp1 = new Frame(c1Name, s1);

        components = new ArrayList<>();
        components.add(comp1);
        components.add(new Saddle(c2Name, s2));
        components.add(new Frame(c3Name, s1));
        components.add(new Frame(c3Name, s1));
        components.add(new Frame(c3Name, s1));

    }

    @Test
    public void testComponentMapper() {

            Map<Component, Integer> resultMap = RequestMapper.mapComponentStringMap(componentStringMap, components);
            assertEquals(resultMap.size(), componentStringMap.size());
            for(Component c :resultMap.keySet()) {
                assertTrue(components.contains(c));
            }
            assertEquals(resultMap.get(comp1), c1Amount);

    }

}
