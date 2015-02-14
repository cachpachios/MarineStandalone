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

package org.marinemc.util;

import org.marinemc.game.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created 2015-02-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PlayerStringFactory {

    private Collection<Variable> variables;

    public PlayerStringFactory() {
        this.variables = new ArrayList<>();
        this.variables.addAll(getDefaultVariables());
    }

    public String format(final Player p, String s) {
        for (final Variable v : variables) {
            s = s.replace(v.variable, v.getString(p));
        }
        return s;
    }

    public List getDefaultVariables() {
        return Arrays.asList(
                new Variable("%name") {
                    @Override
                    public String getString(Player p) {
                        return p.getUserName();
                    }
                },
                new Variable("%ip") {
                    @Override
                    public String getString(Player p) {
                        return p.getClient().getAdress().getHostName();
                    }
                }
        );
    }

    public static abstract class Variable {
        private final String variable;

        public Variable(final String variable) {
            this.variable = variable;
        }

        public abstract String getString(final Player p);
    }

}
