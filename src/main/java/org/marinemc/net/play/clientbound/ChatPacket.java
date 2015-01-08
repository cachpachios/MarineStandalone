///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.net.play.clientbound;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.marinemc.game.chat.ChatMessage;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 * @author Fozie
 */
public class ChatPacket extends Packet {

	private String message;
	private final int position;
	private JSONObject object;

	public ChatPacket(final String message) {
		this(message, 0);
	}

	public ChatPacket(final String message, final boolean test) {
		super(0x02, States.INGAME);
		this.message = message;
		position = 0;
		if (test)
			throw new UnsupportedOperationException(
					"JSON Testing isn't implemented yet");
	}

	public ChatPacket(final ChatMessage chat) {
		super(0x02, States.INGAME);
		message = chat.toString();
		position = 0;
	}

	public ChatPacket(final ChatMessage chat, final int position) {
		super(0x02, States.INGAME);
		message = chat.toString();
		this.position = position;
	}

	@SuppressWarnings("unchecked")
	public ChatPacket(final String message, final int position) {
		super(0x02, States.INGAME);
		if (message.startsWith("{\""))
			this.message = message;
		else {
			object = new JSONObject();
			if (message.length() == 0)
				this.message = "";
			else {
				object.put("text", message);
				this.message = object.toJSONString();
			}
		}
		this.position = position;
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
		if (message == null || message.length() == 0)
			return;

		final ByteList data = new ByteList();

		if (message.length() < Short.MAX_VALUE)
			data.writeUTF8(message);
		else
			data.writeUTF8(message.substring(0, 32766));

		data.writeByte((byte) position);

		stream.write(getID(), data);
	}

}
