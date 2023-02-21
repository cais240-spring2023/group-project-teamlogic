package edu.wsu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @org.junit.jupiter.api.Test
    void rolesTest(){
        Model model = new Model();
        model.addPlayer(new Player("Daph"));
        model.addPlayer(new Player("Jack"));
        model.addPlayer(new Player("Casey"));
        model.addPlayer(new Player("Lucas"));
        model.addPlayer(new Player("Ivan"));
        model.addPlayer(new Player("Gavin"));
        model.assignRoles();
        assertEquals(5,model.countInnocents());
        assertEquals(1,model.countMurderers());
    }

    @org.junit.jupiter.api.Test
    void killTest(){
        Model model = new Model();
        model.addPlayer(new Murderer("Daph"));
        model.addPlayer(new Innocent("Jack"));
        model.addPlayer(new Innocent("Casey"));
        model.addPlayer(new Innocent("Lucas"));
        model.addPlayer(new Innocent("Ivan"));
        model.addPlayer(new Detective("Gavin"));
        model.nightHandler(model.players[0],model.players[1]);
        model.nightHandler(model.players[5],model.players[0]);
        assertEquals(4,model.countInnocents());
        assertTrue(model.players[0].isAlive());
        assertFalse(model.players[1].isAlive());
        assertTrue(model.players[2].isAlive());
        assertTrue(model.players[3].isAlive());
        assertTrue(model.players[4].isAlive());
        assertTrue(model.players[5].isAlive());
        assertEquals(1,model.countMurderers());
    }

    @org.junit.jupiter.api.Test
    void winTest(){
        Model model = new Model();
        model.addPlayer(new Murderer("Daph"));
        model.addPlayer(new Innocent("Jack"));
        model.addPlayer(new Innocent("Casey"));
        model.addPlayer(new Innocent("Lucas"));
        model.addPlayer(new Innocent("Ivan"));
        model.addPlayer(new Detective("Gavin"));
        model.nightHandler(model.players[0],model.players[1]);
        model.nightHandler(model.players[0],model.players[2]);
        model.nightHandler(model.players[0],model.players[3]);
        model.nightHandler(model.players[0],model.players[4]);
        model.nightHandler(model.players[0],model.players[5]);
        assertEquals(model.checkWinner(),Model.Role.MURDERER);
        model = new Model();
        model.addPlayer(new Murderer("Daph"));
        model.addPlayer(new Innocent("Jack"));
        model.addPlayer(new Innocent("Casey"));
        model.addPlayer(new Innocent("Lucas"));
        model.addPlayer(new Innocent("Ivan"));
        model.addPlayer(new Detective("Gavin"));
        model.players[0].kill();
        assertEquals(model.checkWinner(),Model.Role.INNOCENT);
    }

    @org.junit.jupiter.api.Test
    void voteTest(){
        Model model = new Model();
        model.addPlayer(new Murderer("Daph"));
        model.addPlayer(new Innocent("Jack"));
        model.addPlayer(new Innocent("Casey"));
        model.addPlayer(new Innocent("Lucas"));
        model.addPlayer(new Innocent("Ivan"));
        model.addPlayer(new Detective("Gavin"));
        model.receiveVote(model.players[0],model.players[5]);
        model.receiveVote(model.players[1],model.players[0]);
        model.receiveVote(model.players[2],model.players[0]);
        model.receiveVote(model.players[3],model.players[0]);
        model.receiveVote(model.players[4],model.players[0]);
        model.receiveVote(model.players[5],model.players[0]);
        model.tallyVotes().kill();
        assertEquals(model.checkWinner(),Model.Role.INNOCENT);
    }
}