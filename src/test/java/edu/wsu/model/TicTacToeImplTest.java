package edu.wsu.model;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeImplTest {

  @org.junit.jupiter.api.Test
  void getWinner() {
    TicTacToe ttt = new TicTacToeImpl();
    assertEquals("Bertie", ttt.getWinner());
  }
}