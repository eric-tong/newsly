package xyz.muggr.newsly;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

public class NewslyActivity extends AppCompatActivity {

    public int DP_1;
    public int[] SCREEN_SIZE;
    private boolean IS_LANDSCAPE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get screen values
        DP_1 = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        SCREEN_SIZE = new int[]{size.x, size.y};
        IS_LANDSCAPE = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

    }

    //region OnClick methods
    //=======================================================================================

    protected void onButtonClick(View view) {

    }

    //=======================================================================================
    //endregion

    //region Theme methods
    //=======================================================================================

    void setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | (isDarkTheme() ? 0 : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusbar));
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    boolean isDarkTheme(){
        return false;
    }

    //=======================================================================================
    //endregion

    //region Internet methods
    //=======================================================================================

    public void openGooglePlayPage() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            Toast.makeText(this, "Leave a rating", Toast.LENGTH_LONG).show();
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void openPage(String url) {
        // TODO CHECK INTERNET CONNECTION
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    //=======================================================================================
    //endregion

}
