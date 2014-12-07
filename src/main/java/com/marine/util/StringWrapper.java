package com.marine.util;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */

/**
 * @author Empire92
 */
public class StringWrapper {

    public final String value;

    /**
     * Constructor
     *
     * @param value to wrap
     */
    public StringWrapper(final String value) {
        this.value = value;
    }

    /**
     * Check if a wrapped string equals another one
     *
     * @param obj to compare
     * @return true if obj equals the stored value
     */
    @Override
    public boolean equals(final Object obj) {
        return (this == obj) &&
                (obj != null) &&
                (getClass() == obj.getClass()) &&
                (obj instanceof StringWrapper) &&
                ((StringWrapper) obj).value.toLowerCase().equals(this.value.toLowerCase());
    }

    /**
     * Get the string value
     *
     * @return string value
     */
    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Get the hash value
     *
     * @return has value
     */
    @Override
    public int hashCode() {
        return this.value.toLowerCase().hashCode();
    }
}