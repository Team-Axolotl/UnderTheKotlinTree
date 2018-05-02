package softwaregroup.com.ease_ui

import android.content.Context
import android.support.annotation.CallSuper
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

abstract class ViewGroupBuilder<Y : ViewGroup, CLP : LayoutParams, LP : VGLoutParam>(context: Context, lpp: LPP<LP>) : AbstractViewBuilder<Y, LP>(context, lpp) {

    abstract val childLayoutParamsProvider: LayoutParamsProvider<CLP>

    protected val children: ArrayList<View> = ArrayList()

    fun textView(attrs: TextViewBuilder<CLP>.() -> Unit): TextView {
        return TextViewBuilder(getContext(), childLayoutParamsProvider).apply(attrs).build().apply { children.add(this) }
    }

    fun button(attrs: ButtonViewBuilder<CLP>.() -> Unit): Button {
        return ButtonViewBuilder(getContext(), childLayoutParamsProvider).apply(attrs).build().apply { children.add(this) }
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

    fun editText(attrs: EditTextViewBuilder<LinearLayout.LayoutParams>.() -> Unit): EditText {
        return EditTextViewBuilder(getContext(), childLayoutParamsProvider).apply(attrs).build().apply { children.add(this) }
    }

    override fun setViewProperties(view: LinearLayout) {
        super.setViewProperties(view)
        view.orientation = orientation.intValue
    }

    override fun createView() = LinearLayout(getContext())
}


//
//internal val layoutAttributeRenderers = listOf(
//        ::linearLayoutRenderer,
//        ::relativeLayoutRenderer
//)
//
//internal fun linearLayoutRenderer(parentName: String, attrs: Map<String, String>) = if (parentName == "LinearLayout") listOf(
//        attrs.prop("gravity") { renderAttribute(NoAttr, it) },
//        attrs.prop("weight")
//) + marginLayoutRenderer(parentName, attrs) else null
//
//internal fun relativeLayoutRenderer(parentName: String, attrs: Map<String, String>) = if (parentName == "RelativeLayout") listOf(
//        attrs.func("above") { renderReference(NoAttr, it.key, it.value) },
//        attrs.func("below") { renderReference(NoAttr, it.key, it.value) },
//        attrs.func("toRightOf") { renderReference(NoAttr, "toRightOf", it.value) },
//        attrs.func("toLeftOf") { renderReference(NoAttr, "toLeftOf", it.value) },
//        attrs.func("alignLeft") { renderReference(NoAttr, "sameLeft", it.value) },
//        attrs.func("alignTop") { renderReference(NoAttr, "sameTop", it.value) },
//        attrs.func("alignRight") { renderReference(NoAttr, "sameRight", it.value) },
//        attrs.func("alignBottom") { renderReference(NoAttr, "sameBottom", it.value) },
//        attrs.func("alignParentLeft") { "alignParentLeft" * "" },
//        attrs.func("alignParentTop") { "alignParentTop" * "" },
//        attrs.func("alignParentRight") { "alignParentRight" * "" },
//        attrs.func("alignParentBottom") { "alignParentBottom" * "" },
//        attrs.func("alignParentStart") { "alignParentStart" * "" },
//        attrs.func("alignParentEnd") { "alignParentEnd" * "" },
//        attrs.func("centerInParent") { "centerInParent" * "" },
//        attrs.func("centerHorizontal") { "centerHorizontally" * "" },
//        attrs.func("centerVertical") { "centerVertically" * "" }
//
//) + marginLayoutRenderer(parentName, attrs) else null
//
//internal fun marginLayoutRenderer(parentName: String, attrs: Map<String, String>) =
//        listOf("margin", "marginLeft", "marginTop", "marginRight", "marginBottom").map {
//            attrs.prop(it) { renderDimension(NoAttr, it.key.swapCamelCase(), it.value) }
//        }
//
//private fun Map<String, String>.func(key: String, transformer: ((KeyValuePair) -> KeyValuePair?)? = null): KeyValuePair? {
//    val value = get(key)
//    return if (value != null) {
//        if (transformer != null) {
//            val result = transformer(key * value)
//            return if (result != null) (result.key + "(" + result.value + ")") * "" else null
//        } else "$key($value)" * ""
//    } else null
//}
//
//private fun Map<String, String>.prop(key: String, transformer: ((KeyValuePair) -> KeyValuePair?)? = null): KeyValuePair? {
//    val value = get(key)
//    return if (value != null) {
//        if (transformer != null) transformer(key * value) else key * value
//    } else null
//}