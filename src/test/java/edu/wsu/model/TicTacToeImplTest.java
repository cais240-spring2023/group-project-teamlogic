package edu.wsu.model;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeImplTest {

  @org.junit.jupiter.api.Test
  void getWinner() {
    Player ttt = new Innocent();
    assertEquals("Bertie", ttt.getRole());
  }
}