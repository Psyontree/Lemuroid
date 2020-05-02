package com.swordfish.touchinput.pads

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import com.swordfish.touchinput.controller.R
import com.swordfish.touchinput.events.EventsTransformers
import com.swordfish.touchinput.events.OptionType
import com.swordfish.touchinput.events.PadEvent
import com.swordfish.touchinput.views.ActionButtons
import com.swordfish.touchinput.views.DirectionPad
import com.swordfish.touchinput.views.IconButton
import com.swordfish.touchinput.views.SingleButton
import io.reactivex.Observable

class GameGearPad @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseGamePad(context, attrs, defStyleAttr,
    SemiPadConfig(R.layout.layout_gg_left, 3, 5),
    SemiPadConfig(R.layout.layout_gg_right, 3, 5)
) {

    override fun getEvents(): Observable<PadEvent> {
        return Observable.merge(
                getDirectionEvents(),
                getActionEvents(),
                getStartEvents(),
                getMenuEvents()
        )
    }

    private fun getActionEvents(): Observable<PadEvent> {
        return findViewById<ActionButtons>(R.id.actions)
                .getEvents()
                .compose(EventsTransformers.actionButtonsMap(KeyEvent.KEYCODE_BUTTON_B, KeyEvent.KEYCODE_BUTTON_A))
    }

    private fun getDirectionEvents(): Observable<PadEvent> {
        return findViewById<DirectionPad>(R.id.direction).getEvents()
                .compose(EventsTransformers.directionPadMap())
    }

    private fun getStartEvents(): Observable<PadEvent> {
        return findViewById<SingleButton>(R.id.start)
                .getEvents()
                .compose(EventsTransformers.singleButtonMap(KeyEvent.KEYCODE_BUTTON_START))
    }

    private fun getMenuEvents(): Observable<PadEvent> {
        return findViewById<IconButton>(R.id.menu)
                .getEvents()
                .compose(EventsTransformers.clickMap(OptionType.SETTINGS))
    }
}