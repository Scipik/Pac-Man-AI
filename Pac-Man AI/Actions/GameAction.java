package edu.ucsc.gameAI.actions;

import pacman.game.Constants.MOVE;
import edu.ucsc.gameAI.IAction;
import pacman.game.Game;

public class GameAction implements IAction {
        private Game game;
        
        public GameAction() {
                // TODO Auto-generated constructor stub
        }
        public GameAction(Game g)
        {
                game = g;
        }
        
        public Game getGame()
        {
                return game;
        }
        public void setGame(Game g)
        {
                game = g;
        }

        @Override
        public void doAction() {
                // TODO Auto-generated method stub

        }

        @Override
        public MOVE getMove() {
                // TODO Auto-generated method stub
                return null;
        }

}
