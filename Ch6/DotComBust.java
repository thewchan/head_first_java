import java.util.*;


public class DotComBust {
    // Declare and initialize the variables we'll need
    private GameHelper helper = new GameHelper();
    private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
    private int numOfGuesses = 0;

    private void setUpGame() {
        // First make some dotComs and give them locations
        // Make three DotCom objects give them names and put them in the ArrayList
        DotCom one = new DotCom();
        one.setName("Pets.com");
        DotCom two = new DotCom();
        two.setName("eToys.com");
        DotCom three = new DotCom();
        three.setName("Go2.com");
        dotComsList.add(one);
        dotComsList.add(two);
        dotComsList.add(three);

        // Print brief instructions for user
        System.out.println("Your goal is to sink three dot coms.");
        System.out.println("Pets.com, eToys.com, Go2.com");
        System.out.println("Try to sink them all in the fewest number of guesses.");

        // Repeat with each dotComs in the list
        for (DotCom dotComToSet : dotComsList) {
            // Ask the Helper for a DotCom location
            ArrayList<String> newLocation = helper.placeDotCom(3);
            // Call the setter method on this DotCom to give it the location from the helper
            dotComToSet.setLocationCells(newLocation);
        } // close for loop
    } // close setUpGame method

    private void startPlaying() {
        // As long as the DotCome list is NOT empty
        while (!dotComsList.isEmpty()) {
            // Get user input
            String userGuess = helper.getUserInput("Enter a guess");
            // Call checkUserGuess method
            checkUserGuess(userGuess);
        } // close while loop
        // Call finishGame method
        finishGame();
    } // Close startPlaying method

    private void checkUserGuess(String userGuess) {
        // Increment the number of guesses the user has made
        numOfGuesses++;
        // Assume a "miss" unless told otherwise
        String result = "miss";

        // Repeat with all DotComs in the list
        for (int x = 0; x < dotComsList.size(); x++) {
            // Ask the DotCom to check the user guess looking for a hit/kill
            result = dotComsList.get(x).checkYourself(userGuess);
            if (result.equals("hit")) {
                // Get out of the loop early, no point in testing the others
                break;
            }
            if (result.equals("kill")) {
                dotComsList.remove(x);
                break;
            }
        } // Close for loop
        // Print the result for the user
        System.out.println(result);
    } // Close checkUserGuess method

    private void finishGame() {
        // Print a message telling the user how he did in the game
        System.out.println("All DotComs are dead! Your stock is now worthless.");
        if (numOfGuesses <= 18) {
            System.out.println("It only took you " + numOfGuesses + " guess.");
            System.out.println("You got out before your options sank.");
        } else {
            System.out.println("Took you long enough. " + numOfGuesses + " guesses.");
            System.out.println("Fish are dancing with your options.");
        }
    } // Close finishGame method

    public static void main(String[] args) {
        // Create the game object
        DotComBust game = new DotComBust();
        // Tell the game object to set up the game
        game.setUpGame();
        // Tell the game object to start the main game play loop
        game.startPlaying();
    } // Close main method
}       