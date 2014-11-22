//package edu.ucsc.gameAI.actions;
//
//import java.util.ArrayList;
//
//import edu.ucsc.gameAI.IAction;
//import edu.ucsc.gameAI.ICondition;
//import edu.ucsc.gameAI.conditions.PacmanInRegion;
//import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;
//import pacman.game.Constants.DM;
//import pacman.game.Constants.GHOST;
//import pacman.game.Constants.MOVE;
//import pacman.game.Game;
//
//public class PacEatAvoidPowerAction extends GameAction implements IBinaryNode{
//        public PacEatAvoidPowerAction() {
//        }
//        
//        public PacEatAvoidPowerAction(Game g) {
//                super(g);
//        }
//        
//        @Override
//        public MOVE getMove()
//        {
//                int current = getGame().getPacmanCurrentNodeIndex();
//                
//                int[] pPills = getGame().getPowerPillIndices();
//                
//                int pillx1 = getGame().getNodeXCood(pPills[0]);
//                int pilly1 = getGame().getNodeYCood(pPills[0]);
//                int pillx2 = getGame().getNodeXCood(pPills[3]);
//                int pilly2 = getGame().getNodeYCood(pPills[3]);
//                
//                int nodes[] = getGame().getActivePillsIndices();
//                double nodesDanger[] = new double[nodes.length];
//                
//                for(GHOST ghost : GHOST.values()) {
//                        int ghostIndex = getGame().getGhostCurrentNodeIndex(ghost);
//                        double distFromPac = getGame().getShortestPathDistance(current, ghostIndex);
//                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0) {
//                                for(int i = 0; i < nodes.length; i++) {
//                                        nodesDanger[i] = nodesDanger[i] + (double)getGame().getShortestPathDistance(nodes[i],getGame().getGhostCurrentNodeIndex(ghost))/distFromPac;
//                                }
//                        }
//                }
//                
//                ArrayList<Integer> targets=new ArrayList<Integer>();
//                ArrayList<Double> targetsDanger=new ArrayList<Double>();
//                        
//                for(int i = 0; i < nodes.length; i++) {
//                        int px = getGame().getNodeXCood(i);
//                        int py = getGame().getNodeYCood(i);
//                        
//                        if (pillx1 <= px && px <= pillx2 && pilly1 <= py && py <= pilly2)
//                        {
//                                targets.add(nodes[i]);
//                                targetsDanger.add(nodesDanger[i]);
//                        }
//                }
//                
//                if (targets.size() == 0) {
//                        return getGame().getNextMoveTowardsTarget(current,getGame().getClosestNodeIndexFromNodeIndex(current,getGame().getActivePillsIndices(),DM.PATH),DM.PATH);
//                }
//                
//                int[] targetsArray=new int[targets.size()];             //convert from ArrayList to array
//                double[] targetsDangerArray=new double[targets.size()];
//                        
//                for(int i=0;i<targetsArray.length;i++) {
//                        targetsArray[i]=targets.get(i);
//                        targetsDangerArray[i]=targetsDanger.get(i);
//                }
//                
//                double max = Integer.MIN_VALUE;
//                int safestNode = 0;
//                
//                for(int i = 0; i < targetsArray.length; i++) {
//                        if(targetsDangerArray[i] > max) {
//                                max = targetsDangerArray[i];
//                                safestNode = targetsArray[i];
//                        }
//                }
//                
//                System.out.println("Eating");
//                
//                return getGame().getNextMoveTowardsTarget(current,safestNode,DM.PATH);
//        }
//        
//        @Override
//        public IAction makeDecision(Game game) {
//                setGame(game);
//                return this;
//        }
//}



package edu.ucsc.gameAI.actions;

import java.util.ArrayList;

import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.ICondition;
import edu.ucsc.gameAI.conditions.PacmanInRegion;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class PacEatAvoidPowerAction extends GameAction implements IBinaryNode{
        public PacEatAvoidPowerAction() {
        }
        
        public PacEatAvoidPowerAction(Game g) {
                super(g);
        }
        
        @Override
        public MOVE getMove()
        {
                int current = getGame().getPacmanCurrentNodeIndex();
                
                int[] pPills = getGame().getPowerPillIndices();
                
                ArrayList<Integer> targets=new ArrayList<Integer>();
                        
                for (int ppill : getGame().getActivePillsIndices()) {
                        int px = getGame().getNodeXCood(ppill);
                        int py = getGame().getNodeYCood(ppill);
                        
                        if (getGame().getNodeXCood(pPills[0]) <= px && px <= getGame().getNodeXCood(pPills[3]) && getGame().getNodeYCood(pPills[0]) <= py && py <= getGame().getNodeYCood(pPills[3]))
                        {
                                targets.add(ppill);
                        }
                }
                
                if (targets.size() == 0) {
                        return getGame().getNextMoveTowardsTarget(current,getGame().getClosestNodeIndexFromNodeIndex(current,getGame().getPillIndices(),DM.PATH),DM.PATH);
                }
                
                int[] targetsArray=new int[targets.size()];             //convert from ArrayList to array
                        
                for(int i=0;i<targetsArray.length;i++)
                        targetsArray[i]=targets.get(i);
                
                return getGame().getNextMoveTowardsTarget(current,getGame().getClosestNodeIndexFromNodeIndex(current,targetsArray,DM.PATH),DM.PATH);
        }
        
        @Override
        public IAction makeDecision(Game game) {
                setGame(game);
                return this;
        }
}
