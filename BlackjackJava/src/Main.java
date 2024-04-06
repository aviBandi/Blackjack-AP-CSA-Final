// This java program creates a game of blackjack. This is a one player game between the dealer and the one player only.


// Ask Hamre if it EXPLICITLY has to be two diff arguments even though it will run different sections every time.
// INTEGRATE A TWO PLAYER FEATURE OF NEEDED INTO THE CODE IF NEEDED FOR CSP FINAL TO HAVE TWO FUNCTION CALLS
// THIS WOULD BE FOR THE TAKE_BET FUNCTION TO BE ABLE TO PASS IN TWO DIFFERENT PARAMENTERS


// Imports modules

import java.util.*;


public class Main {
    // Here we combine all the classes and functions to create the logic of the game.
    public static void main(String[] args) {
        // Scanner allows user to enter input.
        Scanner reader = new Scanner(System.in);

        // Welcome message
        System.out.println("Welcome to BlackJack! Get as close to 21 without going over!\n" +
                " Dealer will hit until they reach 17 or more.");
        System.out.println("Try to get as many chips as you can!!! No max chips.\n" +
                " However try not to go bankrupt with zero chips. Or else you will lose.");

        // Initiates player chips of Chips class.
        Chips player_chips = new Chips();
        // This is practically the game play. If the player loses all chips this will break or if they decide not to keep playing.
        while (true){
            // Initiates playing deck in the game and shuffles the deck.
            Deck deck = new Deck();
            deck.shuffle();

            // Initiates player hand object of Hand class and deals them two cards from deck.
            Hand player_hand = new Hand();
            player_hand.add_card((ArrayList) deck.deal());
            player_hand.add_card((ArrayList) deck.deal());

            // Initiates dealer hand object of Hand class and deals them two cards from deck.
            Hand dealer_hand = new Hand();
            dealer_hand.add_card((ArrayList) deck.deal());
            dealer_hand.add_card((ArrayList) deck.deal());

            // Takes bet of player
            player_chips.bet = take_bet(player_chips);
            // Shows player one of the dealer's cards and all of their own cards.
            show_some(player_hand, dealer_hand);
            // Allows the player to keep hitting until they choose to stand.
            while (ReferencePlaying.playing == true) {
                // Allows player to make their decision
                hit_or_stand(deck, player_hand);
                // After each "hit" or "stand" the player makes. It shows them the updated cards of their hand.
                // And again one of card's in the dealer's hand.
                show_some(player_hand, dealer_hand);
                // If they hit and go over 21 they automatically lose.
                if (player_hand.totalHandvalue > 21) {
                    System.out.println("Player busts!");
                    player_chips.lose_bet();
                    break;
                }
            }
            // If player does not bust and stands before they bust the following will run.
            if (player_hand.totalHandvalue <= 21) {
                // Dealer keeps hitting and drawing a card until he is above 17.
                while (dealer_hand.totalHandvalue < 17) {
                    dealer_hand.add_card((ArrayList) deck.deal());
                }
                // Shows all the cards to the player as now the game is done.
                System.out.println("-----------------------");
                System.out.println("\n Dealer's Hand: ");
                System.out.println(dealer_hand.cards);
                System.out.println("Dealer's total cards' value is: " + dealer_hand.totalHandvalue);
                System.out.println("\n Player's Hand: ");
                System.out.println(player_hand.cards);
                System.out.println("Player's total cards' value is: " + player_hand.totalHandvalue);
                System.out.println("-----------------------");

                // If dealer busts the following will run
                if (dealer_hand.totalHandvalue > 21) {
                    System.out.println("Dealer Busts!");
                    player_chips.win_bet();
                }
                // Else if both don't bust and dealer value is greater than player value. The dealer will be victorious
                else if (dealer_hand.totalHandvalue > player_hand.totalHandvalue) {
                    System.out.println("Dealer Wins!");
                    player_chips.lose_bet();
                }
                // Else if both don't bust and player value is greater than dealer value. The player will be victorious.
                else if (player_hand.totalHandvalue > dealer_hand.totalHandvalue) {
                    System.out.println("Player Wins");
                    player_chips.win_bet();
                }
                // Else if there is a tie. Both player values are same.
                else {
                    System.out.println("There was a tie. No chips will be lost or won.");
                }
            }
            // Shows player their new chip count.
            System.out.println("\n Player's total current chip count at: "+player_chips.total_chips);
            // If player has lost all chips. He has lost completely Game will exit after sorry statement.
            if (player_chips.total_chips == 0){
                System.out.println("You have no more chips to bet. You have completely lost the game.");
                break;
            }
            // Allows the player to play again with the updated chip count. This uses error checking.
            String new_game;
            while (true){
                System.out.println("Please enter 'y' if you want a new game.\n" +
                        "Enter 'n' if you want to end the game. Please enter only 'y' or 'n'.");
                new_game = reader.next();

                if (new_game.equals("y") | new_game.equals("n")){
                    break;
                }
                else{
                    System.out.println("Please re-enter proper value.");
                }
            }
            // If the answer is 'y'  the playing will be true so the loop will continue for another round.
            if (new_game.equals("y")){
                ReferencePlaying.playing = true;
            }
            // Else if they don't want to keep playing, the loop will break.
            else if (new_game.equals("n")){
                System.out.println("Thanks for playing!");
                break;
            }

        }
    }

