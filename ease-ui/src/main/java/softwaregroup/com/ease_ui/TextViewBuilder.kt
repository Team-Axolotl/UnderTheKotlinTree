package softwaregroup.com.ease_ui

import android.content.Context
import android.widget.EditText
import android.widget.TextView

abstract class AbstractTextViewBuilder<T : TextView> : ViewBuilder<T>() {
    companion object {
        const val DEFAULT_TEXT_VIEW_TEXT_SIZE_SP = 14F
    }

    var text: String? = null
    var hint: String? = null
    var textSizeSp: Float = DEFAULT_TEXT_VIEW_TEXT_SIZE_SP

    override fun build(context: Context): T {
        val view = super.build(context)

        view.text = text
        view.hint = hint
        view.textSize = textSizeSp

        return view
    }
}

open class TextViewBuilder : AbstractTextViewBuilder<TextView>() {
    override fun createView(context: Context): TextView = TextView(context)
}

class EditTextViewBuilder : AbstractTextViewBuilder<EditText>() {
    override fun createView(context: Context): EditText = EditText(context)
}