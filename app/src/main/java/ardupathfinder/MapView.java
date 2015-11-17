package ardupathfinder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by jun on 15. 11. 15..
 */
public class MapView extends View {
    private static final String TAG = "MapView";

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "Created");
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        Log.i(TAG, "onDraw()");
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        canvas.drawLine(100, 100, 1000, 1000, p);
    }
}
