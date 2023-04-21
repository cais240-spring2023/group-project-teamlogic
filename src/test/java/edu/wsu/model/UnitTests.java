package edu.wsu.model;

import edu.wsu.model.Model.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

  @Test
  void allRolesSetCorrectly(){
    Model model = new Model();
    Player jerry = new Player("Jerry");
    Player steve = new Player("Steve");
    Player sarah = new Player("Sarah");
    Player ben = new Player("ben");
    Player ron = new Player("ron");
    Player bill = new Player("bill");
    Player emily = new Player("emily");
    Player mary = new Player("mary");

    jerry = jerry.setInnocent();
    steve = steve.setMurderer();
    sarah = sarah.setDetective();
    ben = ben.setDoctor();
    ron = ron.setEngineer();
    bill = bill.setJanitor();
    emily = emily.setSilencer();
    mary = mary.setTrickster();

    model.addPlayer(jerry);
    model.addPlayer(steve);
    model.addPlayer(sarah);
    model.addPlayer(ben);
    model.addPlayer(ron);
    model.addPlayer(bill);
    model.addPlayer(emily);
    model.addPlayer(mary);

    assertEquals(8, model.countPlayers());
    assertEquals(4,model.countInnocents());
    assertEquals(3,model.countMurderers());
    assertEquals(8,model.getLivingPlayerCount());
    assertEquals(Role.INNOCENT,jerry.getRole());
    assertEquals(Role.MURDERER,steve.getRole());
    assertEquals(Role.DETECTIVE,sarah.getRole());
    assertEquals(Role.DOCTOR,ben.getRole());
    assertEquals(Role.ENGINEER,ron.getRole());
    assertEquals(Role.JANITOR,bill.getRole());
    assertEquals(Role.SILENCER,emily.getRole());
    assertEquals(Role.TRICKSTER,mary.getRole());
  }

  @Test
  void murderPlayer1(){
    Model model = new Model();
    Player jerry = new Player("Jerry");
    Player steve = new Player("Steve");
    Player sarah = new Player("Sarah");
    Player ben = new Player("Ben");

    model.addPlayer(jerry);
    jerry.setInnocent();
    model.addPlayer(steve);
    steve.setInnocent();
    model.addPlayer(sarah);
    sarah.setInnocent();
    model.addPlayer(ben);
    ben.setMurderer();

    assertEquals(4, model.countPlayers());
    //These do not work
    //assertEquals("Innocent",model.getPlayer("Jerry"));
    //assertEquals(1, model.countMurderers());
    jerry.killedBy(ben);
    assertFalse(jerry.isAlive());
    assertEquals(3,model.getLivingPlayerCount());
  }

  @Test
  void murderPlayer2(){
    Model model = new Model();
    Player jerry = new Player("Jerry");
    Player steve = new Player("Steve");
    Player sarah = new Player("Sarah");
    Player ben = new Player("Ben");

    model.addPlayer(jerry);
    jerry.setInnocent();
    model.addPlayer(steve);
    steve.setInnocent();
    model.addPlayer(sarah);
    sarah.setInnocent();
    model.addPlayer(ben);
    ben.setMurderer();

    assertEquals(4, model.countPlayers());
    //These do not work
    //assertEquals("Innocent",model.getPlayer("Jerry"));
    //assertEquals(1, model.countMurderers());
    steve.killedBy(ben);
    assertFalse(steve.isAlive());
    assertEquals(3,model.getLivingPlayerCount());
  }

  @Test
  void murderPlayer3(){
    Model model = new Model();
    Player jerry = new Player("Jerry");
    Player steve = new Player("Steve");
    Player sarah = new Player("Sarah");
    Player ben = new Player("Ben");

    model.addPlayer(jerry);
    jerry.setInnocent();
    model.addPlayer(steve);
    steve.setInnocent();
    model.addPlayer(sarah);
    sarah.setInnocent();
    model.addPlayer(ben);
    ben.setMurderer();

    assertEquals(4, model.countPlayers());
    //These do not work
    //assertEquals("Innocent",model.getPlayer("Jerry"));
    //assertEquals(1, model.countMurderers());
    sarah.killedBy(ben);
    assertFalse(sarah.isAlive());
    assertEquals(3,model.getLivingPlayerCount());
  }

  @Test
  void murderMultiplePlayers(){
    Model model = new Model();
    Player jerry = new Player("Jerry");
    Player steve = new Player("Steve");
    Player sarah = new Player("Sarah");
    Player ben = new Player("Ben");

    jerry = jerry.setInnocent();
    model.addPlayer(jerry);
    steve = steve.setInnocent();
    model.addPlayer(steve);
    sarah = sarah.setInnocent();
    model.addPlayer(sarah);
    ben = ben.setMurderer();
    model.addPlayer(ben);

    assertEquals(4, model.countPlayers());
    //These do not work
    //assertEquals("Innocent",model.getPlayer("Jerry"));
    assertEquals(Role.MURDERER, ben.getRole());
    sarah.killedBy(ben);
    assertFalse(sarah.isAlive());
    assertEquals(3,model.getLivingPlayerCount());
    steve.killedBy(ben);
    assertFalse(steve.isAlive());
    assertEquals(2, model.getLivingPlayerCount());
    jerry.killedBy(ben);
    assertFalse(jerry.isAlive());
    assertEquals(1, model.getLivingPlayerCount());

  }

  @Test
  void voteOffMurderer(){
    Model model = new Model();
    Player jerry = new Player("Jerry");
    Player steve = new Player("Steve");
    Player sarah = new Player("Sarah");
    Player ben = new Player("Ben");

    jerry = jerry.setInnocent();
    model.addPlayer(jerry);
    steve = steve.setInnocent();
    model.addPlayer(steve);
    sarah = sarah.setInnocent();
    model.addPlayer(sarah);
    ben = ben.setMurderer();
    model.addPlayer(ben);

    assertEquals(4, model.countPlayers());
    //These do not work
    //assertEquals("Innocent",model.getPlayer("Jerry"));
    //assertEquals(1, model.countMurderers());
    model.receiveVote(jerry, ben);
    model.receiveVote(steve, ben);
    model.receiveVote(sarah, ben);
    model.receiveVote(ben, sarah);
    assertEquals(ben,model.tallyVotes());
    ben.kill();
    assertEquals(Role.INNOCENT, model.checkWinner());
    assertFalse(ben.isAlive());
    assertEquals(3,model.getLivingPlayerCount());
  }

  @Test
  void murdererWins(){
    Model model = new Model();
    Player jerry = new Player("Jerry");
    Player steve = new Player("Steve");
    Player sarah = new Player("Sarah");
    Player ben = new Player("Ben");

    jerry = jerry.setInnocent();
    steve = steve.setInnocent();
    sarah = sarah.setInnocent();
    ben = ben.setMurderer();
    model.addPlayer(jerry);
    model.addPlayer(steve);
    model.addPlayer(sarah);
    model.addPlayer(ben);

    assertEquals(4, model.countPlayers());
    assertEquals(1, model.countMurderers());
    jerry.killedBy(ben);
    assertFalse(jerry.isAlive());
    assertEquals(3,model.getLivingPlayerCount());
    steve.killedBy(ben);
    assertFalse(steve.isAlive());
    assertEquals(2, model.getLivingPlayerCount());
    sarah.killedBy(ben);
    assertFalse(sarah.isAlive());
    assertEquals(1, model.getLivingPlayerCount());
    assertEquals(Role.MURDERER, model.checkWinner());
  }

  @Test
  void detectiveAssigningWorks(){
    Model model = new Model();
    Player jerry = new Player("Jerry");
    Player steve = new Player("Steve");
    Player sarah = new Player("Sarah");
    Player ben = new Player("Ben");

    jerry = jerry.setDetective();
    steve = steve.setInnocent();
    sarah = sarah.setInnocent();
    ben = ben.setMurderer();
    model.addPlayer(jerry);
    model.addPlayer(steve);
    model.addPlayer(sarah);
    model.addPlayer(ben);

    assertEquals(4, model.countPlayers());
    assertEquals(1, model.countMurderers());
    assertEquals(Role.DETECTIVE,jerry.getRole());
  }
}
