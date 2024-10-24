# S52_WordleGUI_Hard

### Problem Statement
This project involves creating a Wordle-like game where players have 6 chances to guess a random 5-letter word, with color-coded feedback for each guess. The GUI will display remaining valid letters, previous guesses, and whether the player has won or lost.

### Developer Documentation

#### Wordle Class

##### Description:
The `Wordle` class provides a Java Swing implementation of the popular word game "Wordle." It allows the user to guess a 5-letter word in 6 attempts. Feedback is given based on the player's guesses, using color-coded buttons:
- **Green**: Correct letter in the correct position.
- **Yellow**: Correct letter, but in the wrong position.
- **Dark Grey**: Incorrect letter.

##### Main Attributes:
- **`JButton[] buttons`**: Array representing the keyboard buttons used for the game.
- **`Container container`**: Holds all UI components, including buttons and text areas.
- **`GridLayout gridLayout`**: Used to layout the game’s UI elements.
- **`int chances`**: Tracks the number of attempts the player has made.
- **`int[] colorArray`**: Array representing colors for feedback:
  - 0: Normal Gray
  - 1: Dark Gray
  - 2: Yellow
  - 3: Green
- **`String[] wordList`**: A list of possible 5-letter answer words for the game.

##### Key Methods:
- **`Wordle()`**: Constructor that sets up the game UI, initializes the buttons, text area, and gameplay.
- **`actionPerformed(ActionEvent e)`**: Handles user interaction with the buttons. It manages word input, guessing, and provides feedback through colors.
  - Checks if the guessed word is correct or incorrect and updates the UI accordingly.
  - Gives feedback on each guess by setting the background color of the buttons (Green, Yellow, Grey).
- **`initializeKeyboard()`**: Initializes and sets up the keyboard layout on the UI.
- **`resetGame()`**: Resets the game when the player starts a new round.

---

## Main Class

##### Description:
The `Main` class serves as the entry point for the application. It initializes the `Wordle` game UI and makes the game window visible.

##### Key Methods:
- **`main(String[] args)`**: Initializes the `Wordle` object, sets its default behavior, and starts the game UI.


---
### User Documentation

#### Overview:
This Wordle game implementation allows you to guess a 5-letter word within 6 attempts. After each guess, the game provides feedback based on how accurate your guess was:
- **Green**: The letter is in the correct position.
- **Yellow**: The letter exists in the word but in the wrong position.
- **Dark Grey**: The letter does not exist in the word.

#### How to Play:
1. **Starting the Game**:
   - Launch the game by running the `Main` class. The game window will appear with a virtual keyboard and space for guessing words.

2. **Making a Guess**:
   - Type a 5-letter word using the on-screen keyboard.
   - Press "Enter" to submit the guess.
   - The game will give feedback on the guess:
     - **Green**: Correct letter in the correct position.
     - **Yellow**: Correct letter in the wrong position.
     - **Grey**: Incorrect letter.
   - You have 6 attempts to guess the word correctly.

3. **Using the Backspace**:
   - If you need to delete a letter before submitting a guess, press the "Backspace" button.

4. **Winning and Losing**:
   - If you guess the word correctly within 6 attempts, a congratulatory message will appear.
   - If you fail to guess within 6 tries, the correct word will be revealed.

5. **Resetting the Game**:
   - After completing a game, the application will automatically reset, allowing you to play again with a new word.

#### Example Gameplay:

- **Scenario 1**: You guess "APPLE" and the correct word is "ALONE":
  - **Green** for "A" (correct position),
  - **Yellow** for "L" and "E" (correct letters but wrong positions),
  - **Grey** for "P" (incorrect letter).
  
- **Scenario 2**: You guess "TRAIN" and the correct word is "LEARN":
  - **Grey** for "T", "R", and "I" (incorrect letters),
  - **Yellow** for "A" and "N" (correct letters, wrong positions).

#### Notes:
- The game will automatically reset after 6 attempts or when you guess the word correctly.
- All guesses must be 5-letter words.

### Source Code
[Click here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/ttsilimigras/-/tree/master/oral_exam1/S52_WordleGUI)
