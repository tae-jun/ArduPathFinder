package ardupathfinder.message;

import java.util.ArrayList;

/**
 * Created by jun on 15. 11. 24..
 */
class MessageProtocol extends ArrayList<Integer> {
    static final int TURN_LEFT = 0;
    static final int TURN_RIGHT = 1;

    static final int MAX_DISTANCE = 100;
    static final int PROTOCOL_LENGTH = 6;

    static final String DELIMITER = ",";

    MessageProtocol(String rawMessage) throws ProtocolBrokenException, NumberFormatException {
        String[] protocs = rawMessage.split(DELIMITER);

        if (protocs.length != PROTOCOL_LENGTH)
            throw new ProtocolBrokenException();

        for (String el : protocs)
            this.add(Integer.parseInt(el));
    }

    boolean isStraight() {
        if (this.get(0) == 0 || this.get(1) == 0)
            return false;
        else
            return true;
    }
}
