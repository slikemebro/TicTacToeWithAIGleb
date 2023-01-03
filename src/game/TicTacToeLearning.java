package game;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GOval;
import visual.Visual;

import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeLearning extends Visual {

    int move = 0;

    boolean game = true;

    ArrayList<String> gameLine = new ArrayList<>();

    String currentGame = "";

    int[][] arr = new int[3][3];

//    FileWriter writer = new FileWriter("games.txt", false);


    @Override
    public void run() {
        addVisual();
        startGame();
    }

    private void startGame() {
        System.out.println(getScore());
        makeCross();
        firstMove();
        currentGame += getArray() + " ";
        checkStrategy();
        currentGame += getArray() + " ";
        while (game) {
            lookForWin(1);
            currentGame += getArray() + " ";
            checkWin();
            lookForWin(2);
            currentGame += getArray() + " ";
            checkWin();
        }
    }

    private void lookForStrategy(int element) {
        int space = 0;
        int count = 0;
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == element) count++;
                if (arr[i][j] == 0) {
                    y = i;
                    x = j;
                    space++;
                }
            }
            if (count == 2 && space == 1) {
                if (element == 1) {
                    drawCircle(y, x);
                } else {
                    drawCross(y, x);
                }
                return;
            }
            space = 0;
            count = 0;
        }

        space = 0;
        count = 0;

        for (int j = 0; j < arr.length; j++) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i][j] == element) count++;
                if (arr[i][j] == 0) {
                    y = i;
                    x = j;
                    space++;
                }
            }
            if (count == 2 && space == 1) {
                if (element == 1) {
                    drawCircle(y, x);
                } else {
                    drawCross(y, x);
                }
                return;
            }
            space = 0;
            count = 0;
        }

        space = 0;
        count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i][i] == element) count++;
            if (arr[i][i] == 0) {
                y = i;
                x = i;
                space++;
            }
            if (count == 2 && space == 1) {
                if (element == 1) {
                    drawCircle(y, x);
                } else {
                    drawCross(y, x);
                }
                return;
            }
        }

        space = 0;
        count = 0;

        int j = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i][j] == element) count++;
            if (arr[i][j] == 0) {
                y = i;
                x = j;
                space++;
            }
            if (count == 2 && space == 1) {
                if (element == 1) {
                    drawCircle(y, x);
                } else {
                    drawCross(y, x);
                }
                return;
            }
            j++;
        }
        if (element == 2) makeCross();
        else makeOval();
    }

    private void lookForWin(int element) {
        int space = 0;
        int count = 0;
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == element) count++;
                if (arr[i][j] == 0) {
                    y = i;
                    x = j;
                    space++;
                }
            }
            if (count == 2 && space == 1) {
                if (element == 1) {
                    drawCross(y, x);
                } else {
                    drawCircle(y, x);
                }
                return;
            }
            space = 0;
            count = 0;
        }

        space = 0;
        count = 0;

        for (int j = 0; j < arr.length; j++) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i][j] == element) count++;
                if (arr[i][j] == 0) {
                    y = i;
                    x = j;
                    space++;
                }
            }
            if (count == 2 && space == 1) {
                if (element == 1) {
                    drawCross(y, x);
                } else {
                    drawCircle(y, x);
                }
                return;
            }
            space = 0;
            count = 0;
        }

        space = 0;
        count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i][i] == element) count++;
            if (arr[i][i] == 0) {
                y = i;
                x = i;
                space++;
            }
            if (count == 2 && space == 1) {
                if (element == 1) {
                    drawCross(y, x);
                } else {
                    drawCircle(y, x);
                }
                return;
            }
//            space = 0;
//            count = 0;
        }

        space = 0;
        count = 0;

        int j = 0;
        for (int i = arr.length - 1; i >= 0; i--) {

            if (arr[i][j] == element) count++;
            if (arr[i][j] == 0) {
                y = i;
                x = j;
                space++;
            }
            if (count == 2 && space == 1) {
                if (element == 1) {
                    drawCross(y, x);
                } else {
                    drawCircle(y, x);
                }
                return;
            }
            j++;
//                space = 0;
//                count = 0;

        }
        if (element == 1) {
            lookForStrategy(2);
        } else lookForStrategy(1);
    }

    private void checkStrategy() {
        if (arr[1][1] == 0) drawCircle(1, 1);
        else if (arr[1][1] == 1) {
            makeOval();
//            drawCircle(0, 0);
        } else {
            makeOval();
        }
    }

    private void checkWin() {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2] && arr[i][0] != 0) doWin(arr[i][0]);
            if (arr[0][i] == arr[1][i] && arr[1][i] == arr[2][i] && arr[0][i] != 0) doWin(arr[0][i]);
        }
        if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] != 0) doWin(arr[1][1]);
        if (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[0][2] != 0) doWin(arr[1][1]);
        if (move == 9) doDraw();
    }

    private void doDraw() {
        System.out.println(currentGame);
        game = false;
        saveInformation();
        newGame();
    }

    private void newGame() {
        removeAll();
        move = 0;
        game = true;
        gameLine = new ArrayList<>();
        currentGame = "";
        arr = new int[3][3];
        addVisual();
        startGame();
    }

    private void doWin(int i) {
        System.out.println(currentGame);
//        currentGame += getArray() + " ";
        game = false;
        removeAll();
        addScore();
        GLabel win;
        if (i == 1) {
            win = new GLabel("You won!!!", 100, 100);
            setScore(getScore() + 1);
        } else {
            win = new GLabel("You lost", 100, 100);
            setScore(getScore() + 1);
            saveInformation();
        }
        add(win);
        newGame();
    }

    private void firstMove() {
        try {
            Scanner sc = new Scanner(new FileReader("games.txt"));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                gameLine.add(line);
            }

        } catch (Exception e) {

        }
    }

    private void makeCross() {
        int x, y;
        do {
            x = (int) (Math.random() * 3);
            y = (int) (Math.random() * 3);
        } while (arr[y][x] != 0);
        drawCross(y, x);
    }

    private void drawCross(int y, int x) {
        arr[y][x] = 1;
        x *= POSITION_OF_LINE_HEIGHT;
        y *= POSITION_OF_LINE_HEIGHT;
        GLine firstLine = new GLine(x, y,
                x + POSITION_OF_LINE_WIDTH, y + POSITION_OF_LINE_HEIGHT);
        GLine secondLine = new GLine(x + POSITION_OF_LINE_WIDTH, y,
                x, y + POSITION_OF_LINE_HEIGHT);
        add(firstLine);
        add(secondLine);
        move++;
    }

    private void saveInformation() {
        try (FileWriter writer = new FileWriter("games.txt", false)) {
            for (String s : gameLine) {
                writer.write(s + "\n");
            }
            String str = currentGame;
            if (!gameLine.contains(str)) writer.write(str);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getArray() {
        String arrayStr = "";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arrayStr += arr[i][j];
            }
        }
        return arrayStr;
    }

    private void makeOval() {
        int x, y;
        do {
            x = (int) (Math.random() * 3);
            y = (int) (Math.random() * 3);
        } while (arr[y][x] != 0);
        drawCircle(y, x);
    }

    private void drawCircle(int y, int x) {
        GOval oval = new GOval(POSITION_OF_LINE_WIDTH * x, POSITION_OF_LINE_HEIGHT * y,
                POSITION_OF_LINE_WIDTH, POSITION_OF_LINE_HEIGHT);
        add(oval);
        arr[y][x] = 2;
        move++;
    }
}
