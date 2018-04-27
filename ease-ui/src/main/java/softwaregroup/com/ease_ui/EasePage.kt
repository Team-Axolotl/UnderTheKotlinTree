package softwaregroup.com.ease_ui

import android.view.View
import com.fasterxml.jackson.annotation.JsonIgnore
import kotlin.collections.ArrayList


@DslMarker
annotation class PageDsl

//================================================================================================//
// START BUILDER DEFINITIONS ---------------------------------------------------------------------//
//================================================================================================//
@PageDsl
interface FieldBuilder<T> {
    fun build(): T
}

class PageBuilder(val id: String) : FieldBuilder<Page> {
    private val fields = ArrayList<Field>()

    fun textField(id: String, attrs: TextFieldBuilder.() -> Unit) {
        fields.add(TextFieldBuilder(id).apply(attrs).build())
    }

    fun button(attrs: ButtonFieldBuilder.() -> Unit) {
        fields.add(ButtonFieldBuilder().apply(attrs).build())
    }

    override fun build() = Page(id, fields)
}

class TextFieldBuilder(val id: String) : FieldBuilder<TextField> {
    var text: String? = null
    var hint: String? = null
    var textSize: Int = 14

    override fun build() = TextField(id, text, hint, textSize)
}

class ButtonFieldBuilder : FieldBuilder<ButtonField> {
    var text: String? = null
    var onClick: View.OnClickListener? = null

    override fun build() = ButtonField(text, onClick)
}
//================================================================================================//
// END BUILDER DEFINITIONS -----------------------------------------------------------------------//
//================================================================================================//

//================================================================================================//
// START VIEW DEFINITIONS ------------------------------------------------------------------------//
//================================================================================================//
abstract class Field

abstract class InputField(@JsonIgnore val id: String) : Field()
abstract class ClickableField(@JsonIgnore val clickListener: View.OnClickListener? = null) : Field()

class Page(
        id: String,
        val fields: ArrayList<Field> // todo - consider whether a LinkedHashMap<id, Field> is more appropriate, or keep the ids in the views themselves
) : InputField(id) // todo - touch-up class hierarchy. A page is not an input per se, now is it...

class TextField(
        id: String,
        val text: String?,
        val hint: String?,
        val textSize: Int
) : InputField(id)

class ButtonField(
        val text: String?,
        onClick: View.OnClickListener?
) : ClickableField(onClick)
//================================================================================================//
// END VIEW DEFINITIONS --------------------------------------------------------------------------//
//================================================================================================//

/** Root element of a [Page] declaration */
fun page(id: String, attrs: PageBuilder.() -> Unit): Page = PageBuilder(id).apply(attrs).build()


// Example createView \/ here. Just add water. ... ... or uncomment. whatever.
//val builtPage = page("testPage") {
//    textField("testField1") {
//        text = "testText"
//        hint = "testHint"
//    }
//
//    button {
//        onClick = View.OnClickListener {
//            Log.wtf("wow!", "This might actual;ly work?! (O_o)")
//        }
//    }
//}


// This is an example of an invalid page createView.
// The @PageDsl annotation blocks the out-of place calls to the createView methods like textField(id, attr)
//val failedPage = page("testPage") {
//    textField("testField1") {
//        text = "testText"
//        hint = "testHint"
//
//        button {
//            onClick = View.OnClickListener {
//                Log.wtf("dang!", "This ain't gonna happen m8!")
//            }
//        }
//    }
//
//    button {
//        onClick = View.OnClickListener {
//            Log.wtf("Nah", "fo real!")
//        }
//    }
//}