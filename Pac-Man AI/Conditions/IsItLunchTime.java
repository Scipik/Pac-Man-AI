package edu.ucsc.gameAI.conditions;

import pacman.game.Game;
import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.*;

public class IsItLunchTime implements ICondition{
        private int dangerDistance;
        
        public IsItLunchTime()
        {
                dangerDistance = 8;
        }
        public IsItLunchTime(int d) {
                dangerDistance = d;
        }
        
        @Override
        public boolean test(Game game) {
                int current=game.getPacmanCurrentNodeIndex();
                
                int closestPPillNode = game.getClosestNodeIndexFromNodeIndex(current,game.getActivePowerPillsIndices(),DM.PATH);
                int closestPPill = game.getShortestPathDistance(current,closestPPillNode);
                
                for(GHOST ghost : GHOST.values())
                {
                        int ghostNode = game.getGhostCurrentNodeIndex(ghost);
                        if(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
                                if(game.getShortestPathDistance(current, ghostNode) < dangerDistance && closestPPill < dangerDistance) {
                                        return true;
                                }
                }
                return false;
        }
}
