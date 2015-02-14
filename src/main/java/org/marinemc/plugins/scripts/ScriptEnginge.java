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

import org.json.JSONObject;
import org.json.JSONTokener;
import org.marinemc.logging.Logging;
import org.marinemc.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created 2015-01-31 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ScriptEnginge {

    public final File scriptFolder;
    private File[] rawScripts;
    private Script[] scripts;

    public ScriptEnginge(final File scriptFolder) {
        this.scriptFolder = scriptFolder;
    }

    public void load() {
        boolean created = false;
        if (!scriptFolder.exists()) {
            try {
                if (!scriptFolder.mkdir()) {
                    Logging.getLogger().error("Could not create the script folder: " + scriptFolder);
                } else {
                    created = true;
                }
            } catch (final Throwable e) {
                Logging.getLogger().error("Could not create the script folder: " + scriptFolder, e);
                created = false;
            }
        } else {
            created = true;
        }
        if (!created) {
            throw new RuntimeException("Script Folder could not be created -> Stopping the script engine");
        }
        Logging.getLogger().log("Script Folder: " + scriptFolder.getName());
        File[] scripts = getContents(scriptFolder);
        if (scripts == null) {
            Logging.getLogger().log("Found no scripts -> Disabling script engine");
        } else {
            this.rawScripts = scripts;
        }
        // TODO REMOVE
        for (final File s : scripts) {
            Logging.getLogger().log(s.getName());
        }
    }

    public void loadScripts() {
        for (int i = 0; i < rawScripts.length; i++) {
            try {
                scripts[i] = getScript(rawScripts[i]);
                Logging.getLogger().log("Loaded script: " + scripts[i]);
            } catch (final ScriptEngineException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private Script getScript(final File file) throws ScriptEngineException {
        List<File> files;
        try {
            files = Arrays.asList(file.listFiles());
        } catch (final Exception e) {
            throw new ScriptEngineException("Could not list contents for \"" + file.getName() + "\"", e);
        }

        File main = null;
        for (final File f : files) {
            if (f.getName().equalsIgnoreCase("script.json")) {
                main = f;
                break;
            }
        }
        if (main == null) {
            throw new ScriptEngineException("No \"script.json\" found in script folder \"" + file.getName() + "\"");
        }
        JSONTokener tokener;
        FileInputStream stream;
        try {
            stream = new FileInputStream(main);
            tokener = new JSONTokener(stream);
        } catch (final FileNotFoundException e) {
            throw new ScriptEngineException("File not found...", e);
        }
        JSONObject object = new JSONObject(tokener);
        Assert.AssertJSONChild(object, "main");
        String m = object.getString("main");
        main = null;
        for (final File f : files) {
            if (f.getName().equalsIgnoreCase(m)) {
                main = f;
                break;
            }
        }
        if (main == null) {
            throw new ScriptEngineException("No \"" + m + "\" found i nscript folder \"" + file.getName() + "\"");
        }
        return new Script(file.getName(), main.toString(), files.toArray(new File[files.size()]));
    }

    private File[] getContents(final File file) {
        if (!file.isDirectory()) {
            return null;
        }
        Collection<File> fileList = new ArrayList<>();
        File[] files = file.listFiles();
        if (files == null) {
            return new File[0];
        }
        for (final File f : files) {
            if (f.exists() && f.isDirectory()) {
                fileList.add(f);
            }
        }
        return fileList.toArray(new File[fileList.size()]);
    }

    public static class ScriptEngineException extends RuntimeException {

        public ScriptEngineException(final String error) {
            super("Script Engine> " + error);
        }

        public ScriptEngineException(final String error, final Throwable cause) {
            super("Script Engine> " + error, cause);
        }
    }
}
