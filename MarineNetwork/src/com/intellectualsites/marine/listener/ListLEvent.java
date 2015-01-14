package com.intellectualsites.marine.listener;

import com.intellectualsites.marine.Network;
import org.json.simple.JSONArray;
import org.marinemc.events.EventListener;
import org.marinemc.events.standardevents.ListEvent;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.logging.Logging;
import org.marinemc.net.packets.status.ListResponse;
import org.marinemc.util.StringUtils;

/**
 * Created 2014-12-26 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ListLEvent extends EventListener<ListEvent> {

    @Override
    public void listen(final ListEvent event) {
        ListResponse response = event.getResponse();
        response.setMotd(buildMotd());
        response.setSamplePlayers(buildDesc());
    }

    private JSONArray buildDesc() {
        JSONArray array = new JSONArray();
        array.add(ListResponse.getText("&c&lWe are currently in maintenance mode!"));
        return array;
    }

    private String buildMotd() {
        for (ChatColor color : ChatColor.getColors()) {
            Logging.getLogger().log("&" + color.getOldSystemID() + " = " + color.toString() + "test");
        }
        String base;
        if (Network.getState().hasMOTD()) {
            base = Network.getState().toString();
        } else {
            base = "&e&n-- &fRandom MOTD &e&n--";
        }
        return ChatColor.transform('&',
                StringUtils.format("&3MarineNetwork &9&l-> &3Now supports 1.8!\n" + base));
    }
}
