package org.marinemc.util.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to tell developers that this doing this only affects the client and the
 * changing is not saved on the server
 *
 * @author Fozie
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE,
		ElementType.PARAMETER, ElementType.TYPE })
public @interface Clientside {
}
