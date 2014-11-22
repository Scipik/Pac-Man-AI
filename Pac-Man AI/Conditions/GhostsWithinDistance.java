package edu.ucsc.gameAI.conditions;

import pacman.game.Game;
import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.*;

public class GhostsWithinDistance implements ICondition {
        private int distance;

        public GhostsWithinDistance()
        {
                distance = 20;
        }
        public GhostsWithinDistance(int d) {
                distance = d;
        }

        @Override
        public boolean test(Game game) {
                int current=game.getPacmanCurrentNodeIndex();
                
                for(GHOST ghost : GHOST.values())
                {
                        if(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
                                if(game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost))<distance)
                                        return true;
                }
                return false;
        }

}
