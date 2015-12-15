package ardupathfinder.message;

import android.util.Log;

import static ardupathfinder.message.MessageProtocol.*;

/**
 * Created by jun on 15. 11. 24..
 */
public class TurnMessage extends Message {

    private static final String TAG = "TurnMessage";
    private static final double TURN_RATIO = 320.0 / 1100.0;      // 1700ms -> 360 degree

    private int direction;
    private int wheelRotation;
    private int degree;

    protected TurnMessage(MessageProtocol mp) {
        super(mp);

        if (mp.get(0) == 0) {
            direction = TURN_LEFT;
            wheelRotation = mp.get(1);
        } else {
            direction = TURN_RIGHT;
            wheelRotation = mp.get(0);
        }
        Log.i(TAG, String.valueOf(wheelRotation*TURN_RATIO));
        evalAngle();
    }

    public int getDegree() {
        return degree;
    }

    public boolean isLeft() {
        if (direction == TURN_LEFT)
            return true;
        else
            return false;
    }

    public boolean isRight() {
        if (direction == TURN_RIGHT)
            return true;
        else
            return false;
    }

    private void evalAngle() {
        // TODO: EVAL ANGLE
        if (isLeft())
            degree = (int) (wheelRotation * TURN_RATIO);
        else
            degree = (int) (-wheelRotation * TURN_RATIO);
    }

    @Override
    public String toString() {
        return String.format("TurnMessage { degree: %d, fDis: %d, lDis: %d, rDis: %d, headD: %d }",
                getDegree(), getFrontDistance(),
                getLeftDistance(), getRightDistance(), getHeadDegree());
    }
}
