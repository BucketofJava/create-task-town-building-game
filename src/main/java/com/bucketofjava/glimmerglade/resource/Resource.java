package com.bucketofjava.glimmerglade.resource;

import java.util.HashMap;

public class Resource {
    public static HashMap<String, Float> resourceCosts=new HashMap<>(){{ put("stone", 5F); put("iron", 60F);}};
    private final String name;

    public Resource(String name){
        this.name=name;

    }
    public Resource create(){
        return new Resource(this.name);
    }
    public String getName(){
        return this.name;
    }
}
