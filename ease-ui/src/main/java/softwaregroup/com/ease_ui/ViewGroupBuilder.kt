package softwaregroup.com.ease_ui

import android.content.Context
import android.support.annotation.CallSuper
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*

abstract class ViewGroupBuilder<Y : ViewGroup, CLP : LayoutParams, LP : VGLoutParam>(context: Context, lpp: LPP<LP>) : AbstractViewBuilder<Y, LP>(context, lpp) {

    abstract val childLayoutParamsProvider: LayoutParamsProvider<CLP>

    protected val children: ArrayList<View> = ArrayList()

    fun textView(attrs: TextViewBuilder<CLP>.() -> Unit): TextView {
        return TextViewBuilder(getContext(), childLayoutParamsProvider).apply(attrs).build().apply { children.add(this) }
    }

    fun button(attrs: ButtonViewBuilder<CLP>.() -> Unit): Button {
        return ButtonViewBuilder(getContext(), childLayoutParamsProvider).apply(attrs).build().apply { children.add(this) }
    }

    fun editText(attrs: EditTextViewBuilder<CLP>.() -> Unit): EditText {
        return EditTextViewBuilder(getContext(), childLayoutParamsProvider).apply(attrs).build().apply { children.add(this) }
    }

    fun linerLayout(attrs: LinerLayoutBuilder<CLP>.() -> Unit): LinearLayout{
        return LinerLayoutBuilder(getContext(), childLayoutParamsProvider).apply(attrs).build().apply { children.add(this) }
    }

    fun relativeLayout(attrs: RelativeLayoutBuilder<CLP>.() -> Unit): RelativeLayout{
        return RelativeLayoutBuilder(getContext(), childLayoutParamsProvider).apply(attrs).build().apply { children.add(this) }
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

class LinerLayoutBuilder<LP : VGLoutParam>(context: Context, lpp: LPP<LP>) : ViewGroupBuilder<LinearLayout, LinearLayout.LayoutParams, LP>(context, lpp) {

    override val childLayoutParamsProvider = object : LayoutParamsProvider<LinearLayout.LayoutParams>{
        override fun createLayoutParams() = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

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

class RelativeLayoutBuilder<LP : VGLoutParam>(context: Context, lpp: LPP<LP>) : ViewGroupBuilder<RelativeLayout, RelativeLayout.LayoutParams, LP>(context, lpp){

    override val childLayoutParamsProvider = object : LayoutParamsProvider<RelativeLayout.LayoutParams>{
        override fun createLayoutParams() = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun createView() = RelativeLayout(getContext())
}