    // Used to show the player updates to their own hand throughout the game.
    // Following shows one of the dealer's cards and all of the player's cards.
    public static void show_some(Hand player, Hand dealer){
        System.out.println("-----------------------");
        System.out.println("Dealer's Hand: ");
        System.out.println("<Card(s) Hidden>");
        System.out.println(dealer.cards.get(1));
        System.out.println("\nPlayer's Hand");
        System.out.println(player.cards);
        System.out.println("-----------------------");
    }
    // Allows the user to hit or stand.
    public static void hit_or_stand(Deck deck, Hand hand){

        Scanner reader = new Scanner(System.in);
        // Uses error checking to make sure the user enters a proper hit or stand value that is accepted by the computer.
        while (true){
            // Allows user to input.
            System.out.println("Would you like to hit or stand. Enter 'h' for hit. Enter 's' for stand.");
            String input = reader.next();
            // Deals a card to hand if they hit
            if (input.charAt(0) == 'h') {
                hand.add_card((ArrayList) deck.deal());
            }
            // If they stand, the playing will be false. Loop will not go on for another iteration in which this function is called.
            else if (input.charAt(0) == 's'){
                System.out.println("Player stands. Dealer is playing.");
                ReferencePlaying.playing = false;
            }
            // Else if not valid it will go another iteration, forcing the user to enter a proper input.
            else{
                System.out.println("Please re-enter not a valid input.");
                continue;
            }
            break;
        }
    }

    // Allows player to enter input.
    public static int take_bet(Chips chips){
        // This loop makes sure the user enters a value only valid with the chips they have.
        while (true){
            Scanner reader = new Scanner(System.in);
            // Shows user information and tells them the instructions
            System.out.println("You have the following amount of chips: "+ chips.total_chips);
            System.out.println("Enter the amount of chips you would like to bet: ");
            // Takes input of the chips player would like to bet.
            int chipsBet = reader.nextInt();
            // If user enters a chip amount less than or equal to the amount of chips they have and chips bet greater than zero it is a valid input and then returned.
            if (chips.total_chips >= chipsBet && chipsBet>0){
                return chipsBet;
            }
            // Else if invalid input, it will continue with the list.
            else
                System.out.println("Please re-enter a valid input.");
        }
    }
}

// This class is used to create a global boolean value of playing used in main.
// Playing allows the loop to continue or not.
class ReferencePlaying {
    public static boolean playing = true;
}

// Hand is used to keep track of the cards in the player and dealer's hand.
class Hand{

    // Value used to keep track of to total value in case one busts (goes over 21)
    int totalHandvalue;
    // Keeps track of aces in the hand.
    int aces_count;
    // The cards in the hand will be kept in an arraylist so the code can easily add to the cards arraylist.
    ArrayList cards;
    // Constructor to create arraylist. Assign value of cards and aces to zero.
    public Hand(){
        cards = new ArrayList();
        totalHandvalue = 0;
        aces_count = 0;
    }
    // Adds cards to hand. Also adjusts value of hand based off card value.
    public void add_card(ArrayList card){

        // Following are parallel arrays for ranks of cards of each suit and their value in blackjack.
        String[] ranksInDeck = new String[]{"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
        int[] intValsOfRanks = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 10 ,10, 10, 11};

        ArrayList<String> ranksInDeckAsArrayList = new ArrayList<String>(Arrays.asList(ranksInDeck));

        // Adds card to players hand
        cards.add(card);
        // This is to get the index of the rank from each card. Later to use
        int valIndex = ranksInDeckAsArrayList.indexOf(card.get(1));
        // Adds card value to total hand value.
        totalHandvalue += intValsOfRanks[valIndex];

        // Following is for adjusting for ace.
        if (card.get(1).equals("Ace")){
            aces_count +=1;
        }
        if (totalHandvalue >21 && aces_count >0){
            totalHandvalue -=10;
            aces_count -=1;
        }

    }
}

// Deck class necessary for keeping track of the deck of cards.
class Deck{
    // Suits so cards can be added to the deck with a suit.
    String[] suits = new String[]{"Hearts", "Diamonds", "Spades", "Clubs"};
    // Ranks of the cards so cards can be added with the correct rank to the deck.
    String[] ranks = new String[]{"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};

    // Initiates a deck arraylist used to keep track of the cards.
    public ArrayList deck;
    // Constructor necessary to create the deck with 52 unique cards, based off a real deck of cards.
    public Deck(){
        deck = new ArrayList();
        for (String eachSuit:suits){
            for (String eachRank: ranks){
                Card individualCard = new Card(eachSuit,eachRank);
                deck.add(individualCard.cardlist);
            }
        }
    }
    // shuffle method shuffles the deck arraylist.
    public void shuffle(){
        Collections.shuffle(deck);
    }
    // Deals random card to the player.
    public Object deal(){
        Random rand = new Random();
        // Chooses random index to deal card from deck.
        int randomIndex = rand.nextInt(0,deck.size()-1);
        // Removes the dealt card from the list to avoid repeating the same card.
        deck.remove(randomIndex);
        return deck.get(randomIndex);
    }


}

// This Card Class is used to enter cards into the deck in a proper format. An arraylist is the format that will be used.
class Card{
    String suit;
    String rank;

    public ArrayList cardlist;
    // Constructor that adds the cards to cards list.
    public Card(String paramSuit, String paramRank){
        cardlist = new ArrayList();
        cardlist.add(paramSuit);
        cardlist.add(paramRank);
    }
}

// Chips class will be used to keep track of player chips throughout the game.
class Chips{
    // Total amount of chips.
    int total_chips;
    // The bet the player makes.
    int bet;
    // Initiates total_chips to 100 and bet to zero.
    public Chips(){
        total_chips = 100;
        bet = 0;
    }
    // If one win bet they will be added the bet amount to total.
    public void win_bet(){
        total_chips +=bet;
        bet = 0;
    }
    // If one loses bet they will lose the bet amount from total.
    public void lose_bet(){
        total_chips -=bet;
        bet = 0;
    }
}
