package game;

import acm.graphics.*;
import visual.Visual;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TicTacToe extends Visual {


    int section = 0;

    int move = 0;

    ArrayList<String> gameLine = new ArrayList<>();
    HashMap<String, Integer> leadersList = new HashMap<>();

    int[][] arr = new int[3][3];

    private JTextField name;

    GRect startGameButton = new GRect(
            POSITION_OF_BUTTONS_X,
            POSITION_OF_FIRST_BUTTONS_Y,
            BUTTON_WIDTH,
            BUTTON_HEIGHT);
    GRect complexityButton = new GRect(
            POSITION_OF_BUTTONS_X,
            POSITION_OF_SECOND_BUTTONS_Y,
            BUTTON_WIDTH,
            BUTTON_HEIGHT);
    GRect leadersButton = new GRect(
            POSITION_OF_BUTTONS_X,
            POSITION_OF_THIRD_BUTTONS_Y,
            BUTTON_WIDTH,
            BUTTON_HEIGHT);

    Object clickedElement;

    GLabel complexityLabel = new GLabel("complexity");
    GLabel startGameLabel = new GLabel("start game");
    GLabel leadersLabel = new GLabel("leaders");

    GRect lowLevelButton = new GRect(
            POSITION_OF_FIRST_COMPLEXITY_BUTTONS_X,
            POSITION_OF_COMPLEXITY_BUTTONS_Y,
            COMPLEXITY_BUTTON_WIDTH, COMPLEXITY_BUTTON_HEIGHT);
    GRect mediumLevelButton = new GRect(
            POSITION_OF_SECOND_COMPLEXITY_BUTTONS_X,
            POSITION_OF_COMPLEXITY_BUTTONS_Y,
            COMPLEXITY_BUTTON_WIDTH, COMPLEXITY_BUTTON_HEIGHT);
    GRect highLevelButton = new GRect(
            POSITION_OF_THIRD_COMPLEXITY_BUTTONS_X,
            POSITION_OF_COMPLEXITY_BUTTONS_Y,
            COMPLEXITY_BUTTON_WIDTH, COMPLEXITY_BUTTON_HEIGHT);

    GLabel lowLevelLabel = new GLabel("low");
    GLabel mediumLevelLabel = new GLabel("coming soon");
    GLabel highLevelLabel = new GLabel("high");

    String nickName;

    boolean gameStarted = false;

    int complexity = 1;

    int gameLineCounter = 0;

    GLabel win;
    int xPosition;
    int yPosition;


    @Override
    public void init() {
        System.out.println("init");
        JButton menu = new JButton("menu");
        add(menu, NORTH);

        add(new JLabel("Name: "), NORTH);

        name = new JTextField(20);
        add(name, NORTH);

        JButton startAgain = new JButton("Start new game");
        add(startAgain, NORTH);

        JButton saveRecord = new JButton("save record");
        add(saveRecord, NORTH);

        addActionListeners();

        addMouseListeners();

        firstMove();
        goToMenu();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "menu" -> {
                goToMenu();
                saveRecord();
            }
            case "Start new game" -> {
                saveRecord();
                gameStarted = true;
                removeAll();
                setScore(0);
                newGame();
            }
            case "save record" -> saveRecord();
        }
    }

    private void showNameAndRecord() {
        if (leadersList.containsKey(nickName)) {
            System.out.println("name add");
            GLabel informationAboutPlayer = new GLabel(nickName + " " + leadersList.get(nickName));
            add(informationAboutPlayer,
                    APPLICATION_WIDTH - informationAboutPlayer.getWidth() - 5,
                    informationAboutPlayer.getHeight());
        }
    }

    private void saveRecord() {
        try (FileWriter writer = new FileWriter("leaders.txt", false)) {
            int newScore = getScore();
            if (!nickName.equals("")) {
                if (leadersList.containsKey(nickName)) {
                    newScore = getScore() > leadersList.get(nickName) ? getScore() : leadersList.get(nickName);
                }
                leadersList.put(nickName, newScore);
            }
            String line;
            for (String key : leadersList.keySet()) {
                line = key + " " + leadersList.get(key);

                writer.write(line + "\n");
            }

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void goToMenu() {
        System.out.println(complexity);
        gameStarted = false;
        addVisual();
        removeAll();
        GLabel nameOfTheGame = new GLabel("TicTacToe by Gleb");
        nameOfTheGame.setFont("Verdana-20");
        add(nameOfTheGame, (double) (APPLICATION_WIDTH / 2) - (nameOfTheGame.getWidth() / 2),
                nameOfTheGame.getHeight());

        GLabel informationAboutGame1 = new GLabel("At the moment you can choose only low level or high level");
        GLabel informationAboutGame2 = new GLabel("Enter name for saving record name should be unique");


        complexityLabel.setFont("Verdana-20");
        startGameLabel.setFont("Verdana-20");
        leadersLabel.setFont("Verdana-20");

        addAndFillButton(complexityButton);
        addAndFillButton(leadersButton);
        addAndFillButton(startGameButton);

        add(leadersLabel, POSITION_OF_BUTTONS_X + (BUTTON_WIDTH / 2 - leadersLabel.getWidth() / 2),
                POSITION_OF_THIRD_BUTTONS_Y + (BUTTON_HEIGHT / 2 + leadersLabel.getHeight() / 2));
        add(complexityLabel, POSITION_OF_BUTTONS_X + (BUTTON_WIDTH / 2 - complexityLabel.getWidth() / 2),
                POSITION_OF_SECOND_BUTTONS_Y + (BUTTON_HEIGHT / 2 + complexityLabel.getHeight() / 2));
        add(startGameLabel, POSITION_OF_BUTTONS_X + (BUTTON_WIDTH / 2 - startGameLabel.getWidth() / 2),
                POSITION_OF_FIRST_BUTTONS_Y + (BUTTON_HEIGHT / 2 + startGameLabel.getHeight() / 2));
    }


    @Override
    public void run() {
        System.out.println("run game");
        if (gameStarted) {
            waitForClick();
            makeCross(xPosition, yPosition);
            checkWin();
            if (move > 0 && move < 9) {
                makeOval();
                section += 2;
                checkWin();
            } else {
                checkWin();
            }
            run();
        } else {
            waitForClick();
            run();
        }

    }

    @Override
    public void exit() {
        saveRecord();
        System.out.println("exit");
        super.exit();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        xPosition = mouseEvent.getX();
        yPosition = mouseEvent.getY();

        clickedElement = getElementAt(xPosition, yPosition);
        if (clickedElement != null) {
            removeAll();
            if ((clickedElement.equals(startGameButton) || clickedElement.equals(startGameLabel))) {
                gameStarted = true;
                removeAll();
                setScore(0);
                newGame();
            } else if ((clickedElement.equals(complexityButton) || clickedElement.equals(complexityLabel))) {
                removeAll();
                choseComplexity();
            } else if ((clickedElement.equals(leadersButton) || clickedElement.equals(leadersLabel))) {
                removeAll();
                showLeaders();
            } else if ((clickedElement.equals(lowLevelButton) || clickedElement.equals(lowLevelLabel))) {
                complexity = 0;
                goToMenu();
            } else if ((clickedElement.equals(mediumLevelButton) || clickedElement.equals(mediumLevelLabel))) {
                complexity = 2;//TODO 1
                goToMenu();
            } else if ((clickedElement.equals(highLevelButton) || clickedElement.equals(highLevelLabel))) {
                complexity = 2;
                goToMenu();
            }
        }
    }

    private void showLeaders() {
        GLabel leaders;
        String lineForLeaders;
        Set<Map.Entry<String, Integer>> set = leadersList.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(
                set);
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        leaders = new GLabel("Leaders");
        leaders.setFont("Verdana-40");
        add(leaders, (double) APPLICATION_WIDTH / 2 - leaders.getWidth() / 2, 100);
        int i = 0;
        for (Map.Entry<String, Integer> entry : list) {
            if (i == 5) break;
            lineForLeaders = entry.getKey() + " - " + entry.getValue();
            leaders = new GLabel(lineForLeaders);
            leaders.setFont("Verdana-30");
            add(leaders, (double) APPLICATION_WIDTH / 2 - leaders.getWidth() / 2, 130 + (i * 30));
            i++;
        }

    }


    private void choseComplexity() {
        addAndFillButton(lowLevelButton);
        addAndFillButton(mediumLevelButton);
        addAndFillButton(highLevelButton);

        lowLevelLabel.setFont("Verdana-20");
        mediumLevelLabel.setFont("Verdana-20");
        highLevelLabel.setFont("Verdana-20");

        add(lowLevelLabel,
                POSITION_OF_FIRST_COMPLEXITY_BUTTONS_X + (COMPLEXITY_BUTTON_WIDTH / 2 - lowLevelLabel.getWidth() / 2),
                POSITION_OF_COMPLEXITY_BUTTONS_Y + (COMPLEXITY_BUTTON_HEIGHT / 2 + lowLevelLabel.getHeight() / 2));
        add(mediumLevelLabel,
                POSITION_OF_SECOND_COMPLEXITY_BUTTONS_X + (COMPLEXITY_BUTTON_WIDTH / 2 - mediumLevelLabel.getWidth() / 2),
                POSITION_OF_COMPLEXITY_BUTTONS_Y + (COMPLEXITY_BUTTON_HEIGHT / 2 + mediumLevelLabel.getHeight() / 2));
        add(highLevelLabel,
                POSITION_OF_THIRD_COMPLEXITY_BUTTONS_X + (COMPLEXITY_BUTTON_WIDTH / 2 - highLevelLabel.getWidth() / 2),
                POSITION_OF_COMPLEXITY_BUTTONS_Y + (COMPLEXITY_BUTTON_HEIGHT / 2 + highLevelLabel.getHeight() / 2));
    }


    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        GObject object = getElementAt(mouseEvent.getX(), mouseEvent.getY());
        if (object != null) {
            if (object instanceof GRect) {
                changeColor((GRect) object);
            }
        } else {
            returnColor();
        }
    }

    private void returnColor() {
        startGameButton.setFillColor(anticipationButton);
        leadersButton.setFillColor(anticipationButton);
        complexityButton.setFillColor(anticipationButton);
        highLevelButton.setFillColor(anticipationButton);
        mediumLevelButton.setFillColor(anticipationButton);
        lowLevelButton.setFillColor(anticipationButton);
    }

    private void changeColor(GRect elementAt) {
        elementAt.setFillColor(inducedButton);
    }

    private void addAndFillButton(GRect nameOfButton) {
        nameOfButton.setFilled(true);
        nameOfButton.setFillColor(anticipationButton);
        add(nameOfButton);
    }

    private void checkWin() {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2] && arr[i][0] != 0) doWin(arr[i][0]);
            if (arr[0][i] == arr[1][i] && arr[1][i] == arr[2][i] && arr[0][i] != 0) doWin(arr[0][i]);
        }
        if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] != 0) doWin(arr[1][1]);
        if (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[0][2] != 0) doWin(arr[1][1]);
        if (move == 9) {
            doDraw();
        }
    }

    public void doDraw() {
        pause(500);
        removeAll();
        System.out.println("draw");
        addScore();
        GLabel draw = new GLabel("Draw", 100, 100);
        add(draw);
        pause(500);
        newGame();
    }

    public void newGame() {
        System.out.println("new game");
        removeAll();
        if (name.getText() != null) {
            nickName = name.getText();
            showNameAndRecord();
        }
        section = 0;
        move = 0;
        arr = new int[3][3];
        addVisual();
    }

    public void doWin(int i) {
        pause(500);
        System.out.println("win");
        removeAll();
        addScore();
        if (i == 1) {
            win = new GLabel("You won!!!", 100, 100);
            setScore(getScore() + 1);
        } else {
            win = new GLabel("You lost", 100, 100);
            setScore(getScore() - 1);
        }
        add(win);
        pause(500);
        newGame();
    }

    public void firstMove() {
        try {
            System.out.println("got");
            Scanner sc = new Scanner(new FileReader("games.txt"));

            while (sc.hasNext()) {
                String line = sc.nextLine();
                gameLine.add(line);
            }
            sc = new Scanner(new FileReader("leaders.txt"));

            while (sc.hasNext()) {
                String[] leader = sc.nextLine().split(" ");
                leadersList.put(leader[0], Integer.valueOf(leader[1]));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public void makeCross(int x, int y) {
        System.out.println("makeCross");
        int xBox = (int) (x / POSITION_OF_LINE_WIDTH);
        int yBox = (int) (y / POSITION_OF_LINE_WIDTH);
        while (arr[yBox][xBox] != 0) {
            System.out.println("while");
            waitForClick();
            xBox = (int) (xPosition / POSITION_OF_LINE_WIDTH);
            yBox = (int) (yPosition / POSITION_OF_LINE_WIDTH);
            if (arr[yBox][xBox] == 0) break;
            System.out.println(xBox + " " + yBox);
        }

        arr[yBox][xBox] = 1;
        xBox *= POSITION_OF_LINE_HEIGHT;
        yBox *= POSITION_OF_LINE_HEIGHT;
        GLine firstLine = new GLine(xBox, yBox,
                xBox + POSITION_OF_LINE_WIDTH, yBox + POSITION_OF_LINE_HEIGHT);
        GLine secondLine = new GLine(xBox + POSITION_OF_LINE_WIDTH, yBox,
                xBox, yBox + POSITION_OF_LINE_HEIGHT);
        add(firstLine);
        add(secondLine);

        move++;
    }

    public String getArray() {
        String arrayStr = "";
        for (int[] ints : arr) {
            for (int j = 0; j < arr.length; j++) {
                arrayStr += ints[j];
            }
        }
        return arrayStr;
    }

    public void makeOval() {
        String strategy = "";

        if (complexity == 2) {
            String[] minStrategy = new String[12];
            for (String s : gameLine) {
                String[] line = s.split(" ");
                if (section <= line.length && line[section].equals(getArray())) {
                    if (line.length < minStrategy.length) {
                        System.out.println("min");
                        minStrategy = line;
                    }
                }
            }
            if (minStrategy.length < 12) {
                strategy = minStrategy[section + 1];
            }
//            for ( ; gameLineCounter < gameLine.size(); gameLineCounter++) {
//                String[] line = gameLine.get(gameLineCounter).split(" ");
//                if (section < line.length && line[section].equals(getArray())) {
//                    if (line.length < minStrategy.length) {
//                        System.out.println("min");
//                        minStrategy = line;
//                    }
//                }
//            }
//            if (minStrategy.length < 12) {
//                strategy = minStrategy[section + 1];
//            }
        }


        if (strategy.equals("")) {
            System.out.println("no strategy");
            int x, y;
            do {
                x = (int) (Math.random() * 3);
                y = (int) (Math.random() * 3);
            } while (arr[y][x] != 0);
            drawCircle(y, x);
        } else {
            System.out.println("strategy " + strategy);
            String[] positions = strategy.split("");
            for (int i = 0; i < positions.length; i++) {
                if (positions[i].equals("2")) {
                    switch (i) {
                        case 0 -> {
                            if (arr[0][0] != 2) drawCircle(0, 0);
                        }
                        case 1 -> {
                            if (arr[0][1] != 2) drawCircle(0, 1);
                        }
                        case 2 -> {
                            if (arr[0][2] != 2) drawCircle(0, 2);
                        }
                        case 3 -> {
                            if (arr[1][0] != 2) drawCircle(1, 0);
                        }
                        case 4 -> {
                            if (arr[1][1] != 2) drawCircle(1, 1);
                        }
                        case 5 -> {
                            if (arr[1][2] != 2) drawCircle(1, 2);
                        }
                        case 6 -> {
                            if (arr[2][0] != 2) drawCircle(2, 0);
                        }
                        case 7 -> {
                            if (arr[2][1] != 2) drawCircle(2, 1);
                        }
                        case 8 -> {
                            if (arr[2][2] != 2) drawCircle(2, 2);
                        }
                    }
                }
            }
        }
        move++;
    }

    public void drawCircle(int y, int x) {
        GOval oval = new GOval(POSITION_OF_LINE_WIDTH * x, POSITION_OF_LINE_HEIGHT * y,
                POSITION_OF_LINE_WIDTH, POSITION_OF_LINE_HEIGHT);
        add(oval);
        arr[y][x] = 2;
    }
}
