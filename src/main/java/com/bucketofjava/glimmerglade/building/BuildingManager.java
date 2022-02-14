package com.bucketofjava.glimmerglade.building;

import com.bucketofjava.glimmerglade.GameManager;

import com.bucketofjava.glimmerglade.saving.SaveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BuildingManager {
    private HashMap<String, Building> allBuildings;
    public BuildingManager(SaveData data){
        allBuildings=new HashMap<>();
    }
    public int constructBuilding(String building){
        if(!Building.allPossibleBuildings.containsKey(building)) return 0;
        System.out.println("Building is known");
        for(String r:Building.allPossibleBuildings.get(building).getCost().keySet()){
            if(GameManager.getManager().getResourceManager().checkCount(r)<Building.allPossibleBuildings.get(building).getCost().get(r)){
                System.out.println("Not enough "+r);
                return 0;
            }
        }
        if(!allBuildings.containsKey(building)){

            allBuildings.put(building,Building.allPossibleBuildings.get(building).createNew());
            for(String resource:Building.allPossibleBuildings.get(building).getCost().keySet()){
                GameManager.getManager().getResourceManager().deleteResource(resource, Building.allPossibleBuildings.get(building).getCost().get(resource));
            }
            return 1;
        }
        for(String resource:Building.allPossibleBuildings.get(building).getCost().keySet()){
            GameManager.getManager().getResourceManager().deleteResource(resource, Building.allPossibleBuildings.get(building).getCost().get(resource));
        }
        allBuildings.get(building).upgrade();
        return 2;
    }
    public Set<String> getBuildingNames(){
        return allBuildings.keySet();
    }
    public Building getBuilding(String name){
        return allBuildings.getOrDefault(name, null);
    }
}
