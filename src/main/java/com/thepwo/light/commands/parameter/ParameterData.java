package com.thepwo.light.commands.parameter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A dataclass that holds all the information about a command parameter.
 */
public record ParameterData(

        /*
        The index of this parameter in the command,
        derived from its position in the command method,
        subtracted by one, as CommandSender is not a command parameter.
         */
        int index,

        /*
        Default value of this parameter.
        If this is not specified in the @Param annotation,
        this will equate to an empty string.
         */
        @NotNull String defaultValue,

        /*
        Object type of this parameter,
        derived from data in the method.
         */
        @NotNull Class<?> type,

        /*
        Tab-complete overrides of this parameter.
        If this is not specified in the @Param annotation,
        this will return an empty list.
         */
        @NotNull List<String> suggestions
) {
}
