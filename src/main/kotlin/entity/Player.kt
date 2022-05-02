package entity

/**
 * Entity class that represents a player in the game "Swimming".
 */
class Player(val name: String, val handDeck: TripleDeck) {
    var score: Double = 0.0

    /**
     * Check if player name is not blank.
     */
    init {
        require(name.isNotBlank()){"Playername should not be empty"}
    }
}