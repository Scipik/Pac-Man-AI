package edu.ucsc.gameAI.conditions;

import pacman.game.Game;
import edu.ucsc.gameAI.ICondition;

public class PowerPillInGame implements ICondition{
        public PowerPillInGame ()
        {
        }
        
        public boolean test(Game game) 
        {
                return game.getNumberOfActivePowerPills() != 0;
        }
}
