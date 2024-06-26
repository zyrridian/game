package com.example.ai;

import java.util.ArrayList;

import com.example.common.GamePanel;

public class PathFinder {
    
    GamePanel gamePanel;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        instantiateNodes();
    }

    public void instantiateNodes() {
        nodes = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            nodes[col][row] = new Node(col, row);
            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {
        int col = 0;
        int row = 0;
        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            // Reset open, checked and solid state
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;
            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        // Set start and goal node
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
            // Set solid node
            // Check tiles
            int tileNumber = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][col][row];
            if (gamePanel.tileManager.tile[tileNumber].collision) {
                nodes[col][row].solid = true;
            }

            // Check interactive tiles
            for (int i = 0; i < gamePanel.iTile[1].length; i++) {
                if (gamePanel.iTile[gamePanel.currentMap][i] != null && gamePanel.iTile[gamePanel.currentMap][i].destructible) {
                    int itemCol = gamePanel.iTile[gamePanel.currentMap][i].worldX / gamePanel.tileSize;
                    int itemRow = gamePanel.iTile[gamePanel.currentMap][i].worldY / gamePanel.tileSize;
                    nodes[itemCol][itemRow].solid = true;
                }
            }

            // Set cost 
            getCost(nodes[col][row]);

            col++;
            if (col == gamePanel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node) {
        
        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);

        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F cost
        node.fCost = node.gCost + node.hCost;

    }

    public boolean search() {

        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // Check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the Up node
            if (row - 1 >= 0) {
                openNode(nodes[col][row - 1]);
            }

            // Open the left node
            if (col - 1 >= 0) {
                openNode(nodes[col - 1][row]);
            }

            // Open the down node
            if (row + 1 < gamePanel.maxWorldRow) {
                openNode(nodes[col][row + 1]);
            }

            // col the right node
            if (col + 1 < gamePanel.maxWorldCol) {
                openNode(nodes[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                // Check if this node's F cost is better
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // If F cost is equal, check the G cost
                else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            // If there is no node in the openList, end the loop
            if (openList.size() == 0) {
                break;
            }

            // After the loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }

            step++;
            
        }

        return goalReached;

    }

    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }

}
