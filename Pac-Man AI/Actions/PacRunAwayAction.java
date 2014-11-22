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

public class PacRunAwayAction extends GameAction implements IBinaryNode{
        private int distance;      // Distance from ghost to be scared
        
        // Variables used to keep
//        private int gJunctionIndex;
//        private int gJunctionDanger;
        
        
        public PacRunAwayAction() {
                distance = 40;          
        }

        public PacRunAwayAction(Game g) {
                super(g);
                distance = 40;
        }
        public PacRunAwayAction(Game g, int d)
        {
                super(g);
                distance = d;
        }
        public PacRunAwayAction(int d)
        {
                distance = d;
        }

        @Override
        public MOVE getMove() {
                int MIN_DISTANCE=distance;
                int current = getGame().getPacmanCurrentNodeIndex();
                
                int nodes[] = getGame().getPillIndices();
                double nodesDanger[] = new double[nodes.length];
                
                for(GHOST ghost : GHOST.values()) {
                        int ghostIndex = getGame().getGhostCurrentNodeIndex(ghost);
                        double distFromPac = getGame().getShortestPathDistance(current, ghostIndex);
                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0) {
                                for(int i = 0; i < nodes.length; i++) {
                                        nodesDanger[i] = nodesDanger[i] + (double)getGame().getShortestPathDistance(nodes[i],getGame().getGhostCurrentNodeIndex(ghost))/distFromPac;
                                }
                        }
                }
                
                double max = Integer.MIN_VALUE;
                int safestNode = 0;
                
                for(int i = 0; i < nodes.length; i++) {
                        if(nodesDanger[i] > max && getGame().getShortestPathDistance(current, nodes[i]) < MIN_DISTANCE) {
                                max = nodesDanger[i];
                                safestNode = nodes[i];
                        }
                }
                
