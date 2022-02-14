package com.bucketofjava.glimmerglade.building;

import com.bucketofjava.glimmerglade.citizen.Citizen;



import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Building {
    public ArrayList<String> citizens;

    public static HashMap<String, Building> allPossibleBuildings=new HashMap<>(){{put("farm", new Farm(0)); put("woodlodge", new Woodlodge(0)); put("mine", new Mine(0)); put("ironmine", new IronMine(0));}};

    public int level;
    private HashMap<String, Integer> cost=new HashMap<>();
    public Building(HashMap<String, Integer> cost){
        citizens=new ArrayList<String>();
        level=0;
        this.cost=cost;
    }
    public void assignCitizen(String name){
        citizens.add(name);
    }
    public void unassignCitizen(String name){
        citizens.remove(name);
    }
    public static void initializePossibleBuildings(){

    }
    public HashMap<String, Integer> getCost(){return cost;}
    public abstract Building createNew();
    public abstract HashMap<String, String> produceDaily();
    public abstract void upgrade();

}
