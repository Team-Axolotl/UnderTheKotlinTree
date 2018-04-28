package softwaregroup.com.ease_ui

import android.content.Context
import android.support.annotation.CallSuper
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

abstract class ViewGroupBuilder<Y : ViewGroup>(context: Context) : ViewBuilder<Y>(context) {
    private val children: ArrayList<View> = ArrayList()

    fun textView(attrs: TextViewBuilder.() -> Unit) {
        this.children.add(TextViewBuilder(getContext()).apply(attrs).build())
    }

    fun editText(attrs: EditTextViewBuilder.() -> Unit) {
        this.children.add(EditTextViewBuilder(getContext()).apply(attrs).build())
    }

    fun button(attrs: ButtonViewBuilder.() -> Unit) {
        this.children.add(ButtonViewBuilder(getContext()).apply(attrs).build())
    }

    @CallSuper
    override fun setViewProperties(view: Y) {
        addChildren(view)
    }

    protected open fun addChildren(viewGroup: Y) {
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