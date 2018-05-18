package softwaregroup.com.ease_ui

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.CallSuper
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import softwaregroup.com.ease_ui.util.dpToPx
import softwaregroup.com.ease_ui.util.pxToDp
import java.lang.ref.WeakReference

@DslMarker
annotation class ViewDsl

@ViewDsl
abstract class AbstractViewBuilder<Y : View, LP : VGLoutParam>(context: Context, lpp: LPP<LP>) {

    private val contextRef = WeakReference(context)
    private val layoutParams = lpp.createLayoutParams()
    protected fun getContext() = contextRef.get()!!

    var id: Int = -1
    open var background: Drawable? = null

    open fun layout(params: LP.() -> Unit) {
        layoutParams.apply(params)
    }

    internal abstract fun createView(): Y

    internal fun build(): Y {
        val view = createView()
        view.layoutParams = layoutParams
        setViewProperties(view)
        return view
    }

    @CallSuper
    internal open fun setViewProperties(view: Y) {
        view.id = id
        if (background != null) view.background = background
    }
}

typealias LPP<P> = LayoutParamsProvider<P>

interface LayoutParamsProvider<P : ViewGroup.LayoutParams> {
    fun createLayoutParams(): P
}

typealias VGLoutParam = ViewGroup.LayoutParams

var VGLoutParam.heightDp: Float
    set(value) {
        this.height = dpToPx(value, Resources.getSystem().displayMetrics)
    }
    get() = pxToDp(this.height, Resources.getSystem().displayMetrics)

var VGLoutParam.widthDp: Float
    set(value) {
        this.width = dpToPx(value, Resources.getSystem().displayMetrics)
    }
    get() = pxToDp(this.width, Resources.getSystem().displayMetrics)

fun linearLayout(context: Context, attrs: LinerLayoutBuilder<ViewGroup.LayoutParams>.() -> Unit) = LinerLayoutBuilder(context, llp).apply(attrs).build()
fun relativeLayout(context: Context, attrs: RelativeLayoutBuilder<ViewGroup.LayoutParams>.() -> Unit) = RelativeLayoutBuilder(context, llp).apply(attrs).build()

private val llp = object : LayoutParamsProvider<ViewGroup.LayoutParams> {
    override fun createLayoutParams(): ViewGroup.LayoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}