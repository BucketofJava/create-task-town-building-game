package com.bucketofjava.glimmerglade.citizen;

import java.util.HashMap;

public class Citizen {
    private String name;
    private int age;
    private int birthday;
    private HashMap<String, Integer> skillset;
    private int hunger;
    private int sleepLevel;
    private int funTime;
    private int satisfaction;
    public Citizen(String name, int age, int birthday, RelationMap relationMap) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.skillset = new HashMap<>();
    }
    public void addSkillLevel(String skill, int amount){
        if(!skillset.containsKey(skill)){ skillset.put(skill, amount); return;}
        skillset.put(skill, skillset.get(skill)+amount);
    }
    public int getSkillLevel(String skill){
        return skillset.getOrDefault(skill, 1);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getBirthday() {
        return birthday;
    }

    public int getHunger() {
        return hunger;
    }


    public int getSleepLevel() {
        return sleepLevel;
    }

    public int getFunTime() {
        return funTime;
    }

    public int getSatisfaction() {
        return satisfaction;
    }
    public String[] getAllSkills(){
        return skillset.keySet().toArray(new String[0]);
    }

    public void increaseAge(int amount){this.age+=amount;}
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setSleepLevel(int sleepLevel) {
        this.sleepLevel = sleepLevel;
    }

    public void setFunTime(int funTime) {
        this.funTime = funTime;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }
}
