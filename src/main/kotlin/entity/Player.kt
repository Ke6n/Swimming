package entity

/**
 * Entity class that represents a player in the game "Swimming".
 */
class Player(val name: String, var handDeck: TripleDeck) {
    var score: Double = 0.0

    /**
     * Check if player name is not blank.
     */
    init {
        check(name.isNotBlank()) { "Player name should not be empty" }
    }
}