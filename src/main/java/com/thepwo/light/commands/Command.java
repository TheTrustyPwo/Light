package com.thepwo.light.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to denote Light custom command executors which should be applied to methods.
 * Note that the class inheriting the method MUST extend com.thepwo.light.commands.LightCommand.
 *
 * @see com.thepwo.light.commands.LightCommand
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * @return The list of names and aliases of the defined command.
     * Each name and alias will be registered independently and bounded to a CommandNode.
     * They can have spaces such as '/item give...', and the main command and sub command argument will be handled accordingly.
     */
    String[] names();

    /**
     * @return The in game permission required to execute this command, defaults to none.
     * If the executor does not have permission, the no-permission message will be sent.
     */
    String permission() default "";

    /**
     * @return This overrides the automatically generated command usage by the system.
     * By default, if this is not specified, Light will generate a usage based on the command parameters, which will
     * be sent to the executor if he has inputted wrong or incorrect number of arguments.
     */
    String usage() default "";

    /**
     * @return Specifies if this command should be handled asynchronously, meaning it will not block the main thread when executing.
     */
    boolean async() default false;
}
