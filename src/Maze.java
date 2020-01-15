import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Component.CENTER_ALIGNMENT;

public class Maze {
    private JFrame frame;
    private JPanel mainPanel, optionsPanel;
    private JButton generateButton, solveButton, stopButton;
    private JLabel speedLabel, rowsLabel, colsLabel;
    private JSlider speedSlider, rowsSlider, colsSlider;
    private Board board;

    private int minBoardRows = 10;
    private int minBoardCols = 10;
    private int maxBoardRows = 60;
    private int maxBoardCols = 60;
    private int boardRows = 10;
    private int boardCols = 10;
    private int minSpeed = 1;
    private int maxSpeed = 100;
    private int rowsSliderLoc = minBoardRows;
    private int colsSliderLoc = minBoardCols;

    static public void main(String[] args){
        new Maze();
    }

    public Maze(){
        createGUI();
    }

    private void createGUI(){
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        frame = new JFrame("Maze");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        frame.add(mainPanel);

        board = new Board(boardRows, boardCols);
        mainPanel.add(board);

        addOptions();

        frame.pack();
        frame.setVisible(true);
    }

    private void addOptions(){
        //options panel
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(optionsPanel);

        //generate button
        generateButton = new JButton("Generate");
        generateButton.setAlignmentX(CENTER_ALIGNMENT);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPanel.removeAll();
                board = new Board(boardRows, boardCols);
                mainPanel.add(board);
                addOptions();
                mainPanel.revalidate();
                board.generate(0,0);
                board.setAllVisited(false);
                frame.pack();
            }
        });
        optionsPanel.add(generateButton);

        optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));

        //solve button
        solveButton = new JButton("Solve");
        solveButton.setAlignmentX(CENTER_ALIGNMENT);
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                board.solve(0,0);
            }
        });
        optionsPanel.add(solveButton);

        optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));

        //speed label
        speedLabel = new JLabel("Speed:");
        speedLabel.setAlignmentX(CENTER_ALIGNMENT);
        optionsPanel.add(speedLabel);

        //speed slider
        speedSlider = new JSlider(JSlider.HORIZONTAL, minSpeed, maxSpeed, maxSpeed);
        speedSlider.setAlignmentX(CENTER_ALIGNMENT);
        optionsPanel.add(speedSlider);

        optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));

        //rows label
        rowsLabel = new JLabel("Rows: " + boardRows);
        rowsLabel.setAlignmentX(CENTER_ALIGNMENT);
        optionsPanel.add(rowsLabel);

        //rows slider
        rowsSlider = new JSlider(JSlider.HORIZONTAL, minBoardRows, maxBoardRows, rowsSliderLoc);
        rowsSlider.setAlignmentX(CENTER_ALIGNMENT);
        rowsSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (!source.getValueIsAdjusting()) {
                    rowsSliderLoc = source.getValue();
                    boardRows = source.getValue();
                }
            }
        });
        optionsPanel.add(rowsSlider);

        optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));

        //cols label
        colsLabel = new JLabel("Columns: " + boardCols);
        colsLabel.setAlignmentX(CENTER_ALIGNMENT);
        optionsPanel.add(colsLabel);

        //cols slider
        colsSlider = new JSlider(JSlider.HORIZONTAL, minBoardCols, maxBoardCols, colsSliderLoc);
        colsSlider.setAlignmentX(CENTER_ALIGNMENT);
        colsSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (!source.getValueIsAdjusting()) {
                    colsSliderLoc = source.getValue();
                    boardCols = source.getValue();
                }
            }
        });
        optionsPanel.add(colsSlider);

        optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));

        //stop button
        stopButton = new JButton("Stop");
        stopButton.setAlignmentX(CENTER_ALIGNMENT);
        optionsPanel.add(stopButton);
    }
}
