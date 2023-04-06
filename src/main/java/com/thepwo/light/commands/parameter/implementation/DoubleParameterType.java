package com.thepwo.light.commands.parameter.implementation;

import net.evilkingdom.prison.commands.constants.Messages;
import net.evilkingdom.prison.commands.parameter.ParameterType;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleParameterType implements ParameterType<Double> {
    @Override
    public @Nullable Double transform(@NotNull CommandSender sender, @NotNull String source) {
        Double aDouble = null;
        String formatted = source.replace(",", "").replace("_", "");
        try {
            aDouble = Double.valueOf(formatted);
            if (aDouble.isNaN() || aDouble.isInfinite()) {
                sender.sendMessage(Messages.INVALID_DOUBLE.replace("%number%", source));
                return null;
            }
        } catch (NumberFormatException ignored) {
            sender.sendMessage(Messages.INVALID_DOUBLE.replace("%number%", source));
        }
        return aDouble;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String source) {
        return new ArrayList<>(Arrays.asList("1.0", "10.0", "100.0", "1000.0"));
    }
}

