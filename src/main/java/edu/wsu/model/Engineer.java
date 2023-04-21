package edu.wsu.model;

import edu.wsu.model.Model.Role;

public class Engineer extends Innocent{

    public Engineer(String name){
        super(name);
    }



    @Override
    public void tellRole(){
        hear("You are the engineer!");
    }

    @Override
    public String getNightActionName(){
        return "visit, or ";
    }
    public boolean hasAction(){
        return true;
    }
    @Override
    public String roleString(){
        return "an engineer.";
    }
    @Override
    public void nightHandler(Player acted){
        if(acted == this){
            Model.MAX_TURNS--;//The train will take one less day to reach its destination
            hear("$ days until the train reaches its destination.");
        }
    }
    @Override
    public Role getRole() {
        return Role.ENGINEER;
    }
    @Override
    public String roleName(){ return "Engineer";}
}
