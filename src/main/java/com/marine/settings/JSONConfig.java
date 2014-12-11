package com.marine.settings;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 2014-12-03 for MarineStandalone
 *
 * @author Citymonstret
 */
public class JSONConfig {

    public JSONObject map;
    public File file, path;
    public String name;

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
        boolean created = false;
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Could not create " + name + ".json");
                }
                created = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!created) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                JSONTokener tokener = new JSONTokener(reader);
                this.map = new JSONObject(tokener);
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.map = new JSONObject();
        }
        for(Map.Entry<String, Object> entry : defaultValues().entrySet()) {
            setIfNull(entry.getKey(), entry.getValue());
        }
    }

    public Map<String, Object> defaultValues() {
        return new HashMap<String, Object>();
    }

    public void setIfNull(String s, Object o) {
        if (map.isNull(s))
            map.put(s, o);
    }

    public void set(String s, Object o) {
        if (!map.isNull(s))
            map.remove(s);
        try {
            map.put(s, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            map.write(writer);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
