package org.marinemc.util.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to aware developer that the following code is to be cautious with and not called without understanding what it does.
 * Sometimes it could be put to tell user that its perhaps automaticlly called and should only in rare cases be needed to be called.
 * 
 * @author Fozie
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR,ElementType.METHOD})
public @interface Cautious {

}
