package view

import entity.Card
import entity.CardPile
import entity.TripleDeck
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

/**
 * This is the main thing for the Swimming game. The scene shows the complete table at once.
 */
class SwimmingGameScene(private val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable {

    var playerNames: List<String>? = null
    var middleCardIndex: Int = -1
    var playerCardIndex: Int = -1

    // Cards status: true if selected, false otherwise
    private val middleCardsSelected = mutableListOf(false, false, false)
    private val handCardsSelected = mutableListOf(false, false, false)

    private val drawPile = LabeledStackView(posX = 1295, posY = 270, "draw pile", stackRotation = 315.0)
    private val discardPile = LabeledStackView(posX = 495, posY = 270, "discard pile", stackRotation = 45.0)
    private var middleDeck = TripleDeckView(posX = 745, posY = 350, width = 500)
    var handDeck = TripleDeckView(height = 226, posX = 690, posY = 720, spacing = 36.0).apply {
        onMouseClicked = {
            // Cards flip
            this.forEach { cv -> cv.showFront() }
        }
    }
        private set

    val passButton = Button(
        width = 400, height = 100,
        posX = 240, posY = 763,
        text = "Pass",
        font = Font(size = 40, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(55, 15, 78)
        isFocusable = false
    }
    val knockButton = Button(
        width = 400, height = 100,
        posX = 1280, posY = 763,
        text = "Knock",
        font = Font(size = 40, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(55, 15, 78)
        isFocusable = false
    }
    val changeOneCardButton = Button(
        width = 100, height = 100,
        posX = 540, posY = 580,
    ).apply {
        visual = ImageVisual("change1.png")
        isFocusable = false
    }
    val changeAllCardsButton = Button(
        width = 100, height = 100,
        posX = 1280, posY = 580
    ).apply {
        visual = ImageVisual("changeAll.png")
        isFocusable = false
    }
    private val activePlayerLabel = Label(
        width = 1000, height = 100,
        posX = 460, posY = 950,
        font = Font(size = 40, color = Color.WHITE)
    )

    val remainingTurnsLabel = Label(
        width = 1000, height = 120,
        posX = 460, posY = 120,
        font = Font(size = 50, color = Color.ORANGE, fontWeight = Font.FontWeight.BOLD)
    )

    val pauseButton = Button(
        width = 100, height = 100,
        posX = 1780, posY = 20
    ).apply {
        visual = ImageVisual("pause-button.png")
        isFocusable = false
    }

    val helpButton = Button(
        width = 92, height = 92,
        posX = 1660, posY = 24
    ).apply {
        visual = ImageVisual("help-button.png")
        isFocusable = false
    }

    /**
     * structure to hold pairs of (card, cardView) that can be used
     *
     * 1. to find the corresponding view for a card passed on by a refresh method (forward lookup)
     *
     * 2. to find the corresponding card to pass to a service method on the occurrence of
     * ui events on views (backward lookup).
     */
    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()

    init {
        background = ImageVisual("poker_table.jpg")
        addComponents(
            drawPile,
            discardPile,
            middleDeck,
            handDeck,
            passButton,
            knockButton,
            changeOneCardButton,
            changeAllCardsButton,
            activePlayerLabel,
            remainingTurnsLabel,
            pauseButton,
            helpButton
        )
        components.forEach { it.isVisible = false }
    }

    /**
     * perform refreshes that are necessary after a new game started
     */
    override fun refreshOnStartGame() {
        val game = rootService.currentGame!!

        cardMap.clear()

        val cardImageLoader = CardImageLoader()

        initializeStackView(game.drawPile, drawPile, cardImageLoader)
        initializeStackView(game.discardPile, discardPile, cardImageLoader)
        initializeTripleDeckView(game.middleDeck, middleDeck, middleCardsSelected, false, cardImageLoader)
        initializeTripleDeckView(
            game.players[0].handDeck, handDeck, handCardsSelected,
            true, cardImageLoader, cardHeight = 226, cardWidth = 156
        )
        activePlayerLabel.text = "Player 1: ${playerNames!![0]}"
    }

    /**
     * clears [stackView], adds a new [CardView] for each
     * element of [stack] onto it, and adds the newly created view/card pair
     * to the global [cardMap].
     */
    private fun initializeStackView(stack: CardPile, stackView: LabeledStackView, cardImageLoader: CardImageLoader) {
        stackView.clear()
        stack.cardsOnPile.reversed().forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }

    /**
     * clears [deckView], adds a new [CardView] for each
     * element of [deck] onto it, and adds the newly created view/card pair
     * to the global [cardMap].
     *
     * @param showBack true: show the back of cards after initialization, false: otherwise
     */
    private fun initializeTripleDeckView(
        deck: TripleDeck, deckView: LinearLayout<CardView>, cardsSelected: MutableList<Boolean>, showBack: Boolean,
        cardImageLoader: CardImageLoader, cardHeight: Number = 200, cardWidth: Number = 130
    ) {
        deckView.clear()
        deck.cards.forEachIndexed { index, card ->
            val cardView = CardView(
                height = cardHeight,
                width = cardWidth,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply {
                if (!showBack) showFront()
                onMouseClicked = {
                    if (handDeck.first().currentSide == CardView.CardSide.FRONT) {
                        cardsSelected[index] = !cardsSelected[index]
                        if (cardsSelected[index]) {
                            if (showBack) playerCardIndex = index else middleCardIndex = index

                            playAnimation(
                                MovementAnimation(
                                    componentView = this,
                                    byY = -height / 5,
                                    duration = 20
                                )
                            )
                            cardsSelected.forEachIndexed { i, _ -> if (i != index) cardsSelected[i] = false }
                            resetUnselectedCards(deckView, cardsSelected)
                        } else {
                            if (showBack) playerCardIndex = -1 else middleCardIndex = -1

                            playAnimation(
                                MovementAnimation(
                                    componentView = this,
                                    duration = 20
                                )
                            )
                        }
                    }
                }
            }
            deckView.add(cardView)
            cardMap.add(card to cardView)
        }
    }

    /**
     * Play animation: Reset the unselected cards
     */
    private fun resetUnselectedCards(deckView: LinearLayout<CardView>, cardsSelected: MutableList<Boolean>) {
        deckView.forEachIndexed { index, cardView ->
            if (!cardsSelected[index]) {
                playAnimation(
                    MovementAnimation(
                        componentView = cardView,
                        duration = 20
                    )
                )
            }
        }
    }

    /**
     * perform refreshes that are necessary after passed
     */
    override fun refreshOnPass() {
        val game = rootService.currentGame!!
        val cardImageLoader = CardImageLoader()
        initializeStackView(game.discardPile, discardPile, cardImageLoader)
        initializeStackView(game.drawPile, drawPile, cardImageLoader)
    }

    /**
     * perform refreshes that are necessary after knocked
     */
    override fun refreshOnKnock() {
        remainingTurnsLabel.text =
            "     !! Knocked !!     \nRemaining Turns: ${rootService.currentGame!!.movesRemaining}"
        remainingTurnsLabel.isVisible = true
    }

    /**
     * perform refreshes that are necessary after one card changed
     *
     * @param playerCardIndex is the index of the hand card to be changed
     * @param middleCardIndex is the index of the middle card to be changed
     */
    override fun refreshOnChangeOneCard(playerCardIndex: Int, middleCardIndex: Int) {
        val game = rootService.currentGame!!
        val cardImageLoader = CardImageLoader()
        handCardsSelected[playerCardIndex] = false
        middleCardsSelected[middleCardIndex] = false
        initializeTripleDeckView(game.middleDeck, middleDeck, middleCardsSelected, false, cardImageLoader)
        this.playerCardIndex = -1
        this.middleCardIndex = -1
    }

    /**
     * perform refreshes that are necessary after all cards changed
     */
    override fun refreshOnChangeAllCards() {
        val game = rootService.currentGame!!
        val cardImageLoader = CardImageLoader()
        for (i in 0..2) {
            handCardsSelected[i] = false
            middleCardsSelected[i] = false
        }
        initializeTripleDeckView(game.middleDeck, middleDeck, middleCardsSelected, false, cardImageLoader)
        this.playerCardIndex = -1
        this.middleCardIndex = -1
    }

    /**
     * perform refreshes that are necessary after the turn is over
     *
     * @param hasGameEnded is true if the game is over, false otherwise
     */
    override fun refreshOnEndTurn(hasGameEnded: Boolean) {
        if (!hasGameEnded) {
            val game = rootService.currentGame!!
            val cardImageLoader = CardImageLoader()
            middleCardIndex = -1
            playerCardIndex = -1
            handCardsSelected.replaceAll { false }
            middleCardsSelected.replaceAll { false }
            initializeTripleDeckView(game.middleDeck, middleDeck, middleCardsSelected, false, cardImageLoader)
            initializeTripleDeckView(
                game.players[game.activePlayerIndex].handDeck, handDeck, handCardsSelected, true,
                cardImageLoader, cardHeight = 226, cardWidth = 156
            )
            activePlayerLabel.text = "Player ${game.activePlayerIndex + 1}: ${playerNames!![game.activePlayerIndex]}"
        }
    }
}