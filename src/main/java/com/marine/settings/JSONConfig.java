package com.marine.settings;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 * Created 2014-12-03 for MarineStandalone
 *
 * @author Citymonstret
 */
public class JSONConfig {

    public final JSONObject map;
    private final File path, file;
    private final String name;

    public JSONConfig(File file) {
        this.path = file.getParentFile();
        this.file = file;
        this.name = file.getName();
        this.map = new JSONObject();
    }

    public JSONConfig(File path, String name) {
        this.path = path;
        this.name = name;
        if (!path.exists()) {
            if (!path.mkdirs()) {
                throw new RuntimeException("Could not create parent folders for " + name + ".json");
            }
        }
        this.file = new File(path + File.separator + name + ".json");
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Could not create " + name + ".json");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.map = new JSONObject();
    }

    public void saveFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            map.write(writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            org.json.simple.JSONObject object = (org.json.simple.JSONObject) new JSONParser().parse(reader);
            for (Map.Entry me : (Set<Map.Entry>) object.entrySet()) {
                object.put(me.getKey(), me.getValue());
            }
            reader.close();
        } catch (ParseException | IOException e) {
            if (!(e instanceof ParseException)) e.printStackTrace();
        }
    }

}
