package org.marinemc.util.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to tell developers that this target will wait for a thread to finish the
 * process
 *
 * @author Fozie
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE,
		ElementType.PARAMETER, ElementType.TYPE })
public @interface Threaded {

}
