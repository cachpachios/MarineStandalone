package com.marine.settings;

/**
 * Created 2014-12-11 for MarineStandalone
 *
 * @author Citymonstret
 */
public class JSONConfigException extends RuntimeException {

    public JSONConfigException(String config, String error) {
        super("JSON Config: " + config + " caused an exception (\"" + error + "\")");
    }

    public JSONConfigException(String config, String error, Throwable exception) {
        super("JSON Config: " + config + " caused an exceiption (\"" + error + "\")", exception);
    }
}
