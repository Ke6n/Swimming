package view

import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView

/**
 * Custom [LinearLayout] for [CardView]s.
 * It is used for all triple deck on the table.
 *
 * @param cardsRotation the rotation of the triple deck
 */
class TripleDeckView(height: Number = 200, width: Number = 600 , posX: Number,
                           posY: Number, spacing: Double = 20.0, cardsRotation: Double = 0.0) :
    LinearLayout<CardView>(height = height, width = width, posX = posX, posY = posY, spacing = spacing) {
    init {
            rotation = cardsRotation
    }
}