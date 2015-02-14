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

package org.marinemc.plugins.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created 2015-01-31 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Script {

    private final String name;
    private final File[] files;
    private final String mainFile;

    public Script(final String name, final String mainFile, final File... files) {
        this.name = name;
        this.mainFile = mainFile;
        this.files = files;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public File[] getFiles() {
        return this.files;
    }

    public String getMainFile() {
        return this.mainFile;
    }

    public String getScript() throws Throwable {
        final BufferedReader reader = new BufferedReader(new FileReader(new File(getMainFile())));
        String line;
        StringBuilder c = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            c.append(line);
        }
        return c.toString();
    }
}
