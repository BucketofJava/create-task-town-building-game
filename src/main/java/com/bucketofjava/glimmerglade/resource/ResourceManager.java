package com.bucketofjava.glimmerglade.resource;

import com.bucketofjava.glimmerglade.saving.SaveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ResourceManager {
    private HashMap<String, HashMap<String, Integer>> resourceMap;
    public ResourceManager(SaveData saveData){
        resourceMap=new HashMap<>();
    }
    public void addResource(String resource){
        if(this.resourceMap.containsKey(resource)){
            this.resourceMap.get(resource).put("amount", this.resourceMap.get(resource).get("amount")+1);
            return;
        }
        this.resourceMap.put(resource, new HashMap<>());
        this.resourceMap.get(resource).put("amount", 1);
    }
    public void addResource(String resource, HashMap<String, Integer> metadata){
        if(this.resourceMap.containsKey(resource)){
            this.resourceMap.get(resource).put("amount", this.resourceMap.get(resource).get("amount")+1);
            for (String key:metadata.keySet()){
                this.resourceMap.get(resource).put(key, metadata.get(key));
            }
            return;
        }
        this.resourceMap.put(resource, new HashMap<>());
        this.resourceMap.get(resource).put("amount", 1);
        for (String key:metadata.keySet()){
            this.resourceMap.get(resource).put(key, metadata.get(key));
        }
    }
    public void addResources(String resource, int amount){
        if(this.resourceMap.containsKey(resource)){
            this.resourceMap.get(resource).put("amount", this.resourceMap.get(resource).get("amount")+amount);

            return;
        }
        this.resourceMap.put(resource, new HashMap<>());
        this.resourceMap.get(resource).put("amount", amount);
    }
    public void addResources(String resource, int amount, HashMap<String, Integer> metadata){
        if(this.resourceMap.containsKey(resource)){
            this.resourceMap.get(resource).put("amount", this.resourceMap.get(resource).get("amount")+amount);
            for (String key:metadata.keySet()){
                this.resourceMap.get(resource).put(key, metadata.get(key));
            }
            return;
        }
        this.resourceMap.put(resource, new HashMap<>());
        this.resourceMap.get(resource).put("amount", amount);
        for (String key:metadata.keySet()){
            this.resourceMap.get(resource).put(key, metadata.get(key));
        }
    }
    public void addResources(Iterable<String> resources){
     for(String resource:resources){
         addResource(resource);
     }
    }
    public int checkStatistic(String resource, String statistic){
        if(!this.resourceMap.containsKey(resource)) return 0;
        return this.resourceMap.get(resource).getOrDefault(statistic, 0);
    }
    public Set<String> getResources(){
        return resourceMap.keySet();
    }
    public int checkCount(String resource){
    if(!this.resourceMap.containsKey(resource)) return 0;
    return this.resourceMap.get(resource).getOrDefault("amount", 0);
    }
    public void deleteResource(String resource, int amount){
        if(!this.resourceMap.containsKey(resource)) return;
        if(this.resourceMap.get(resource).get("amount")<=amount){ this.resourceMap.remove(resource);
        return;}
        this.resourceMap.get(resource).put("amount", this.resourceMap.get(resource).get("amount")-amount);
    }

    public boolean useResource(String resource, int amount) {
            if (!this.resourceMap.containsKey(resource)) return false;
            if (this.resourceMap.get(resource).get("amount") < amount) return false;
            this.resourceMap.get(resource).put("amount", this.resourceMap.get(resource).get("amount") - amount);
        return true;
    }
}
