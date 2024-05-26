package com.kimurashin.sdk.delegate

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty


fun <T : View> viewProvider(@IdRes idRes: Int) = ViewProviderDelegate<T>(idRes)

class ViewProviderDelegate<out T>(
    @IdRes private val idRes: Int
) {
    private var weakView: WeakReference<View>? = null

    private var view: View?
        get() = weakView?.get()
        set(value) {
            weakView = if (value == null) null else WeakReference(value)
        }

    operator fun getValue(thisRef: View, property: KProperty<*>): T {
        return findView(property) {
            thisRef.findViewById(idRes)
        }
    }

    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        view?.let { if (!it.isAttachedToWindow) view = null }
        return findView(property) {
            thisRef.findViewById(idRes)
        }
    }

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return findView(property) {
            thisRef.view?.findViewById(idRes)
        }
    }


    operator fun getValue(viewHolder: RecyclerView.ViewHolder, property: KProperty<*>): T {
        return findView(property) {
            viewHolder.itemView.findViewById(idRes)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun findView(property: KProperty<*>, crossinline initializer: () -> View?): T {
        view = view ?: initializer.invoke()
        if (view == null)
            throw IllegalStateException("View ID $idRes for ${property.name} not found")
        return view as T
    }

}