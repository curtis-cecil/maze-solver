import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;


public class Cell extends JPanel{
    private boolean visited;
    private int height;
    private int width;
    private int rowID;
    private int colID;
    private boolean west = true;
    private boolean south = true;
    private boolean east = true;
    private boolean north = true;
    private Graphics2D g2;

    public Cell(){
        super();

        visited = false;
        height = 10;
        width = 10;

        this.setPreferredSize(new Dimension(height, width));
    }

    public Cell(int targetHeight, int targetWidth, boolean targetVisited){
        super();

        visited = targetVisited;
        height = targetHeight;
        width = targetWidth;

        this.setPreferredSize(new Dimension(height, width));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g2 = (Graphics2D) g;

        if(west){
            g2.draw(new Line2D.Double(0, 0, 0, 10));
        }
        if(south){
            g2.draw(new Line2D.Double(0, 10, 10, 10));
        }
        if(east){
            g2.draw(new Line2D.Double(10, 10, 10, 0));
        }
        if(north){
            g2.draw(new Line2D.Double(0, 0, 10, 0));
        }
    }

    public void fill(Color color){
        setOpaque(true);
        setBackground(color);
        repaint();
    }

    public void breakWall(String targetWall){
        if(targetWall == "west"){
            west = false;
            repaint();
        }
        else if(targetWall == "south"){
            south = false;
            repaint();
        }
        else if(targetWall == "east"){
            east = false;
            repaint();
        }
        else if(targetWall == "north"){
            north = false;
            repaint();
        }
    }

    public boolean wallPresent(String targetWall){
        if(targetWall == "north"){
            return north;
        }
        else if(targetWall == "south"){
            return south;
        }
        else if(targetWall == "west"){
            return west;
        }
        else if(targetWall == "east"){
            return east;
        }
        else{
            return false;
        }
    }

    public void setRowID(int targetID){
        rowID = targetID;
    }

    public int getRowID(){
        return rowID;
    }

    public void setColID(int targetID){
        colID = targetID;
    }

    public int getColID(){
        return colID;
    }

    public void setSize(int newHeight, int newWidth){
        height = newHeight;
        width = newWidth;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public void setVisited(boolean visitedStatus){
        visited = visitedStatus;
    }

    public boolean isVisited(){
        return visited;
    }
}
