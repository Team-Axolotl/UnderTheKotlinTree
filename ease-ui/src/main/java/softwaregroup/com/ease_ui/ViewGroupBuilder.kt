package softwaregroup.com.ease_ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.lang.ref.WeakReference

abstract class ViewGroupBuilder<Y : ViewGroup>(context: Context) : ViewBuilder<Y>() {
    private val contextRef = WeakReference(context)
    protected fun getContext() = contextRef.get()!!
}

class LinerLayoutBuilder(context: Context) : ViewGroupBuilder<LinearLayout>(context) {
    private val children: ArrayList<View> = ArrayList()

    fun textView(key: String, attrs: TextViewBuilder.() -> Unit) {
        children.add(TextViewBuilder().apply(attrs).createView(getContext()))
    }

    override fun createView(context: Context): LinearLayout {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        for (view in children) layout.addView(view)
        return layout
    }
}