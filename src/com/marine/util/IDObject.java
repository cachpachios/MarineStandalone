package com.marine.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created 2014-12-03 for MarineStandalone
 *
 * @author Citymonstret
 */
public interface IDObject {

    public String getStringID();

    public short getNumericID();

    public JSONObject toJSON() throws JSONException;

}
