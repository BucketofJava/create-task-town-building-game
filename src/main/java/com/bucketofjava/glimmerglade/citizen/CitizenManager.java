package com.bucketofjava.glimmerglade.citizen;

import com.bucketofjava.glimmerglade.saving.SaveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class CitizenManager {
    public static String[] CitizenNames=new String[]{"John", "Mary", "Vincent", "Adakin", "Nairit", "McNair", "Martin", "James", "Adi", "Henry", "Aleese", "Asterion", "Emil", "PoopyButt", "Parker", "Ashu", "Eric", "Zach", "Artemis", "Daphne", "Grace", "Captain", "Crunch", "Spraul", "Isaac", "Conrad", "Nabeeha", "Joaquim", "Aanchal", "Romeo", "Tony", "Tiffany", "Lewis", "Oliver"};
    private HashMap<String, Citizen> citizens;
    public CitizenManager(SaveData saveData){
    citizens=new HashMap<>();
    }
    public boolean addCitizen(Citizen citizen){
        if(citizens.containsKey(citizen.getName())) return false;
        citizens.put(citizen.getName(), citizen);
        return true;
    }
    public Citizen getCitizen(String name){
        if(!citizens.containsKey(name)) return null;
        return citizens.get(name);
    }
    public boolean killCitizen(String name){
        if(!citizens.containsKey(name)) return false;
        citizens.remove(name);
        return true;
    }
    public Set<String> getCitizenNames(){
        return citizens.keySet();
    }


    public String createRandomCitizen() {

        Random r=new Random();
        String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        boolean continueLoop=true;
        String name="";
        while (continueLoop){
        name="";
        name=name+CitizenNames[r.nextInt(CitizenNames.length)];
        name=name+alphabet.charAt(r.nextInt(26));
        name=name+alphabet.charAt(r.nextInt(26));
        continueLoop=(getCitizen(name)==null);
        }
        addCitizen(new Citizen(name, 1, r.nextInt(364)+1, null));
        return name;
    }
}
