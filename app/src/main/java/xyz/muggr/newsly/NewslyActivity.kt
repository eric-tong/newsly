package xyz.muggr.newsly

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.widget.Toast

@SuppressLint("Registered")
open class NewslyActivity : AppCompatActivity() {

    var DP_1: Int = 0
    lateinit var SCREEN_SIZE: IntArray
    private var IS_LANDSCAPE: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get screen values
        DP_1 = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics))
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        SCREEN_SIZE = intArrayOf(size.x, size.y)
        IS_LANDSCAPE = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    }

    //region OnClick methods
    //=======================================================================================

    protected fun onButtonClick(view: View) {

    }

    //=======================================================================================
    //endregion

    //region Theme methods
    //=======================================================================================

    fun setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or if (isDarkTheme) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = ContextCompat.getColor(this, R.color.statusbar)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun setFullscreen() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    val isDarkTheme: Boolean
        get() = false

    //=======================================================================================
    //endregion

    //region Internet methods
    //=======================================================================================

    fun openGooglePlayPage() {
        val appPackageName = packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)))
            Toast.makeText(this, "Leave a rating", Toast.LENGTH_LONG).show()
        } catch (anfe: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)))
        }

    }

    fun openPage(url: String) {
        // TODO CHECK INTERNET CONNECTION
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    //=======================================================================================
    //endregion

    //region View methods
    //=======================================================================================

    val navbar: View
        get() = findViewById(R.id.nav_bkg)

    companion object {

        fun fadeView(view: View, from: Float, to: Float, delay: Long, duration: Long): Animator {
            val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", from, to)
            fadeAnim.duration = duration
            fadeAnim.startDelay = delay
            fadeAnim.start()
            return fadeAnim
        }
    }

    //=======================================================================================
    //endregion

}
