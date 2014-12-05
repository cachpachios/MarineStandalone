package com.marine.net.play.clientbound;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.player.Player;

import java.io.IOException;

/**
 * Created 2014-12-05 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerListItemPacket extends Packet {

    private final Action action;
    private final int length;
    private final Player player;

    public PlayerListItemPacket(Action action, int length, Player player) {
        this.action = action;
        this.length = length;
        this.player = player;
    }

    @Override
    public int getID() {
        return 0x38;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData data = new ByteData();
        data.writeInt(action.getActionID());
        data.writeInt(1);
        data.writeLong(player.getUUID().getMostSignificantBits());
        data.writeLong(player.getUUID().getLeastSignificantBits());
        switch (action) {
            case ADD_PLAYER:
            {
                data.writeUTF8(player.getName());
                data.writeInt(0);
                data.writeInt(player.getGamemode().getID());
                data.writeInt(10);
                data.writeBoolean(false);
            }
            break;
            default:
                return;
        }
        stream.write(getID(), data.getBytes());
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }


    public static enum Action {
        ADD_PLAYER(0),
        UPDATE_GAME_MODE(1),
        UPDATE_LATENCY(2),
        UPDATE_DISPLAY_NAME(3),
        REMOVE_PLAYER(4);

        private final int actionID;

        Action(final int actionID) {
            this.actionID = actionID;
        }

        public int getActionID() {
            return this.actionID;
        }
    }
}
