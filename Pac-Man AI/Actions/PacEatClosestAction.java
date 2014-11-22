package edu.ucsc.gameAI.actions;

import java.util.ArrayList;

import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.ICondition;
import edu.ucsc.gameAI.conditions.PacmanInRegion;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class PacEatClosestAction extends GameAction implements IBinaryNode{

        public PacEatClosestAction() {
        }
        
        public PacEatClosestAction(Game g) {
                super(g);
        }
                
        @Override
        public MOVE getMove()
        {
                // With no power pills, just eat the closest pill
                int current = getGame().getPacmanCurrentNodeIndex();
                int closestPill = getGame().getClosestNodeIndexFromNodeIndex(current,getGame().getActivePillsIndices(),DM.PATH);
                return getGame().getNextMoveTowardsTarget(current,closestPill, DM.PATH);

        }

        @Override
        public IAction makeDecision(Game game) {
                setGame(game);
                return this;
        }
}
