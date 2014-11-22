package edu.ucsc.gameAI.actions;

import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class PacEatGhostsAction extends GameAction implements IBinaryNode{
        
        public PacEatGhostsAction() {
        }

        public PacEatGhostsAction(Game g) {
                super(g);
        }

        @Override
        public MOVE getMove()
        {
                int current=getGame().getPacmanCurrentNodeIndex();
                int minDistance=Integer.MAX_VALUE;
                GHOST minGhost=null;            
                
                for(GHOST ghost : GHOST.values())
                        if(getGame().getGhostEdibleTime(ghost)>0)
                        {
                                int distance=getGame().getShortestPathDistance(current,getGame().getGhostCurrentNodeIndex(ghost));
                                
                                if(distance<minDistance)
                                {
                                        minDistance=distance;
                                        minGhost=ghost;
                                }
                        }
                
                if(minGhost!=null)      //we found an edible ghost
                        return getGame().getNextMoveTowardsTarget(getGame().getPacmanCurrentNodeIndex(),getGame().getGhostCurrentNodeIndex(minGhost),DM.PATH);

                return MOVE.NEUTRAL;
        }

        @Override
        public IAction makeDecision(Game game) {
                setGame(game);
                return this;
        }
}
