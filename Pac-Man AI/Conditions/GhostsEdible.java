package edu.ucsc.gameAI.conditions;

import pacman.game.Game;
import pacman.game.Constants.GHOST;
import edu.ucsc.gameAI.ICondition;

public class GhostsEdible implements ICondition {

        public GhostsEdible() {
                // TODO Auto-generated constructor stub
        }

        @Override
        public boolean test(Game game) {
                
                for(GHOST ghost : GHOST.values())
                {
                        if(game.getGhostEdibleTime(ghost)>0)
                                        return true;
                }
                return false;
        }

}
