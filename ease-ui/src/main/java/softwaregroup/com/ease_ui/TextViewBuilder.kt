package softwaregroup.com.ease_ui

import android.content.Context
import android.support.annotation.CallSuper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

const val DEFAULT_TEXT_VIEW_TEXT_SIZE_SP = 14F

@ViewDsl
abstract class AbstractTextViewBuilder<T : TextView, LP : VGLoutParam>(context: Context, lpp: LPP<LP>) : AbstractViewBuilder<T, LP>(context, lpp) {
    var text: String? = null
    var hint: String? = null

    var textSizeSp: Float = DEFAULT_TEXT_VIEW_TEXT_SIZE_SP

    @CallSuper
    override fun setViewProperties(view: T) {
        super.setViewProperties(view)
        view.text = text
        view.hint = hint
        view.textSize = textSizeSp
    }
}

@ViewDsl
open class TextViewBuilder<LP : VGLoutParam>(context: Context, lpp: LPP<LP>) : AbstractTextViewBuilder<TextView, LP>(context, lpp) {
    override fun createView(): TextView = TextView(getContext())
}

class EditTextViewBuilder<LP : VGLoutParam>(context: Context, lpp: LPP<LP>) : AbstractTextViewBuilder<EditText, LP>(context, lpp) {
    override fun createView() = EditText(getContext())
}

class ButtonViewBuilder<LP : VGLoutParam>(context: Context, lpp: LPP<LP>) : AbstractTextViewBuilder<Button, LP>(context, lpp) {
    var onClickListener: View.OnClickListener? = null

    override fun createView() = Button(getContext())

    override fun setViewProperties(view: Button) {
        super.setViewProperties(view)
        view.setOnClickListener(onClickListener)
    }
}