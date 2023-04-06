package com.thepwo.light.commands.parameter.implementation;

import net.evilkingdom.prison.commands.constants.Messages;
import net.evilkingdom.prison.commands.parameter.ParameterType;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntegerParameterType implements ParameterType<Integer> {
    @Override
    public @Nullable Integer transform(@NotNull CommandSender sender, @NotNull String source) {
        Integer integer = null;
        String formatted = source.replace(",", "").replace("_", "");
        try {
            integer = Integer.valueOf(formatted);
        } catch (NumberFormatException ignored) {
            sender.sendMessage(Messages.INVALID_INT.replace("%number%", source));
        }
        return integer;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String source) {
        return new ArrayList<>(Arrays.asList("1", "10", "100", "1000"));
    }
}
