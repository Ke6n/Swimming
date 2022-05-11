package service

import entity.Swimming
import view.Refreshable

/**
 * Main class of the service layer for the Swimming card game. Provides access
 * to all other service classes and holds the [currentGame] state for these
 * services to access.
 */
class RootService {
    /**
     * The currently active game. Can be `null`, if no game has started yet.
     */
    var currentGame: Swimming? = null

    val gameService = GameService(this)
    val playerActionService = PlayerActionService(this)

    /**
     * Adds the provided [newRefreshable] to all services connected
     * to this root service
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * Adds each of the provided [newRefreshables] to all services
     * connected to this root service
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }
}