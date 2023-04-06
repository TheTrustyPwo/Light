package com.thepwo.light.commands.parameter.implementation;

import com.thepwo.light.commands.parameter.ParameterType;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BooleanParameterType implements ParameterType<Boolean> {
    private final static Map<String, Boolean> booleanMap = new HashMap<>();

    static {
        booleanMap.put("TRUE", true);
        booleanMap.put("FALSE", false);
        booleanMap.put("YES", true);
        booleanMap.put("NO", false);
        booleanMap.put("ON", true);
        booleanMap.put("OFF", false);
    }

    @Override
    public @Nullable Boolean transform(@NotNull CommandSender sender, @NotNull String source) {
        Boolean bool = booleanMap.get(source.toUpperCase());
        if (bool == null) sender.sendMessage(Messages.INVALID_BOOLEAN.replace("%boolean%", source));
        return bool;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String source) {
        return Stream.of("TRUE", "FALSE")
                .filter(s -> s.startsWith(source.toUpperCase()))
                .collect(Collectors.toList());
    }
}
