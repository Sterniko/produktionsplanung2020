package de.adventureworks.produktionsplanung.controller.util;

import de.adventureworks.produktionsplanung.model.entities.bike.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMapper {

    private RequestMapper() {

    }

    public static Map<Component, Integer> mapComponentStringMap(Map<String, Integer> stringMap, List<Component> components) {

        Map<Component,Integer> returnMap = new HashMap<>();
        for(String cName: stringMap.keySet()) {
            for(Component c: components) {
                if(cName.equals(c.getName())) {
                    returnMap.put(c, stringMap.get(cName));
                    break;
                }
            }
        }

        return returnMap;

    }

}
