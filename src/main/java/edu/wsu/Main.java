package edu.wsu;

import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;

public class  Main {
    public static void main(String args[]){
        Model m = ModelSingleton.getInstance();
        m.gameLoop();
    }
}
