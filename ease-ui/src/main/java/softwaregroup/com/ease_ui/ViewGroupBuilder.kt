package softwaregroup.com.ease_ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

abstract class ViewGroupBuilder<Y : ViewGroup>(context: Context) : ViewBuilder<Y>(context)

class LinerLayoutBuilder(context: Context) : ViewGroupBuilder<LinearLayout>(context) {
    private val children: ArrayList<View> = ArrayList()

    override fun setViewProperties(view: LinearLayout) {
        view.layoutParams = getLayoutParams()
    }

    override fun createView() = LinearLayout(getContext())

    fun textView(attrs: TextViewBuilder.() -> Unit) {
        children.add(TextViewBuilder(getContext()).apply(attrs).build())
    }
}