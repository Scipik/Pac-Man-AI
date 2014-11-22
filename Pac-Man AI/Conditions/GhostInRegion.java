package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class GhostInRegion implements ICondition {
        
        GHOST gghost;
        int xx1, yy1, xx2, yy2;
        
        
        /**
         * Checks if ghosts are in a specified region inclusive
         * @param x1,y1 should be coord of first object
         * @param x2,y2 should be coord of second object
         */
        public GhostInRegion (int x1, int y1, int x2, int y2)
        {
                if (x1 < x2) {
                        xx1 = x1;
                        xx2 = x2;
                } else {
                        xx1 = x2;
                        xx2 = x1;        
                }
                
                if (y1 < y2) {
                        yy1 = y1;
                        yy2 = y2;
                } else {
                        yy1 = y2;
                        yy2 = y1;
                }
        }
        
        public boolean test(Game game) 
        {
                for(GHOST ghost : GHOST.values())
                {
                        int mx = game.getNodeXCood(game.getGhostCurrentNodeIndex(ghost));
                        int my = game.getNodeYCood(game.getGhostCurrentNodeIndex(ghost));
                        
                        if (xx1 <= mx && mx <= xx2 && yy1 <= my && my <= yy2 && game.getGhostEdibleTime(ghost)==0)
                                return true;
                                
                }
                return false;
        }
}
