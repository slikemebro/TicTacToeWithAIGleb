package visual;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;


public abstract class Visual extends WindowProgram implements Constants {
    int score = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    GRect firstLine = new GRect(POSITION_OF_LINE_WIDTH, 0, SIZE_OF_LINE, APPLICATION_WIDTH);
    GRect secondLine = new GRect(POSITION_OF_LINE_WIDTH * 2, 0, SIZE_OF_LINE, APPLICATION_WIDTH);
    GRect thirdLine = new GRect(0, POSITION_OF_LINE_WIDTH, APPLICATION_WIDTH, SIZE_OF_LINE);
    GRect fourthLine = new GRect(0, POSITION_OF_LINE_WIDTH * 2, APPLICATION_WIDTH, SIZE_OF_LINE);

    GLabel scoreLabel;
    public void addScore(){
        add(scoreLabel = new GLabel(String.valueOf(score)), scoreLabel.getWidth(), scoreLabel.getHeight());
    }
    public void addVisual(){
        getMenuBar().setVisible(false);
        add(firstLine);
        add(secondLine);
        add(thirdLine);
        add(fourthLine);
        add(scoreLabel = new GLabel(String.valueOf(score)), scoreLabel.getWidth(), scoreLabel.getHeight());
    }

}
