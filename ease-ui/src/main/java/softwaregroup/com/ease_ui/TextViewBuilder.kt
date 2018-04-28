package softwaregroup.com.ease_ui

import android.content.Context
import android.support.annotation.CallSuper
import android.widget.EditText
import android.widget.TextView

const val DEFAULT_TEXT_VIEW_TEXT_SIZE_SP = 14F

abstract class AbstractTextViewBuilder<T : TextView>(context: Context) : ViewBuilder<T>(context) {

    var text: String? = null
    var hint: String? = null

    var textSizeSp: Float = DEFAULT_TEXT_VIEW_TEXT_SIZE_SP

    @CallSuper
    override fun setViewProperties(view: T) {
        view.text = text
        view.hint = hint
        view.textSize = textSizeSp
    }
}

open class TextViewBuilder(context: Context) : AbstractTextViewBuilder<TextView>(context) {
    override fun createView(): TextView = TextView(getContext())
}

class EditTextViewBuilder(context: Context) : AbstractTextViewBuilder<EditText>(context) {
    override fun createView() = EditText(getContext())
}