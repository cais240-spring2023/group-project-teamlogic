package edu.wsu.model;

public class Detective extends Innocent{
    //I thought it would be wise to split these off into different classes
    //rather than handling it all in one class
    //
    //That way, you can just add special functionality in each class rather than having to do
    //special checking within the player class

    public Detective(String name) {
        super(name);
        actions.add("arrest");
    }

    @Override
    public void tellRole(){
        hear("You are a detective.");
    }

    public Player textBasedActivityHandler(Player[] players){
        System.out.println(name + ", select a player to investigate.\n");
        Player selected;
        while(true) {
            selected = textBasedPlayerSelector(players);
            if(selected == null) return null;
            if(selected.isAlive()) return selected;
        }
    }

    @Override
    public String getNightActionName(){
        return "investigate";
    }
    public boolean hasAction(){
        return true;
    }
    @Override
    public String roleString(){
        return "a detective.";
    }
    @Override
    public void nightHandler(Player acted){
        Player p = acted.getVisited();
        String name;
        if(p == null){
            name = "nobody!";
        }
        else{
            name = p.getName();
        }
        hear(acted.getName() + " visited " + name);
    }
}
