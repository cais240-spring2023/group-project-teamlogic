package edu.wsu.model;

import edu.wsu.model.Model.Role;

public class Doctor extends Innocent {
    public Doctor(String name){
        super(name);
    }



    @Override
    public void tellRole(){
        hear("You are a doctor, not a murderer.");
    }

    @Override
    public String getNightActionName(){
        return "heal, or ";
    }
    public boolean hasAction(){
        return true;
    }
    @Override
    public String roleString(){
        return "a doctor.";
    }
    public static String healString(){ return "You were attacked, but nursed back to health by a doctor!";}
    @Override
    public void nightHandler(Player acted){
        if(acted.justDied()){
            acted.revive();//They didn't actually die, they got healed by the doctor lol
            acted.hear(Doctor.healString());
        }
        acted.setImmune();
    }

    @Override
    public Role getRole() {
        return Role.DOCTOR;
    }
}
