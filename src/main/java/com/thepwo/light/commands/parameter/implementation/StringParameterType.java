package com.thepwo.light.commands.parameter.implementation;

import net.evilkingdom.prison.commands.parameter.ParameterType;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StringParameterType implements ParameterType<String> {
    @Override
    public @Nullable String transform(@NotNull CommandSender sender, @NotNull String source) {
        return source;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String source) {
        return null;
    }
}
