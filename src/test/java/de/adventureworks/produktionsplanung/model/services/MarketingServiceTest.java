package de.adventureworks.produktionsplanung.model.services;

import de.adventureworks.produktionsplanung.controller.util.RequestMapper;
import de.adventureworks.produktionsplanung.model.DataBean;
import de.adventureworks.produktionsplanung.model.entities.bike.Bike;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessDay;
import de.adventureworks.produktionsplanung.model.entities.businessPeriods.BusinessWeek;
import de.adventureworks.produktionsplanung.model.entities.external.Country;
import de.adventureworks.produktionsplanung.model.services.init.DataInitService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataBean.class, DataInitService.class,BusinessCalendar.class, MarketingService.class})

public class MarketingServiceTest {
    @Autowired
    private DataBean dataBean;

    @Autowired
    private MarketingService marketingService;

    //TODO: ADD test that will fail
    @Test
    public void testMarketing() {
        HashMap<Bike,Integer> helperMap;
        HashMap<Bike,Integer> newWeeklyPlannedProduction;
        HashMap<Bike,Integer> newBDPP = new HashMap<>();
        HashMap<Bike,Integer> planToAdd = new HashMap<>();
        HashMap<Bike,Integer> checkNewWeekPlan = new HashMap<>();

        LocalDate today = LocalDate.now();
        int businessWeek = 2;
        BusinessWeek bW = this.dataBean.getBusinessWeeks().get(businessWeek - 1);

        for(Bike bike : dataBean.getBikes()){
            newBDPP.put(bike,100);
        }
        //Set 100 each BD
        for(BusinessDay bd : bW.getDays()){
            if(!bd.getWorkingDays().get(Country.GERMANY)){
                bd.setPlannedProduction(newBDPP);
            }
        }
        Map<Bike,Integer> checkWeek = new HashMap<>();

        //Set Week with 600
        for(Bike bike : dataBean.getBikes()){
            checkWeek.put(bike,600);
        }
        helperMap = marketingService.getWeeklyPlannedProduction(today, bW);
        //check if both are 600 6WD
        Assert.assertEquals(helperMap,checkWeek);

        //--> Woche auf 600
        //erhöhe um 100 each bike
        for(Bike bike : dataBean.getBikes()){
            planToAdd.put(bike,100);
        }
        //Neue Map sollte Bike,700 enthalten
        for(Bike bike : dataBean.getBikes()){
            checkNewWeekPlan.put(bike,700);
        }
        newWeeklyPlannedProduction = marketingService.addAmountToBusinessWeek(helperMap,planToAdd);

        Assert.assertEquals(newWeeklyPlannedProduction,checkNewWeekPlan);
    }
    @Test
    public void testAddAmount() {
        HashMap<Bike,Integer> helperMap = new HashMap<>();
        HashMap<Bike,Integer> newWeeklyPlannedProduction;
        HashMap<Bike,Integer> planToAdd = new HashMap<>();
        HashMap<Bike,Integer> checkNewWeekPlan = new HashMap<>();

        //444 PP
        for(Bike bike : dataBean.getBikes()){
            helperMap.put(bike,444);
        }
        //222 to Add this week
        for(Bike bike : dataBean.getBikes()){
            planToAdd.put(bike,222);
        }
        newWeeklyPlannedProduction = marketingService.addAmountToBusinessWeek(helperMap,planToAdd);

        //should be 666
        for(Bike bike :dataBean.getBikes()){
            checkNewWeekPlan.put(bike,666);
        }
        Assert.assertEquals(newWeeklyPlannedProduction,checkNewWeekPlan);
    }


    @Test
    public void testGetWeeklyProduction() {
        HashMap<Bike,Integer> helperMap;
        HashMap<Bike,Integer> newBDPP = new HashMap<>();

        LocalDate today = LocalDate.now();
        int businessWeek = 2;
        BusinessWeek bW = this.dataBean.getBusinessWeeks().get(businessWeek - 1);


        for(Bike bike : dataBean.getBikes()){
            newBDPP.put(bike,100);
        }
        //Add 100 Bike if Workingday
        for(BusinessDay bd : bW.getDays()){
            if(!bd.getWorkingDays().get(Country.GERMANY)){
                bd.setPlannedProduction(newBDPP);
            }
        }
        Map<Bike,Integer> newBikeMap = new HashMap<>();

        //Just sunday not a WD so 600
        for(Bike bike : dataBean.getBikes()){
            newBikeMap.put(bike,600);
        }
        helperMap = marketingService.getWeeklyPlannedProduction(today, bW);
        Assert.assertEquals(helperMap,newBikeMap);

    }
}

