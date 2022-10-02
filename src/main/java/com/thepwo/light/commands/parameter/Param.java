package com.thepwo.light.commands.parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to denote a parameter in a method as a command parameter that has to be processed by the library.
 * The method must be marked with the @Command annotation and be inside a class implementing LightCommand.
 *
 * @see com.thepwo.light.commands.Command
 * @see com.thepwo.light.commands.LightCommand
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {

    /**
     * @return The default value of this parameter as a String if the argument is not specified.
     * This parameter will only be optional if default value is defined, otherwise it is still required.
     */
    String defaultValue() default "";

    /**
     * @return The list of tab-complete strings for this parameter.
     * By default, Light provides the necessary tab-complete data for each parameter type.
     * This will override it and should be used if you want to implement custom tab completing.
     */
    String[] suggestions() default {};
}
