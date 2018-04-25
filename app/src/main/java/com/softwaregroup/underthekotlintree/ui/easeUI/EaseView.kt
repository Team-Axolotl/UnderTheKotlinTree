package com.softwaregroup.underthekotlintree.ui.easeUI

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.ref.WeakReference
import java.util.AbstractMap

@DslMarker
annotation class ViewDsl

@ViewDsl
interface ViewBuilder<Y : View> {
    fun build(context: Context): Y
}

interface Mappable<VT>{
    fun getKey() : String
    fun getValue() : VT

    fun getEntry() = AbstractMap.SimpleEntry<String, VT>(getKey(), getValue())
    fun getPair() = Pair(getKey(), getValue())
}

/** Specific abstract class implementation of [ViewBuilder] for the root [ViewGroup]
 *  to facilitate forcing use of same [Context] for all child [Views][View] */
abstract class ViewGroupBuilder<Y : ViewGroup>(context: Context) : ViewBuilder<Y> {
    private val contextRef = WeakReference(context)
    protected fun getContext() = contextRef.get()!!
}

// todo - maybe wrap in a ScrollView? Also use ConstraintLayout.
class LinerLayoutBuilder(context: Context) : ViewGroupBuilder<LinearLayout>(context) {
    private val children: ArrayList<View> = ArrayList()

    fun textView(key: String, attrs: TextViewBuilder.() -> Unit) {
        children.add(TextViewBuilder(key).apply(attrs).build(getContext()))
    }

    override fun build(context: Context): LinearLayout {
        // todo - decide whether to leave it blow with a KotlinNPE or just abort build and clean-up (if context ref GC'd)
        val layout = LinearLayout(context)

        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        for (view in children) layout.addView(view)

        return layout
    }
}

class TextViewBuilder(val key: String) : ViewBuilder<TextView> {

    companion object {
        const val DEFAULT_TEXT_VIEW_TEXT_SIZE_SP = 14F
    }

    var widthDp = ViewGroup.LayoutParams.MATCH_PARENT
    var heightDp = ViewGroup.LayoutParams.WRAP_CONTENT

    var text: String? = null
    var hint: String? = null
    var textSizeSp: Float = DEFAULT_TEXT_VIEW_TEXT_SIZE_SP

    override fun build(context: Context): TextView {
        val view = TextView(context)

        view.tag = key

        view.layoutParams = ViewGroup.LayoutParams(
                if (heightDp < 0) heightDp else dpToPx(heightDp.toFloat(), context),
                if (widthDp < 0) widthDp else dpToPx(widthDp.toFloat(), context)
        )

        view.text = text
        view.hint = hint
        view.textSize = textSizeSp

        return view
    }
}

fun linearLayout(context: Context, attrs: LinerLayoutBuilder.() -> Unit) = LinerLayoutBuilder(context).apply(attrs).build(context)
// the duplicate use of the context object is unfortunate, but doesn't make a functional difference

