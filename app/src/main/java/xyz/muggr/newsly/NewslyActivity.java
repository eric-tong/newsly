package xyz.muggr.newsly;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

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

}
