package edu.wsu.model;

public class ModelSingleton {

  private static Model model;

  public static Model getInstance(){
    if (model == null) {
      model = new Model();
    }
    return model;
  }
}
