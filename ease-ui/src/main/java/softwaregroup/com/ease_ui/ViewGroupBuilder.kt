package softwaregroup.com.ease_ui

import android.content.Context
import android.support.annotation.CallSuper
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

abstract class ViewGroupBuilder<Y : ViewGroup>(context: Context) : AbstractViewBuilder<Y>(context) {
    private val children: ArrayList<View> = ArrayList()

    fun textView(attrs: TextViewBuilder.() -> Unit) : TextView {
        return TextViewBuilder(getContext()).apply(attrs).build().apply { children.add(this) }
    }

    fun editText(attrs: EditTextViewBuilder.() -> Unit) : EditText {
        return EditTextViewBuilder(getContext()).apply(attrs).build().apply { children.add(this) }
    }

    fun button(attrs: ButtonViewBuilder.() -> Unit) : Button {
        return ButtonViewBuilder(getContext()).apply(attrs).build().apply { children.add(this) }
    }

    @CallSuper
    override fun setViewProperties(view: Y) {
        super.setViewProperties(view)
        insertChildren(view)
    }

    protected open fun insertChildren(viewGroup: Y) {
        children.forEach { viewGroup.addView(it) }
    }
}

class LinerLayoutBuilder(context: Context) : ViewGroupBuilder<LinearLayout>(context) {
    enum class Orientation(val intValue: Int) {
        HORIZONTAL(LinearLayout.HORIZONTAL),
        VERTICAL(LinearLayout.VERTICAL)
    }

    val orientation: Orientation = Orientation.VERTICAL

    override fun setViewProperties(view: LinearLayout) {
        super.setViewProperties(view)
        view.orientation = orientation.intValue
    }

    override fun createView() = LinearLayout(getContext())
}