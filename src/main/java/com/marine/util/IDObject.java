package com.marine.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created 2014-12-03 for MarineStandalone
 *
 * @author Citymonstret
 */
public interface IDObject {

    /**
     * Get the string ID
     *
     * @return String ID
     */
    public String getStringID();

    /**
     * Get the numeric ID
     *
     * @return Numeric ID
     */
    public short getNumericID();

    /**
     * Turn the item into a json object
     *
     * @return JSON Object
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException;

}
