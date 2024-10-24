import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple implementation of the Wordle game using java swing GUI
 * This game allows the user to guess a 5-letter word within 6 attempts
 * This GUI proves feedback with colors:
 * Green: Correct letter and position
 * Yellow: Correct letter but not in correct position
 * Dark Grey: Incorrect letter
 *
 * @author Thomas Tsilimigras
 * @see java.lang.Object
 */
public class Wordle extends JFrame implements ActionListener {

    /**
     * An array of JButton elements representing the keyboard buttons in the game.
     */
    private final JButton[] buttons; // array of buttons

    /**
     * The container that holds all the UI components for the game (e.g., buttons, labels).
     */
    private final Container container; // frame container

    /**
     * The grid layout used for organizing the buttons in the first grid on the UI.
     */
    private final GridLayout gridLayout; // first gridlayout

    /**
     * int to keep track of the amount of guesses the user has used.
     * once it hits 6 and they haven't gotten the word the game is over
     * and it displays the word
     */
    private int chances;

    /**
     * Array (length 5) either has a:
     *  normal Gray - 0
     *  darker gray - 1
     *  yellow - 2
     *  green - 3
     *
     *  depending on what it is assigned
     */
    private final int[] colorArray;

    /**
     * List of 20 words that answerWord randomly selects from
     * final because the list should never change
     */
    private final String[] wordList;

    /**
     * the answer of the word
     * randomly selected a word from wordList
     */
    private final String answerWord;


    /**
     * array of words to keep track of what words that the user has entered
     * used for the word visual at the top where it displays the words
     */
    private String[] chanceWords;

    /**
     * array of colors for the backgrounds (new color(r,g,b))
     */
    private final Color[] colors;

    /**
     * array of letters for the keyboard (in qwerty order)
     * includes "->" for enter at i = 19 and "<-" at i = length-1
     */
    private final String[] letters;

    /**
     * This is the text area right above the keyboard where the user can see what they are typing
     */
    private final JTextArea textArea1;

    /**
     * This is the constructor for the Wordle class assigning all the values to variables as well as setting up the
     * buttons for the gui, as well as choosing a random word from a list of 20 words for the user to guess from
     * This also sets up the keyboard for the user to use to type in answer
     */
    public Wordle() {
        /* creating container for window */
        super("Wordle GUI");

        this.gridLayout = new GridLayout(); // 2 by 4; gaps of 5
        container = getContentPane();
        container.setBackground(new Color(18, 18, 19));
        setLayout(gridLayout);

        /* choosing words from list as well as setting up chances */
        this.chanceWords = new String[]{"", "", "", "", "", ""};
        this.chances = 0;
        this.colorArray = new int[5];
        this.wordList = new String[]{
                "SLATE",
                "TEACH",
                "POOPS",
                "OTTER",
                "WHALE",
                "MEANS",
                "SMART",
                "MOUSE",
                "WATER",
                "PLANT",
                "MONEY",
                "GLASS",
                "WHITE",
                "SMOKE",
                "BEADS",
                "BLACK",
                "ASIAN",
                "HORSE",
                "SHADE",
                "TRYST"};

        this.answerWord = wordList[(int) (Math.random() * wordList.length)];
        //System.out.println(answerWord);

        /* layout of gui */
        setLayout(null);

        //Text Area where word is typed
        textArea1 = new JTextArea("");
        textArea1.setEditable(false);
        textArea1.setBackground(Color.GRAY);
        textArea1.setForeground(Color.white);
        textArea1.setOpaque(true);
        textArea1.setBounds(0, 0, 50, 20);
        textArea1.setLocation(410, 670);
        container.add(textArea1);

        // Key board
        this.letters = new String[]{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",  // Top row
                "A", "S", "D", "F", "G", "H", "J", "K", "L",       // Middle row
                "->", "Z", "X", "C", "V", "B", "N", "M", "<-"}; // Bottom row
        this.buttons = new JButton[letters.length];
        this.colors = new Color[]{new Color(128, 131, 132), new Color(58, 58, 61), new Color(180, 159, 59), new Color(84, 141, 78)};

        for (int count = 0; count < letters.length; count++) {
            if (count < 10) {
                buttons[count] = new JButton(letters[count]);
                buttons[count].setBackground(colors[0]);
                buttons[count].setForeground(Color.white);
                buttons[count].setPreferredSize(new Dimension(50, 60));
                buttons[count].addActionListener(this); // register listener

                buttons[count].setBounds(140 + (count * 60), 710, 50, 70);

                add(buttons[count]);
            } else if (count > 9 && count < 19) {
                int adjustCount = count - 10;
                buttons[count] = new JButton(letters[count]);
                buttons[count].setBackground(colors[0]);
                buttons[count].setForeground(Color.white);
                buttons[count].setPreferredSize(new Dimension(50, 65));
                buttons[count].addActionListener(this); // register listener

                buttons[count].setBounds(170 + (adjustCount * 60), 790, 50, 70);

                add(buttons[count]);
            } else {
                int adjustCount = count - 19;

                buttons[count] = new JButton(letters[count]);
                buttons[count].setBackground(colors[0]);
                buttons[count].setForeground(Color.white);
                buttons[count].setPreferredSize(new Dimension(50, 60));
                buttons[count].addActionListener(this); // register listener

                buttons[count].setBounds(170 + (adjustCount * 60), 870, 50, 70);

                container.add(buttons[count]);
            }
        }


    }

