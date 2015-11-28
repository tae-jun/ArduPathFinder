package ardupathfinder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ardupathfinder.message.Message;
import ardupathfinder.message.StraightMessage;
import ardupathfinder.message.TurnMessage;

import static java.lang.Math.*;

/**
 * Created by jun on 15. 11. 15..
 */
public class MapView extends View {

    private static final String TAG = "MapView";
    private static final float SCALE_RATIO = 1.f;

    private float[] pos = {0f, 0f};
    private double radian = toRadians(90.0);
    private float scale = 0.8f;
    private float tranX = 0.f;
    private float tranY = 0.f;

    private Paint arduPaint;
    Path arduPath = new Path();
    PathMeasure pm = new PathMeasure(arduPath, false);

    private Paint obPaint;
    private float obRadius = 10.f;
    Path obPath = new Path();


    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "Created");
        setWillNotDraw(false);

        arduPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arduPaint.setStyle(Paint.Style.STROKE);
        arduPaint.setStrokeWidth(4);
        arduPaint.setColor(Color.DKGRAY);

        obPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        obPaint.setColor(Color.RED);
    }

    public void draw(Message msg) {
        Log.i(TAG, "draw() <-" + msg.toString());
        // Draw ArduPathFinder's foot print
        if (msg instanceof StraightMessage)
            drawStraight((StraightMessage) msg);
        else if (msg instanceof TurnMessage)
            drawTurn((TurnMessage) msg);
        // Update current absolute position
        updatePosition();
        // Draw obstacle
        drawObstacle(msg);
        // Reload this view
        postInvalidate();
    }

    private void updatePosition() {
        pm.setPath(arduPath, false);
        pm.getPosTan(pm.getLength(), pos, null);
    }

    private void drawObstacle(Message msg) {
        int fDis = msg.getFrontDistance();
        int lDis = msg.getLeftDistance();
        int rDis = msg.getRightDistance();

        float x;
        float y;
        // Front
        if (fDis > 0) {
            double headRadian = toRadians(msg.getHeadDegree() + toDegrees(radian) - 90);
            x = (float) (pos[0] + fDis * cos(headRadian));
            y = (float) (pos[1] - fDis * sin(headRadian));
            obPath.addCircle(x, y, obRadius, Path.Direction.CCW);
        }
        // Left
        if (lDis > 0) {
            x = (float) (pos[0] - lDis * sin(radian));
            y = (float) (pos[1] - lDis * cos(radian));
            obPath.addCircle(x, y, obRadius, Path.Direction.CCW);
        }
        // Right
        if (rDis > 0) {
            x = (float) (pos[0] + rDis * sin(radian));
            y = (float) (pos[1] + rDis * cos(radian));
            obPath.addCircle(x, y, obRadius, Path.Direction.CCW);
        }
    }

    private void drawTurn(TurnMessage tm) {
        radian = toRadians(tm.getDegree() + toDegrees(radian));
    }

    private void drawStraight(StraightMessage sm) {
        // Compute new relative position
        float newX = (float) (sm.getMovedDistance() * cos(radian));
        float newY = (float) (-sm.getMovedDistance() * sin(radian));
        // Add new position to arduPath
        arduPath.rLineTo(newX, newY);
        // Check out of canvas
        if (isOutOfCanvas()) {
            // Compute scale
            scale *= SCALE_RATIO;
            scale *= SCALE_RATIO;
            // Compute translate
            tranX = (getScaledWidth() - getWidth()) / 2;
            tranY = (getScaledHeight() - getHeight()) / 2;
            // Set line thickness
            arduPaint.setStrokeWidth(4 / scale);

            Log.i(TAG, String.format("OutOfCanvas { scale: %f, tranX: %f, tranY: %f}", scale, tranX, tranY));
        }
    }

    private boolean isOutOfCanvas() {
        return pos[0] < -tranX ||
                pos[0] > getWidth() + tranX ||
                pos[1] < -tranY ||
                pos[1] > getHeight() + tranY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(scale, scale);
        canvas.translate(tranX, tranY);

        canvas.drawPath(arduPath, arduPaint);
        canvas.drawPath(obPath, obPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        arduPath.reset();
        arduPath.moveTo(getWidth() / 2, getHeight() * 0.5f);

        obPath.reset();
        obPath.moveTo(getWidth() / 2, getHeight() * 0.5f);
    }

    private int getScaledWidth() {
        return (int) (getWidth() / scale);
    }

    private int getScaledHeight() {
        return (int) (getHeight() / scale);
    }
}
