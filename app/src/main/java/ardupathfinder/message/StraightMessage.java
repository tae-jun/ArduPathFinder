package ardupathfinder.message;

/**
 * Created by jun on 15. 11. 24..
 */
public class StraightMessage extends Message {

    private static final double RUN_RATIO = 250.0 / 1000.0;
    private int movedDistance;

    protected StraightMessage(MessageProtocol mp) {
        super(mp);
        // TODO: EVAL MOVED DISTANCE
        movedDistance = (int) ((mp.get(0) + mp.get(1)) * RUN_RATIO / 2.0);
    }

    public int getMovedDistance() {
        return movedDistance;
    }

    @Override
    public String toString() {
        return String.format("StraightMessage { MovedDistance: %d, fDis: %d, lDis: %d, rDis: %d, headD: %d }",
                getMovedDistance(), getFrontDistance(),
                getLeftDistance(), getRightDistance(), getHeadDegree());
    }
}