                return getGame().getNextMoveTowardsTarget(current,safestNode,DM.PATH);
        }

        @Override
        public IAction makeDecision(Game game) {
                setGame(game);
                return this;
        }
}





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
//public class PacRunAwayAction extends GameAction implements IBinaryNode{
//        private int distance;      // Distance from ghost to be scared
//        
//        // Variables used to keep
//        private int gJunctionIndex;
//        private int gJunctionDanger;
//        
//        
//        public PacRunAwayAction() {
//                distance = 40;          
//        }
//
//        public PacRunAwayAction(Game g) {
//                super(g);
//                distance = 40;
//        }
//        public PacRunAwayAction(Game g, int d)
//        {
//                super(g);
//                distance = d;
//        }
//        public PacRunAwayAction(int d)
//        {
//                distance = d;
//        }
//
//        @Override
//        public MOVE getMove() {
//                int MIN_DISTANCE=distance;
//                int current = getGame().getPacmanCurrentNodeIndex();
//                
//                int closestPillNode = getGame().getClosestNodeIndexFromNodeIndex(current,getGame().getActivePillsIndices(),DM.PATH);
////                int closestPill = getGame().getShortestPathDistance(current,closestPillNode);
//                
//                int[] junctionsArray = getGame().getJunctionIndices();
//                int[] junctionDangerArray = new int[junctionsArray.length];
//                
//                
//                for(GHOST ghost : GHOST.values()) {
//                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0) {
//                                junctionDanger(ghost);
//                                for(int i = 0; i < junctionsArray.length; i++) {
//                                        if(junctionsArray[i] == gJunctionIndex) {
//                                                junctionDangerArray[i] = gJunctionDanger;
//                                                break;
//                                        }
//                                }
//                        }
//                }
//                
//                ArrayList<Integer> junctions=new ArrayList<Integer>();
//                ArrayList<Integer> junctionDanger=new ArrayList<Integer>();
//                
//                for(int i = 0; i < junctionsArray.length; i++) {
//                        if (getGame().getShortestPathDistance(current, junctionsArray[i]) < MIN_DISTANCE) {
//                                junctions.add(junctionsArray[i]);
//                                junctionDanger.add(junctionDangerArray[i]);
//                        }
//                }
//                
//                int[] junctionsInRange=new int[junctions.size()];
//                int[] junctionInRangeDanger=new int [junctions.size()];
//                
//                for(int i=0;i<junctionsInRange.length;i++) {
//                        junctionsInRange[i]=junctions.get(i);
//                        junctionInRangeDanger[i]=junctionDanger.get(i);
//                }
//                
//                int minDanger = Integer.MAX_VALUE;
//                int minDangerNode = 0;
//                
//                for (;;) {
//                        for(int i=0;i<junctionsInRange.length;i++) {
//                                if(junctionInRangeDanger[i] < minDanger) {
//                                        minDanger = junctionInRangeDanger[i];
//                                        minDangerNode = junctionsInRange[i];
//                                }
//                                
//                        }
//                        if (ghostRegionBuffer(getGame(), current, minDangerNode)) { 
//                        } else {
//                                break;
//                        }
//                }
//                
//                if (ghostRegionBuffer(getGame(), current, minDangerNode)) {
//                        
//                }
//                
//                System.out.println(minDangerNode);
//                
//                return getGame().getNextMoveTowardsTarget(current,minDangerNode,DM.PATH);
//        }
//                
//        private boolean ghostRegionBuffer(Game game, int current, int node) {
//                        
//                int x1 = game.getNodeXCood(current);
//                int y1 = game.getNodeYCood(current);
//                int x2 = game.getNodeXCood(node);
//                int y2 = game.getNodeYCood(node);
//                        
//                if (x1 == x2) {
//                        x1 = x1 - 2;
//                        x2 = x2 + 2;
//                }
//                        
//                if (y1 == y2) {
//                        y1 = y1 - 2;
//                        y2 = y2 + 2;
//                }
//                        
//                ICondition test = new GhostInRegion(x1, y1, x2, y2);
//                return test.test(game);
//        }
//        
//        private void junctionDanger(GHOST ghost) {
//                int dangerValue = 50;
//                int node = getGame().getGhostCurrentNodeIndex(ghost);
//                int currentNode = node;
//                int neighbor;
//                
//                for(;;) {
//                        if(getGame().isJunction(currentNode)) {
//                                gJunctionIndex = currentNode;
//                                gJunctionDanger = dangerValue;
//                                break;
//                        }
//                        dangerValue--;
//                        
//                        int nodes[] = getGame().getNeighbouringNodes(currentNode, getGame().getGhostLastMoveMade(ghost));
//                        
//                        if (nodes == null) {
//                                nodes = getGame().getNeighbouringNodes(currentNode);
//                        }
//                        
//                        for (int i = 0; i < nodes.length; i++) {
////                                System.out.println(currentNode + ", " + nodes[i]);
//                                neighbor = getGame().getNeighbour(currentNode, getGame().getMoveToMakeToReachDirectNeighbour(currentNode,nodes[i]));
//                                if (neighbor != -1) {
//                                        currentNode = nodes[i];
////                                        System.out.println("It broke out");
//                                        break;
//                                }
//                        }
////                        System.exit(1);
//                }
//        }
//
//        @Override
//        public IAction makeDecision(Game game) {
//                setGame(game);
//                return this;
//        }
//}





















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
//public class PacRunAwayAction extends GameAction implements IBinaryNode{
//        private int distance;           // Distance from ghost to be scared
//        
//        public PacRunAwayAction() {
//                distance = 40;          
//        }
//
//        public PacRunAwayAction(Game g) {
//                super(g);
//                distance = 40;
//        }
//        public PacRunAwayAction(Game g, int d)
//        {
//                super(g);
//                distance = d;
//        }
//        public PacRunAwayAction(int d)
//        {
//                distance = d;
//        }
//
//        @Override
//        public MOVE getMove() {
//                int MIN_DISTANCE=distance;
//                
//                int current = getGame().getPacmanCurrentNodeIndex();
//                
//                int avgGhostX = 0;
//                int avgGhostY = 0;
//                int ghostInRangeCount = 0;
//                
//                int closestPillNode = getGame().getClosestNodeIndexFromNodeIndex(current,getGame().getActivePillsIndices(),DM.PATH);
////                int closestPill = getGame().getShortestPathDistance(current,closestPillNode);
//                
//                for(GHOST ghost : GHOST.values())
//                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0)
//                                if(getGame().getShortestPathDistance(current,getGame().getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE) {
//                                        ghostInRangeCount++;
//                                        avgGhostX = avgGhostX + getGame().getNodeXCood(getGame().getGhostCurrentNodeIndex(ghost));
//                                        avgGhostY = avgGhostY + getGame().getNodeYCood(getGame().getGhostCurrentNodeIndex(ghost));
//                                }
//                
//                for(GHOST ghost : GHOST.values()) {
//                        if(getGame().getGhostEdibleTime(ghost)==0 && getGame().getGhostLairTime(ghost)==0) {
//                                if (ghostRegionBuffer(getGame(),current,closestPillNode)) {
//                                        ArrayList<Integer> targets=new ArrayList<Integer>();
//                                                
//                                        avgGhostX = avgGhostX/ghostInRangeCount;
//                                        avgGhostY = avgGhostY/ghostInRangeCount;
//                                                
//                                        for (int pills:getGame().getPillIndices()) {
//                                                if (getGame().getShortestPathDistance(current, pills)<MIN_DISTANCE) {
//                                                        targets.add(pills);
//                                                }
//                                        }
//                                        
//                                        int[] targetsArray=new int[targets.size()];             //convert from ArrayList to array
//                                        
//                                        for(int i=0;i<targetsArray.length;i++)
//                                                targetsArray[i]=targets.get(i);
//                                        
//                                        for(int n = 0; n < getGame().getNumberOfNodes(); n++) {
//                                                if (getGame().getNodeXCood(n) == avgGhostX && getGame().getNodeYCood(n) == avgGhostY) {
//                                                        return getGame().getNextMoveTowardsTarget(current, getGame().getFarthestNodeIndexFromNodeIndex(n, targetsArray, DM.PATH), DM.PATH);
//                                                }
//                                        }
//                                } else {
//                                        return getGame().getNextMoveTowardsTarget(current,closestPillNode,DM.PATH);
//                                }
//                        }
//                }
//                return getGame().getNextMoveTowardsTarget(current,closestPillNode,DM.PATH);
//        }
//                
//        private boolean ghostRegionBuffer(Game game, int current, int pPill) {
//                        
//                int x1 = game.getNodeXCood(current);
//                int y1 = game.getNodeYCood(current);
//                int x2 = game.getNodeXCood(pPill);
//                int y2 = game.getNodeYCood(pPill);
//                        
//                if (x1 == x2) {
//                        x1 = x1 - 2;
//                        x2 = x2 + 2;
//                }
//                        
//                if (y1 == y2) {
//                        y1 = y1 - 2;
//                        y2 = y2 + 2;
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
