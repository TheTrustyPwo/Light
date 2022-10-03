package com.thepwo.light.commands.parameter;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Generic interface to handle and transform parameter string inputs to their respective object types.
 *
 * @param <T> Parameter object type
 */
public interface ParameterType<T> {

    /**
     * Transform, basically cast, the string input source to this parameter type's object.
     *
     * @param sender Executor of the command
     * @param source String input source, essentially the raw argument string
     * @return The transformed object "cast" from the source
     */
    @Nullable
    T transform(@NotNull final CommandSender sender, @NotNull final String source);

    /**
     * Built-in default tab-complete for this parameter type,
     * which the developer can choose to override in the @Param annotation.
     *
     * @param sender Executor of the command
     * @param source String input source, essentially the raw argument string
     * @return The list of tab-complete strings for this parameter type
     * @see com.thepwo.light.commands.parameter.Param
     */
    @NotNull
    List<String> tabComplete(@NotNull final CommandSender sender, @NotNull final String source);
}
