import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;
import java.util.Stack;

public class Board extends JPanel {
    private Cell cells[][];
    private int rows;
    private int cols;
    private Stack solveStack = new Stack();
    private Cell nextCell;

    public Board(){
        super();

        rows = 10;
        cols = 10;

        this.setLayout(new GridLayout(rows, cols));

        cells = new Cell[rows][cols];

        for(int currentRow = 0; currentRow < rows; currentRow++){
            for(int currentCol = 0; currentCol < cols; currentCol++){
                cells[currentRow][currentCol] = new Cell();
                this.add(cells[currentRow][currentCol]);
            }
        }
    }

    public Board(int targetRows, int targetCols){
        super();

        rows = targetRows;
        cols = targetCols;

        this.setLayout(new GridLayout(rows, cols));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cells = new Cell[rows][cols];

        for(int currentRow = 0; currentRow < rows; currentRow++){
            for(int currentCol = 0; currentCol < cols; currentCol++){
                cells[currentRow][currentCol] = new Cell();
                cells[currentRow][currentCol].setRowID(currentRow);
                cells[currentRow][currentCol].setColID(currentCol);
                this.add(cells[currentRow][currentCol]);
            }
        }
    }

    public void generate(int currentRow, int currentCol){
        Stack stack = new Stack();
        cells[currentRow][currentCol].setVisited(true);
        Cell currentCell = cells[currentRow][currentCol];
        stack.push(currentCell);

        while(unvisitedNeighbors(currentRow, currentCol) && !cells[rows-1][cols-1].isVisited()){
            Random random = new Random();
            int direction = random.nextInt(4);

            //above
            if(direction == 0){
                if(currentRow != 0){
                    if(!cells[currentRow-1][currentCol].isVisited()){
                        cells[currentRow][currentCol].breakWall("north");
                        cells[currentRow-1][currentCol].breakWall("south");
                        generate(currentRow-1,currentCol);
                    }
                }
            }

            //to the left
            else if(direction == 1){
                if(currentCol != 0){
                    if(!cells[currentRow][currentCol-1].isVisited()){
                        cells[currentRow][currentCol].breakWall("west");
                        cells[currentRow][currentCol-1].breakWall("east");
                        generate(currentRow,currentCol-1);
                    }
                }
            }

            //below
            else if(direction == 2){
                if(currentRow != rows-1){
                    if(!cells[currentRow+1][currentCol].isVisited()){
                        cells[currentRow][currentCol].breakWall("south");
                        cells[currentRow+1][currentCol].breakWall("north");
                        generate(currentRow+1,currentCol);
                    }
                }
            }

            //to the right
            else if(direction == 3){
                if(currentCol != cols-1){
                    if(!cells[currentRow][currentCol+1].isVisited()){
                        cells[currentRow][currentCol].breakWall("east");
                        cells[currentRow][currentCol+1].breakWall("west");
                        generate(currentRow,currentCol+1);
                    }
                }
            }
        }

        if(!stack.empty()) {
            stack.pop();
            if(!stack.empty()) {
                currentCell = (Cell) stack.peek();
                currentRow = currentCell.getRowID();
                currentCol = currentCell.getColID();
                while (!unvisitedNeighbors(currentRow, currentCol) && !stack.empty()) {
                    stack.pop();
                    currentCell = (Cell) stack.peek();
                    currentRow = currentCell.getRowID();
                    currentCol = currentCell.getColID();
                }
            }
        }

        while (unvisitedNeighbors(currentRow, currentCol)) {
            Random random = new Random();
            int direction = random.nextInt(4);

            //above
            if (direction == 0) {
                if (currentRow != 0) {
                    if (!cells[currentRow - 1][currentCol].isVisited()) {
                        cells[currentRow][currentCol].breakWall("north");
                        cells[currentRow - 1][currentCol].breakWall("south");
                        generate(currentRow - 1, currentCol);
                    }
                }
            }

            //to the left
            else if (direction == 1) {
                if (currentCol != 0) {
                    if (!cells[currentRow][currentCol - 1].isVisited()) {
                        cells[currentRow][currentCol].breakWall("west");
                        cells[currentRow][currentCol - 1].breakWall("east");
                        generate(currentRow, currentCol - 1);
                    }
                }
            }

            //below
            else if (direction == 2) {
                if (currentRow != rows - 1) {
                    if (!cells[currentRow + 1][currentCol].isVisited()) {
                        cells[currentRow][currentCol].breakWall("south");
                        cells[currentRow + 1][currentCol].breakWall("north");
                        generate(currentRow + 1, currentCol);
                    }
                }
            }

            //to the right
            else if (direction == 3) {
                if (currentCol != cols - 1) {
                    if (!cells[currentRow][currentCol + 1].isVisited()) {
                        cells[currentRow][currentCol].breakWall("east");
                        cells[currentRow][currentCol + 1].breakWall("west");
                        generate(currentRow, currentCol + 1);
                    }
                }
            }
        }
    }

