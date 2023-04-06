package com.thepwo.light.commands.parameter.implementation;

import net.evilkingdom.prison.commands.constants.Messages;
import net.evilkingdom.prison.commands.parameter.ParameterType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialParameterType implements ParameterType<Material> {
    @Override
    public @Nullable Material transform(@NotNull CommandSender sender, @NotNull String source) {
        Material material = Material.getMaterial(source);
        if (material == null) sender.sendMessage(Messages.INVALID_MATERIAL.replace("%material%", source));
        return material;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String source) {
        return Arrays.stream(Material.values())
                .map(Material::toString)
                .filter(s -> s.startsWith(source.toUpperCase()))
                .collect(Collectors.toList());
    }
}
