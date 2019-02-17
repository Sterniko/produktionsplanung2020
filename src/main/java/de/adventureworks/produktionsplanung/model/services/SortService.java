package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SortService {

    /**
     *
     * @param businessDayList to Sort
     * @return sorted List after LocalDate
     */
    public static List<BusinessDay> sortBusinessDayList(List<BusinessDay> businessDayList){
        //sort them
        Collections.sort(businessDayList, new Comparator<BusinessDay>() {
            public int compare(BusinessDay o1, BusinessDay o2) {
                if(o1.getDate().isAfter(o2.getDate())){
                    return 1;
                }
                return -1;
            }
        });
        return businessDayList;
    }

    /**
     *Takes Map <LD,BD> and returns a List of BD
     * @param localeDateBusinessDaysMap Map to Convert into List
     * @return List of Businessdays
     */

    public static List<BusinessDay> mapToListBusinessDays(Map<LocalDate,BusinessDay> localeDateBusinessDaysMap){
        List<BusinessDay> businessDayList = new ArrayList<>();
        for(Map.Entry entry: localeDateBusinessDaysMap.entrySet()){
            businessDayList.add((BusinessDay) entry.getValue());
        }
        return businessDayList;
    }


}
