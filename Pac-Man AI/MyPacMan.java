package pacman.entries.pacman;

import java.util.ArrayList;

import pacman.controllers.Controller;
import pacman.game.Game;
import static pacman.game.Constants.MOVE;
import edu.ucsc.gameAI.decisionTrees.binary.*;
import edu.ucsc.gameAI.actions.*;
import edu.ucsc.gameAI.conditions.*;


/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
        private BinaryDecision root;
        
        private static final int MIN_GHOST_DISTANCE=50;       // If a ghost is this close, be cautious
        private static final int TAKE_THE_PILL=8;       // If ghost is this close eat the pill
        private static final int MIN_POWER_PILL_DISTANCE=2;     // Distance needed to get close to a pill but not eat it
	
        // Default constructor
        public MyPacMan()
        {
                root = null;
        }
        
        // Main constructor
        public MyPacMan(Game g)
        {
                initTree(g);
        }
        
        private void initTree(Game g)
        {
                int[] pPills = g.getPowerPillIndices();
                
                root = new BinaryDecision();
                BinaryDecision nRun = new BinaryDecision();
                BinaryDecision run = new BinaryDecision();
                
                root.setCondition(new GhostsWithinDistance(MIN_GHOST_DISTANCE));
                root.setFalseBranch(nRun);
                root.setTrueBranch(run);
                
                // Check proximity to pill and ghost
                BinaryDecision toTakeOrNot = new BinaryDecision();
                
                run.setCondition(new PowerPillInGame());
                run.setFalseBranch(new PacRunAwayAction(g,MIN_GHOST_DISTANCE));
                run.setTrueBranch(toTakeOrNot);
                
                toTakeOrNot.setCondition(new IsItLunchTime());
                toTakeOrNot.setFalseBranch(new PacRunAwayToPillAction(g,MIN_GHOST_DISTANCE, TAKE_THE_PILL, MIN_POWER_PILL_DISTANCE));
                toTakeOrNot.setTrueBranch(new PacEatClosestPowerPillAction(g));
                
                // If theres power pills in game, save them for ghosts
                BinaryDecision eatPills = new BinaryDecision();
                
                nRun.setCondition(new GhostsEdible());
                nRun.setFalseBranch(eatPills);
                nRun.setTrueBranch(new PacEatGhostsAction(g));
                
                // With power pills in game, go for pills according to region
                BinaryDecision selectiveEating = new BinaryDecision();
                
                eatPills.setCondition(new PowerPillInGame());
                eatPills.setFalseBranch(new PacEatClosestAction(g));
                eatPills.setTrueBranch(selectiveEating);
                
                // Find middle region and if pacman is there, focus on pills inside, else get closest
                selectiveEating.setCondition(new PacmanInRegion(g.getNodeXCood(pPills[0]), g.getNodeYCood(pPills[0]), g.getNodeXCood(pPills[3]), g.getNodeYCood(pPills[3])));
                selectiveEating.setFalseBranch(new PacEatClosestAction(g));
                selectiveEating.setTrueBranch(new PacEatAvoidPowerAction(g));
                
//                eatPills.setCondition(new PowerPillInGame());
//                eatPills.setFalseBranch(selectiveEating);
//                eatPills.setTrueBranch(new PacEatAvoidPowerAction(g));
//                
//                // Find middle region and if pacman is there, focus on pills inside, else get closest
//                selectiveEating.setCondition(new PacmanInRegion(g.getNodeXCood(pPills[0]), g.getNodeYCood(pPills[0]), g.getNodeXCood(pPills[3]), g.getNodeYCood(pPills[3])));
//                eatPills.setFalseBranch(new PacEatClosestAction(g));
//                eatPills.setTrueBranch(new PacEatAvoidPowerAction(g));
        }
        
        // Gets pacmans move
	public MOVE getMove(Game game, long timeDue) 
	{
	        // If root is null, create it
		if (root == null)
		        initTree(game);
		
		return root.makeDecision(game).getMove();
	}
}