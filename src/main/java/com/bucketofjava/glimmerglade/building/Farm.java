package com.bucketofjava.glimmerglade.building;

import com.bucketofjava.glimmerglade.GameManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Farm extends Building{

    public Farm(int level) {
        super(new HashMap<String, Integer>(){{put("wood", 13);}});
        this.level=level;
    }


    @Override
    public Building createNew() {
        return new Farm(0);
    }

    @Override
    public HashMap<String, String> produceDaily() {
        HashMap<String, String> dailyProduction=new HashMap<String, String>();
        dailyProduction.put("resource", "wheat");

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
            //float agemodifier=1.0F/(Math.min(citizenAge-(citizenAge%10), 1)+(citizenAge%10));

            amount+=Math.floor(agemodifier*Math.max(Math.floor((level+1)*(GameManager.getManager().getCitizenManager().getCitizen(citizen).getSkillLevel("farming")/10.0)), level+1));
            Random r=new Random();

            GameManager.getManager().getCitizenManager().getCitizen(citizen).addSkillLevel("farming", (int) (Math.floor((r.nextInt(13)*agemodifier)/12.0)));
        }
        for(String citizen:deadCitizens){
            unassignCitizen(citizen);
        }

        dailyProduction.put("amount", String.valueOf(amount));
        dailyProduction.put("foodlevel", "14");

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
