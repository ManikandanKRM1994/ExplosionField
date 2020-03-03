package com.krm.explosionfield

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.krm.explosionfield.ExplosionField.Companion.attach2Window

class MainActivity : Activity() {
    private var mExplosionField: ExplosionField? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mExplosionField = attach2Window(this)
        addListener(findViewById(R.id.root))
    }

    private fun addListener(root: View) {
        if (root is ViewGroup) {
            for (i in 0 until root.childCount) {
                addListener(root.getChildAt(i))
            }
        } else {
            root.isClickable = true
            root.setOnClickListener { v ->
                mExplosionField!!.explode(v)
                v.setOnClickListener(null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_reset) {
            val root = findViewById<View>(R.id.root)
            reset(root)
            addListener(root)
            mExplosionField!!.clear()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun reset(root: View) {
        if (root is ViewGroup) {
            for (i in 0 until root.childCount) {
                reset(root.getChildAt(i))
            }
        } else {
            root.scaleX = 1f
            root.scaleY = 1f
            root.alpha = 1f
        }
    }
}