    /**
     * Processes the button press events in the game.
     * Handles submitting the player's guess, checking it against the correct answer,
     * and updating the UI accordingly (e.i., setting the colors of the letters
     * based on correctness and updating the keyboard button colors).
     *
     * The method works as follows:
     * - If the guess is valid (5 letters), it compares the guess with the answer.
     * - It assigns colors to the letters to show if they are correct (green),
     *  in the wrong position (yellow), or not in the word (dark gray).
     *  - Updates the on-screen keyboard with appropriate colors.
     *  - Displays a message if the player wins or loses, and moves to the next guess.
     * - Also handles input from enter and backspace buttons during the guessing process.
     *
     *  @param e the event to be processed
    */
    @Override
    public void actionPerformed(ActionEvent e) {

        String lastWord = "";
        if (e.getSource() == buttons[19] && chanceWords[chances].length() == 5 && e.getSource() != buttons[letters.length - 1]) { // submit guess

            //System.out.println("___");
            //System.out.println(chanceWords[chances]);
            //System.out.println(answerWord);
            lastWord = chanceWords[chances];
            //System.out.println("Text area is " + textArea1.getText());
            textArea1.setText("");
            //System.out.println("Text area is " + textArea1.getText());
            //System.out.println("__");

            int[] letterCount = new int[26];
            boolean[] usedPositions = new boolean[5];
            for (int i = 0; i < 5; i++) {
                char answerChar = answerWord.charAt(i);
                letterCount[answerChar - 'A']++;

                if (chanceWords[chances].charAt(i) == answerChar) {
                    colorArray[i] = 3;
                    letterCount[answerChar - 'A']--;
                    usedPositions[i] = true;
                }
            }
            for (int i = 0; i < 5; i++) {
                char guessChar = chanceWords[chances].charAt(i);

                if (colorArray[i] != 3 && letterCount[guessChar - 'A'] > 0) {
                    colorArray[i] = 2;

                    letterCount[guessChar - 'A']--;
                }
                if (colorArray[i] != 3 && colorArray[i] != 2) {
                    colorArray[i] = 1;
                }


                JLabel Letter = new JLabel(String.valueOf(chanceWords[chances].charAt(i)));
                Letter.setSize(70, 90);
                Letter.setOpaque(true);
                Letter.setLocation(220 + (i * 90), 100 + (chances * 95));
                Letter.setBackground(colors[colorArray[i]]);
                Letter.setForeground(Color.WHITE);

                Letter.setHorizontalAlignment(SwingConstants.CENTER);
                Letter.setFont(new Font(null, 0, 30));
                container.add(Letter);
                colorArray[i] = 0;
            }

            for (int i = 0; i < letters.length; i++) {
                String buttonText = buttons[i].getText();
                int indexInGuess = lastWord.indexOf(buttonText);

                // check if the letter is in the answer word
                if (answerWord.contains(buttonText)) {
                    if (indexInGuess != -1) {
                        // check if the letter is in the correct position EI Green
                        if (lastWord.charAt(indexInGuess) == answerWord.charAt(indexInGuess)) {
                            buttons[i].setBackground(colors[3]); // Green for correct position
                        }
                        // if its not green, check if its in the wrong position EI Yellow
                        else if (buttons[i].getBackground() != colors[3]) {
                            //System.out.println(buttons[i].getText() + " Was changed to yellow");
                            buttons[i].setBackground(colors[2]); // Yellow for wrong position
                        }

                    }
                }
                if(lastWord.contains(buttons[i].getText()) && buttons[i].getBackground() != colors[3] && buttons[i].getBackground() != colors[2]){
                    buttons[i].setBackground(colors[1]);
                }
            }


            if (chanceWords[chances].equals(answerWord)) {
                textArea1.setSize(335, 20);
                textArea1.setText("Congratulations! You guess the word in " + (chances + 1) + "/6 guesses!");
                textArea1.setLocation(262, 670);

                container.repaint(); // repaint is in the super class container
                return;
            }
            if (chances == 5) {
                textArea1.setSize(200, 20);
                textArea1.setText("Nice Try! The word was " + answerWord);
                textArea1.setLocation(330, 670);

                container.repaint(); // repaint is in the super class container
                return;
            }
            container.repaint();// repaint is in the super class container
            chances++;

        } else if (e.getSource() != buttons[letters.length - 1] && chanceWords[chances].length() < 5 && e.getSource() != buttons[19]) {
            // Handle letter entry
            chanceWords[chances] += e.getActionCommand();
            //System.out.println(chanceWords[chances]);
            textArea1.setText(chanceWords[chances]);

        } else if (e.getSource() == buttons[letters.length - 1] && chanceWords[chances].length() > 0) {
            // Handle backspace
            chanceWords[chances] = chanceWords[chances].substring(0, chanceWords[chances].length() - 1);
            //System.out.println(chanceWords[chances]);
            textArea1.setText(chanceWords[chances]);
        }

    }


}