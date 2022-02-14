package com.bucketofjava.glimmerglade;

import com.bucketofjava.glimmerglade.building.Building;
import com.bucketofjava.glimmerglade.building.BuildingManager;
import com.bucketofjava.glimmerglade.citizen.Citizen;
import com.bucketofjava.glimmerglade.citizen.CitizenManager;

import com.bucketofjava.glimmerglade.resource.ResourceManager;
import com.bucketofjava.glimmerglade.saving.SaveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    int currentDay;
    CommandManager cm;
    int actionNum;
    int maxActions;
    int foodRequired=31;
    float money;
    CitizenManager citizenManager;
    BuildingManager buildingManager;
    ResourceManager resourceManager;
    static GameManager manager;
    String saveDataPath;
    HashMap<String, Integer> inventory;
    public GameManager(String saveDataPath){
        this.saveDataPath=saveDataPath;setManager(this);
    }
    public void start(){
        maxActions=5;
        Scanner scanner=new Scanner(System.in);
        cm=new CommandManager();
        buildingManager=new BuildingManager(new SaveData());
        citizenManager=new CitizenManager(new SaveData());
        resourceManager=new ResourceManager(new SaveData());
        Citizen citizen=new Citizen("JohnSS", 30, 300, null);
        citizen.addSkillLevel("Alchemy", 17);
        citizenManager.addCitizen(citizen);
        Citizen citizen2=new Citizen("MaryAA", 30, 300, null);
        citizen2.addSkillLevel("Woodcutting", 5);
        citizenManager.addCitizen(citizen2);
        inventory=new HashMap<>();
        for (int i=0; i<100; i++){
        resourceManager.addResource("wood");}
        actionNum=maxActions;
        boolean continueGame=true;
        while (continueGame){

        String nextCommand= scanner.nextLine();
        int commandCost=cm.runCommand(nextCommand);

        if(commandCost<=-1){
           for (int i=0; i<-1*commandCost; i++) {
           continueGame=advanceDay();
           if(!continueGame){
               System.out.println("Game Over");
               break;
           }
           }
            continue;
        }
            actionNum-=commandCost;

        }

    }
    public boolean advanceDay(){
        System.out.println("Day ended. Resources produced by Buildings:");
        for (String name:getBuildingManager().getBuildingNames()){
            System.out.println(name+":");
            Building building=getBuildingManager().getBuilding(name);
            HashMap<String, String> dailyProduction=building.produceDaily();
            for(int i=0; i<dailyProduction.get("resource").split("/").length; i++){
                HashMap<String, Integer> resourceMetadata=new HashMap<>();
                for(String dataKey:dailyProduction.keySet()){
                    if(dataKey.equals("resource")||dataKey.equals("amount")) continue;
                    resourceMetadata.put(dataKey, Integer.parseInt(dailyProduction.get(dataKey).split("/")[i]));

                }
            getResourceManager().addResources(dailyProduction.get("resource").split("/")[i], Integer.parseInt(dailyProduction.get("amount").split("/")[i]),resourceMetadata);
            System.out.println(dailyProduction.get("resource").split("/")[i]+" x "+dailyProduction.get("amount").split("/")[i]);
            }

        }
        ArrayList<String> toKill=new ArrayList<>();
        for (String name:getCitizenManager().getCitizenNames()){
          Citizen citizen=getCitizenManager().getCitizen(name);
          int hunger= citizen.getHunger();
          hunger+=25;
          for (String resource:getResourceManager().getResources()){
          if(hunger<=0) break;
          if(getResourceManager().checkStatistic(resource, "foodlevel")>0){

              int amountConsumed=(int) Math.min(getResourceManager().checkCount(resource), Math.ceil(((float)hunger)/getResourceManager().checkStatistic(resource, "foodlevel")));

              hunger=Math.max(hunger-(getResourceManager().checkStatistic(resource, "foodlevel")*amountConsumed), 0);

              getResourceManager().useResource(resource, amountConsumed);

          }
        }
          citizen.setHunger(hunger);
          if(hunger>=100){
             toKill.add(name);
          }

        }
        Random r=new Random();
        int viableCitizens=0;
        for(String citizen:getCitizenManager().getCitizenNames()){
            if(getCitizenManager().getCitizen(citizen).getAge()>=13){
                viableCitizens+=1;
            }
        }
        if((viableCitizens-(viableCitizens%2))/Math.max(r.nextInt(viableCitizens+250), 1)>=1){
            System.out.println(getCitizenManager().createRandomCitizen()+" has been born.");
        }
        for (String name:toKill){
            getCitizenManager().killCitizen(name);
            System.out.println("Citizen "+name+"has died");
        }


        currentDay++;
        if(currentDay%365==0){
            for(String name:getCitizenManager().getCitizenNames()){
                getCitizenManager().getCitizen(name).increaseAge(1);
            }
        }
        actionNum=maxActions;
        return getCitizenManager().getCitizenNames().size()>0;

    }
    public static void setManager(GameManager gameManager){
       manager=gameManager;
    }
    public static GameManager getManager() {
        return manager;
    }

    public CommandManager getCommandManager() {
        return cm;
    }

    public CitizenManager getCitizenManager() {
        return citizenManager;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
    public void loseGame(){}

    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
    public void incrementMoney(float amount){
        this.money+=amount;
    }

    public int getRemainingActions() {
        return actionNum;
    }
}
