package com.marine.plugins;



import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

/**
 * Created 2014-12-10 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PluginFile {

    public final String name;
    public final String mainClass;
    public final String author;
    public final String version;

    public PluginFile(InputStream stream) throws Exception {
        final JSONTokener tokener = new JSONTokener(stream);
        final JSONObject object = new JSONObject(tokener);

        this.name = object.getString("name");
        this.mainClass = object.getString("main");
        this.author = object.getString("author");
        this.version = object.getString("version");

        stream.close();
    }

}
