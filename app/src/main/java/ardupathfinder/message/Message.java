package ardupathfinder.message;

import java.util.Date;

import static ardupathfinder.message.MessageProtocol.*;

/**
 * Created by jun on 15. 11. 24..
 */
public class Message {

    private int frontDistance;
    private int leftDistance;
    private int rightDistance;

    private int headDegree;

    private Date date;

    protected Message(MessageProtocol mp) {
        frontDistance = mp.get(2) > MAX_DISTANCE ? -1 : mp.get(2);
        leftDistance = mp.get(3) > MAX_DISTANCE ? -1 : mp.get(3);
        rightDistance = mp.get(4) > MAX_DISTANCE ? -1 : mp.get(4);
        headDegree = mp.get(5);

        date = new Date();
    }

    public int getFrontDistance() {
        return frontDistance;
    }

    public int getLeftDistance() {
        return leftDistance;
    }

    public int getRightDistance() {
        return rightDistance;
    }

    public int getHeadDegree() {
        return headDegree;
    }

    public Date getDate() {
        return date;
    }

    public static Message parse(String rawMessage) throws ProtocolBrokenException, NumberFormatException {
        MessageProtocol mp = new MessageProtocol(rawMessage);
        if (mp.isStraight())
            return new StraightMessage(mp);
        else
            return new TurnMessage(mp);
    }

    @Override
    public String toString() {
        return String.format("Message { fDis: %d, lDis: %d, rDis: %d, headD: %d }", frontDistance, leftDistance, rightDistance, headDegree);
    }
}
