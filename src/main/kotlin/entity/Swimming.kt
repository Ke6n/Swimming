package entity

/**
 * Entity class that represents a game state of "Swimming".
 */
class Swimming(val players: List<Player>, val drawPile: CardPile, var middleDeck: TripleDeck) {
    var passCounter: Int = 0
    var movesRemaining: Int = players.size
    var hasKnocked: Boolean = false
    var activePlayerIndex: Int = 0
        private set
    var discardPile: CardPile = CardPile(ArrayDeque(32))

    /**
     * Check if there are 2-4 players in the game.
     */
    init {
        check(players.size in 2..4) { "The game requires 2 to 4 players" }
    }

    /**
     * Calculate the index of the next active player.
     *
     * The index is incremented by 1 on every change.
     * Set to 0 if it's out of bounds.
     */
    fun changeActivePlayer() {
        if (activePlayerIndex < players.size - 1) activePlayerIndex++ else activePlayerIndex = 0
    }
}