    public void setAllVisited(boolean status){
        for(int currentRow = 0; currentRow < rows; currentRow++){
            for(int currentCol = 0; currentCol < cols; currentCol++){
                cells[currentRow][currentCol].setVisited(status);
            }
        }
    }

    public void solve(int currentRow, int currentCol) {
        if (cells[rows - 1][cols - 1].isVisited()) {
            return;
        }

        cells[currentRow][currentCol].setVisited(true);
        cells[currentRow][currentCol].fill(Color.BLUE);
        nextCell = cells[currentRow][currentCol];
        //solveStack.push(nextCell);

        for (int currentNeighbor = 0; currentNeighbor < totalNeighbors(currentRow, currentCol); currentNeighbor++) {
            //above
            if (currentRow != 0) {
                if(!cells[currentRow][currentCol].wallPresent("north")) {
                    if (!cells[currentRow - 1][currentCol].isVisited()) {
                        solve(currentRow - 1, currentCol);
                    }
                }
            }

            //to the left
            if (currentCol != 0) {
                if(!cells[currentRow][currentCol].wallPresent("west")) {
                    if (!cells[currentRow][currentCol - 1].isVisited()) {
                        solve(currentRow, currentCol - 1);
                    }
                }
            }

            //below
            if (currentRow != rows - 1) {
                if(!cells[currentRow][currentCol].wallPresent("south")) {
                    if (!cells[currentRow + 1][currentCol].isVisited()) {
                        solve(currentRow + 1, currentCol);
                    }
                }
            }

            //to the right
            if (currentCol != cols - 1) {
                if(!cells[currentRow][currentCol].wallPresent("east")) {
                    if (!cells[currentRow][currentCol + 1].isVisited()) {
                        solve(currentRow, currentCol + 1);
                    }
                }
            }
        }
    }

    public boolean unvisitedNeighbors(int cellRow, int cellCol){
        if(cellRow != 0){
            if(!cells[cellRow-1][cellCol].isVisited()){
                return true;
            }
        }
        if(cellRow != rows-1){
            if(!cells[cellRow+1][cellCol].isVisited()){
                return true;
            }
        }
        if(cellCol != 0){
            if(!cells[cellRow][cellCol-1].isVisited()){
                return true;
            }
        }
        if(cellCol != cols-1){
            if(!cells[cellRow][cellCol+1].isVisited()){
                return true;
            }
        }

        return false;
    }

    public int totalNeighbors(int cellRow, int cellCol){
        int result = 4;

        if(cells[cellRow][cellCol].wallPresent("north")){
            result--;
        }
        if(cells[cellRow][cellCol].wallPresent("south")){
            result--;
        }
        if(cells[cellRow][cellCol].wallPresent("west")){
            result--;
        }
        if(cells[cellRow][cellCol].wallPresent("east")){
            result--;
        }

        return result;
    }
}
