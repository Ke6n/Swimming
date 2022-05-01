package entity

/**
 * Entity class that represents a game state of "Swimming".
 */
class Swimming(val players: List<Player>, val drawPile: CardPile, val middleDeck: TripleDeck) {
    var passCounter: Int = 0
    var movesRemaining: Int = players.size
    var hasKnocked: Boolean = false
    var activePlayerIndex: Int = 0
    var discardPile: CardPile? = null

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