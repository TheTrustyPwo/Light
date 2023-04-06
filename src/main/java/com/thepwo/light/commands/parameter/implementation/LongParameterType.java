package com.thepwo.light.commands.parameter.implementation;

import net.evilkingdom.prison.commands.constants.Messages;
import net.evilkingdom.prison.commands.parameter.ParameterType;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongParameterType implements ParameterType<Long> {
    @Override
    public @Nullable Long transform(@NotNull CommandSender sender, @NotNull String source) {
        Long aLong = null;
        String formatted = source.replace(",", "").replace("_", "");
        try {
            aLong = Long.valueOf(formatted);
        } catch (NumberFormatException ignored) {
            sender.sendMessage(Messages.INVALID_INT.replace("%number%", source));
        }
        return aLong;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String source) {
        return new ArrayList<>(Arrays.asList("1", "10", "100", "1000"));
    }
}
