package edu.wsu.model;

import java.util.*;

public class Players {
    public String role;
    public String playername;
    public List<String> actionOption;
    public Boolean alive;

    public Players(String role, String playername) {
        this.role = role;

        this.playername = playername;

        this.actionOption = new ArrayList<>();

        this.alive = true;

        if (role.equals("innocent")){
            actionOption.add("vote");
            actionOption.add("skip");
        }
        if (role.equals("murderer")){
            actionOption.add("vote");
            actionOption.add("skip");
            actionOption.add("murder");
        }
        if (role.equals("cop")){
            actionOption.add("vote");
            actionOption.add("skip");
            actionOption.add("arrest");
        }
    }
}
