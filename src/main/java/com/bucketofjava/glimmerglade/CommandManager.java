package com.bucketofjava.glimmerglade;

import com.bucketofjava.glimmerglade.building.Building;
import com.bucketofjava.glimmerglade.citizen.Citizen;
import com.bucketofjava.glimmerglade.resource.Resource;

import java.util.HashMap;
import java.util.Locale;

public class CommandManager {
    String assignCommandString="assign {citizen} to {building}";
    String profileCommandString="profile {citizen/building/nation} {name}";
    String constructCommandString="construct {building}";
    String[] commands=new String[]{assignCommandString, profileCommandString, constructCommandString};

    public CommandManager(){}
    public int runCommand(String string){

        try{
        switch(string.split(" ")[0]){
            case "profile":
                switch(string.split(" ")[1]){
                    case "citizen":
                        Citizen citizen=GameManager.getManager().getCitizenManager().getCitizen(string.split(" ")[2]);
                        if(citizen==null){break;}
                        System.out.println(citizen.getName());
                        System.out.println("Age: "+citizen.getAge()+" Birthday: "+citizen.getBirthday()+"th day of the year");
                        System.out.println("Hunger: "+citizen.getHunger()+" Satisfaction: "+citizen.getSatisfaction());
                        System.out.println("Skills:");
                        for (String skill:citizen.getAllSkills()){
                            System.out.println(skill+": "+citizen.getSkillLevel(skill));
                        }
                        return 0;

                    default:
                        return 0;
                }
                    case "skip":
                        if(string.split(" ").length<=1) return -1;
                        try{
                            return -1*Integer.parseInt(string.split(" ")[1]);
                        }catch (Exception e){
                            return 0;
                        }
                    case "construct":
                        if(GameManager.getManager().getRemainingActions()<=0){
                            System.out.println("You don't have enough actions!");
                            return 0;
                        }
                        int value=GameManager.getManager().buildingManager.constructBuilding(string.split(" ")[1].toLowerCase());
                        if(value==0){System.out.println("Construction failed. Check to see if you have enough materials for this building");
                        return 0;}
                        return 1;
                    case "inventory":
                        System.out.println("Resources: ");
                        for (String resource:GameManager.getManager().getResourceManager().getResources()){
                            System.out.println(resource+" x "+GameManager.getManager().getResourceManager().checkCount(resource));
                        }
                        System.out.println("Coins: "+GameManager.getManager().getMoney());
                        System.out.println("Buildings: ");
                        for(String buildingName:GameManager.getManager().getBuildingManager().getBuildingNames()){
                            Building building=GameManager.getManager().getBuildingManager().getBuilding(buildingName);
                            System.out.println(buildingName+" level "+String.valueOf(building.level));
                        }
                        System.out.println("Citizens: ");
                        for(String citizenName:GameManager.getManager().getCitizenManager().getCitizenNames()){
                            System.out.println(citizenName);
                        }
                        return 0;
                    case "assign":
                        if(GameManager.getManager().getRemainingActions()<=0){
                            System.out.println("You don't have enough actions!");
                            return 0;
                        }
                        if(string.split(" ").length<3) return 0;
                        Citizen citizen=GameManager.getManager().getCitizenManager().getCitizen(string.split(" ")[1]);
                        if(citizen==null) return 0;
                        Building building=GameManager.getManager().getBuildingManager().getBuilding(string.split(" ")[2]);
                        if(building==null) return 0;
                        for (String b:GameManager.getManager().getBuildingManager().getBuildingNames()){
                            if(GameManager.getManager().getBuildingManager().getBuilding(b).citizens.contains(citizen.getName())){
                                GameManager.getManager().getBuildingManager().getBuilding(b).unassignCitizen(citizen.getName());
                            }
                        }
                        building.assignCitizen(citizen.getName());
                        System.out.println(citizen.getName()+" assigned to building "+string.split(" ")[2]);
                        return 1;
                    case "sell":
                        if(GameManager.getManager().getRemainingActions()<=0){
                            System.out.println("You don't have enough actions!");
                            return 0;
                        }
                        if(string.split(" ").length<3){return 0;}
                        int amount;
                        try{
                            amount=Integer.parseInt(string.split(" ")[2]);
                        }catch (Exception e){
                            return 0;
                        }
                        if(GameManager.getManager().getCitizenManager().getCitizen(string.split(" ")[1])!=null) {
                            GameManager.getManager().getCitizenManager().killCitizen(string.split(" ")[1]);
                            GameManager.getManager().incrementMoney(2.5F);
                            return 1;
                        }
                        if(GameManager.getManager().getResourceManager().checkCount(string.split(" ")[1])<=0) {return 0;}
                        if(Resource.resourceCosts.getOrDefault(string.split(" ")[1], 0F)<=0) return 0;
                        GameManager.getManager().getResourceManager().useResource(string.split(" ")[1], amount);
                        GameManager.getManager().incrementMoney(amount*Resource.resourceCosts.getOrDefault(string.split(" ")[1], 0F));
                        return 1;
                    case "blueprints":
                        for(String buildingName:Building.allPossibleBuildings.keySet()){
                            System.out.println(buildingName+":");
                            for(String resourceName:Building.allPossibleBuildings.get(buildingName).getCost().keySet()){
                                System.out.println(resourceName+" x "+Building.allPossibleBuildings.get(buildingName).getCost().get(resourceName));
                            }
                        }
                        return 0;
                    case "help":
                        System.out.println("help: list all commands");
                        System.out.println("construct [name of building]: construct a certain building");
                        System.out.println("assign [name of citizen] [name of building]: assign a citizen to a certain building");
                        System.out.println("skip (number of days): skip one day, or specify a certain amount to skip");
                        System.out.println("shop: list prices for buying and selling items");
                        System.out.println("sell [item or citizen] [amount(put 1 for citizen)]: sell an item or a citizen based on the shop");
                        System.out.println("buy [item] [amount]: buy an item or a citizen based on the shop");
                        System.out.println("blueprints: list buildings and their costs");
                        System.out.println("profile citizen [citizen name]: get the profile of a certain citizen");
                        System.out.println("inventory: get your town's inventory, including items, buildings, and citizens");
                        return 0;
                    case "shop":
                        for(String resource:Resource.resourceCosts.keySet()){
                            System.out.println(resource+" - "+String.valueOf(Resource.resourceCosts.get(resource)+" coins"));
                        }
                        System.out.println("Citizen (Selling) - 2.5 coins");

                        return 0;
                    case "buy":
                        if(GameManager.getManager().getRemainingActions()<=0){
                            System.out.println("You don't have enough actions!");
                            return 0;
                        }
                        if(string.split(" ").length<3){return 0;}
                        int buyAmount;
                        try{
                            buyAmount=Integer.parseInt(string.split(" ")[2]);
                        }catch (Exception e){
                            return 0;
                        }

                        //if(GameManager.getManager().getResourceManager().checkCount(string.split(" ")[1])<=0) {return 0;}
                        if(Resource.resourceCosts.getOrDefault(string.split(" ")[1], 0F)<=0) return 0;
                        if(GameManager.getManager().getMoney()<buyAmount*Resource.resourceCosts.getOrDefault(string.split(" ")[1], 0F)) return 0;
                        GameManager.getManager().getResourceManager().addResources(string.split(" ")[1], buyAmount);
                        GameManager.getManager().incrementMoney(-1*buyAmount*Resource.resourceCosts.getOrDefault(string.split(" ")[1], 0F));
                        return 1;

        }} catch (IndexOutOfBoundsException e){
            System.out.println("Incorrect amount of arguments! Type help for a full list and description of commands");
        }
        return 0;
    }

}
