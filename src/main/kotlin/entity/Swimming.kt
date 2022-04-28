package entity

/**
 * Entity class that represents a game state of "Swimming".
 */
class Swimming(val players: List<Player>) {
    var passCount: Int = 0
    var movesRemaining: Int = players.size
    var hasKnocked: Boolean = false
    var activePlayerIndex: Int = 0

    /**
     * Calculate the index of the next active player.
     */
    fun changeActivePlayer() {
        if (activePlayerIndex < players.size - 1) activePlayerIndex++ else activePlayerIndex = 0
    }
}