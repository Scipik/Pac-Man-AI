//package edu.ucsc.gameAI.actions;
//
//import java.util.ArrayList;
//
//import edu.ucsc.gameAI.IAction;
//import edu.ucsc.gameAI.ICondition;
//import edu.ucsc.gameAI.conditions.GhostInRegion;
//import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;
//import pacman.game.Game;
//import pacman.game.Constants.DM;
//import pacman.game.Constants.GHOST;
//import pacman.game.Constants.MOVE;
//
//public class PacRunAwayToPillAction extends GameAction implements IBinaryNode{
//        private int distance;           // Distance from ghost to be scared
//        private int dangerDistance;     // Distance from ghost to take the pill
//        private int pillDistance;       // Distance to be near pill but not take it
//        
//        public PacRunAwayToPillAction() {
//                distance = 40;          
//                dangerDistance = 8;     
//                pillDistance = 2;
//        }
//
//        public PacRunAwayToPillAction(Game g) {
//                super(g);
//                distance = 40;
//                dangerDistance = 8;
//                pillDistance = 2;
//        }
//        public PacRunAwayToPillAction(Game g, int d, int dd, int pd)
//        {
//                super(g);
//                distance = d;
//                dangerDistance = dd;
//                pillDistance = pd;
//        }
//        public PacRunAwayToPillAction(int d, int dd, int pd)
//        {
//                distance = d;
//                dangerDistance = dd;
//                pillDistance = pd;
//        }
//
//        @Override
//        public MOVE getMove() {
//                int MIN_DISTANCE=distance;
//                
//                int current = getGame().getPacmanCurrentNodeIndex();
//                
//                int closestPPillNode = getGame().getClosestNodeIndexFromNodeIndex(current,getGame().getActivePowerPillsIndices(),DM.PATH);
//                int closestPPill = getGame().getShortestPathDistance(current,closestPPillNode);
//                
//                int nodes[] = getGame().getPillIndices();
//                int nodesDanger[] = new int[nodes.length];
//                
//                for(GHOST ghost : GHOST.values()) 
//                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0) 
//                                for(int i = 0; i < nodes.length; i++) 
//                                        nodesDanger[i] = nodesDanger[i] + getGame().getShortestPathDistance(nodes[i],getGame().getGhostCurrentNodeIndex(ghost));
//                
//                
//                // Looks for the ghost closest to us
//                int minDistance=Integer.MAX_VALUE;
//                GHOST minGhost=null;            
//                
//                for(GHOST ghost : GHOST.values())
//                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0)
//                        {
//                                int distance=getGame().getShortestPathDistance(current,getGame().getGhostCurrentNodeIndex(ghost));
//                                
//                                if(distance<minDistance)
//                                {
//                                        minDistance=distance;
//                                        minGhost=ghost;
//                                }
//                        }
//                
//                int currentGNode = getGame().getGhostCurrentNodeIndex(minGhost);
//                int shortestPathToG = getGame().getShortestPathDistance(current,currentGNode);
//                
//                if(shortestPathToG < dangerDistance || closestPPill > pillDistance) {
//                        if (ghostRegionBuffer(getGame(),current,closestPPillNode) || getGame().getShortestPathDistance(closestPPill, currentGNode) > getGame().getShortestPathDistance(current, closestPPill)) {
//                                int max = Integer.MIN_VALUE;
//                                int safestNode = 0;
//                                
//                                for(int i = 0; i < nodes.length; i++) {
//                                        if(nodesDanger[i] > max && getGame().getShortestPathDistance(current, nodes[i]) < MIN_DISTANCE) {
//                                                max = nodesDanger[i];
//                                                safestNode = nodes[i];
//                                        }
//                                }
//                                
//                                System.out.println("Safest Node");
//                                return getGame().getNextMoveTowardsTarget(current,safestNode,DM.PATH);
//                        } else {
//                                System.out.println("Closest PIll");
//                                return getGame().getNextMoveTowardsTarget(current,closestPPillNode,DM.PATH);
//                        }
//                } else {
//                        System.out.println("Camping");
//                        return getGame().getNextMoveAwayFromTarget(current,closestPPillNode,DM.PATH);
//                }
//        }
//                
//        private boolean ghostRegionBuffer(Game game, int current, int pPill) {
//                        
//                int x1 = game.getNodeXCood(current);
//                int y1 = game.getNodeYCood(current);
//                int x2 = game.getNodeXCood(pPill);
//                int y2 = game.getNodeYCood(pPill);
//                           
//                final int bufferValue = 2;
//           
//                if (x1 <= x2) {
//                        x1 = x1 - bufferValue;;
//                                  x2 = x2 + bufferValue;
//                } else {
//                        int temp = x1;
//                        x1 = x2 - bufferValue;
//                        x2 = temp + bufferValue;
//                }
//                
//                if (y1 <= y2) {
//                        y1 = y1 - bufferValue;
//                        y2 = y2 + bufferValue;
//                } else {
//                        int temp = y1;
//                        y1 = y2 - bufferValue;
//                        y2 = temp + bufferValue;
//                }
//                        
//                ICondition test = new GhostInRegion(x1, y1, x2, y2);
//                return test.test(game);
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
import edu.ucsc.gameAI.conditions.GhostInRegion;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class PacRunAwayToPillAction extends GameAction implements IBinaryNode{
        private int distance;           // Distance from ghost to be scared
        private int dangerDistance;     // Distance from ghost to take the pill
        private int pillDistance;       // Distance to be near pill but not take it
        
        public PacRunAwayToPillAction() {
                distance = 40;          
                dangerDistance = 8;     
                pillDistance = 2;
        }

        public PacRunAwayToPillAction(Game g) {
                super(g);
                distance = 40;
                dangerDistance = 8;
                pillDistance = 2;
        }
        public PacRunAwayToPillAction(Game g, int d, int dd, int pd)
        {
                super(g);
                distance = d;
                dangerDistance = dd;
                pillDistance = pd;
        }
        public PacRunAwayToPillAction(int d, int dd, int pd)
        {
                distance = d;
                dangerDistance = dd;
                pillDistance = pd;
        }

        @Override
        public MOVE getMove() {
                
                int MIN_DISTANCE=distance;
                
                int current = getGame().getPacmanCurrentNodeIndex();
                
                int closestPPillNode = getGame().getClosestNodeIndexFromNodeIndex(current,getGame().getActivePowerPillsIndices(),DM.PATH);
                int closestPPill = getGame().getShortestPathDistance(current,closestPPillNode);
                
                int avgGhostX = 0;
                int avgGhostY = 0;
                int ghostInRangeCount = 0;
                
                for(GHOST ghost : GHOST.values())
                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0)
                                if(getGame().getShortestPathDistance(current,getGame().getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE) { 
                                        ghostInRangeCount++;
                                        avgGhostX = avgGhostX + getGame().getNodeXCood(getGame().getGhostCurrentNodeIndex(ghost));
                                        avgGhostY = avgGhostY + getGame().getNodeYCood(getGame().getGhostCurrentNodeIndex(ghost));
                                }
                
                for(GHOST ghost : GHOST.values()) {
                        int currentGNode = getGame().getGhostCurrentNodeIndex(ghost);
                        int shortestPathToG = getGame().getShortestPathDistance(current,currentGNode);
                        
                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0) {
                                if(shortestPathToG < dangerDistance || closestPPill > pillDistance) {
                                        if (ghostRegionBuffer(getGame(),current,closestPPillNode)) {
                                                
                                                ArrayList<Integer> targets=new ArrayList<Integer>();
                                                
                                                avgGhostX = avgGhostX/ghostInRangeCount;
                                                avgGhostY = avgGhostY/ghostInRangeCount;
                                                
                                                for (int pills:getGame().getPillIndices()) {
                                                        if (getGame().getShortestPathDistance(current, pills)<MIN_DISTANCE) {
                                                                targets.add(pills);
                                                        }
                                                }
                                                
                                                int[] targetsArray=new int[targets.size()];             //convert from ArrayList to array
                                                
                                                for(int i=0;i<targetsArray.length;i++)
                                                        targetsArray[i]=targets.get(i);
                                                
                                                for(int n = 0; n < getGame().getNumberOfNodes(); n++) {
                                                        if (getGame().getNodeXCood(n) == avgGhostX && getGame().getNodeYCood(n) == avgGhostY) {
//                                                                System.out.println("Get Far Node");
                                                                return getGame().getNextMoveTowardsTarget(current, getGame().getFarthestNodeIndexFromNodeIndex(n, targetsArray, DM.PATH), DM.PATH);
                                                                
                                                        }
                                                }
                                        } else {
//                                                System.out.println("Get Pill");
                                                return getGame().getNextMoveTowardsTarget(current,closestPPillNode,DM.PATH);
                                        }
                                } else {
//                                        System.out.println("Camp");
                                        return getGame().getNextMoveAwayFromTarget(current,closestPPillNode,DM.PATH);
                                }
                        }
                }
//                System.out.println("Default");
                return getGame().getApproximateNextMoveTowardsTarget(current,closestPPillNode,getGame().getPacmanLastMoveMade(),DM.PATH);
        }
                
        private boolean ghostRegionBuffer(Game game, int current, int pPill) {
                        
                int x1 = game.getNodeXCood(current);
                int y1 = game.getNodeYCood(current);
                int x2 = game.getNodeXCood(pPill);
                int y2 = game.getNodeYCood(pPill);
                
                final int bufferValue = 3;
                        
                if (x1 <= x2) {
                        x1 = x1 - bufferValue;;
                        x2 = x2 + bufferValue;
                } else {
                        int temp = x1;
                        x1 = x2 - bufferValue;
                        x2 = temp + bufferValue;
                }
                        
                if (y1 <= y2) {
                        y1 = y1 - bufferValue;
                        y2 = y2 + bufferValue;
                } else {
                        int temp = y1;
                        y1 = y2 - bufferValue;
                        y2 = temp + bufferValue;
                }
                        
                ICondition test = new GhostInRegion(x1, y1, x2, y2);
                return test.test(game);
        }

        @Override
        public IAction makeDecision(Game game) {
                setGame(game);
                return this;
        }
}
