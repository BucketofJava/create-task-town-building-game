package com.bucketofjava.glimmerglade.building;

import com.bucketofjava.glimmerglade.GameManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Woodlodge extends Building{

    public Woodlodge(int level){
        super((new HashMap<String, Integer>(){{put("wood", 40); put("wheat", 40);}}));
        this.level=level;


    }
    @Override
    public Building createNew() {
        return new Woodlodge(0);
    }

    @Override
    public HashMap<String, String> produceDaily() {
        HashMap<String, String> dailyProduction=new HashMap<String, String>();
        dailyProduction.put("resource", "wood");

        int amount=0;
        ArrayList<String> deadCitizens=new ArrayList<>();

        for (String citizen:this.citizens){
            if(!GameManager.getManager().getCitizenManager().getCitizenNames().contains(citizen)){
                deadCitizens.add(citizen);
                System.out.println("Citizen not found");
                continue;
            }
            int citizenAge=GameManager.getManager().getCitizenManager().getCitizen(citizen).getAge();
            int agemodifier=1;
            if(citizenAge<10){
                agemodifier= (int) Math.pow((1.0F/(10-citizenAge)), 2);
            }
            amount+=Math.floor(agemodifier*Math.max(Math.floor((level+1)*(GameManager.getManager().getCitizenManager().getCitizen(citizen).getSkillLevel("woodcutting")/10)), level+1));
            Random r=new Random();
            GameManager.getManager().getCitizenManager().getCitizen(citizen).addSkillLevel("woodcutting", (int) (Math.floor((r.nextInt(6)*agemodifier)/5.0)));
        }
        for(String citizen:deadCitizens){
            unassignCitizen(citizen);
        }

        dailyProduction.put("amount", String.valueOf(amount));


//        ArrayList<String> dailyProduction=new ArrayList<>();
//        for (int i=0; i<13*(level+1); i++){
//            dailyProduction.add("wheat");
//        }
        return dailyProduction;
    }

    @Override
    public void upgrade() {
        level++;
    }
}
