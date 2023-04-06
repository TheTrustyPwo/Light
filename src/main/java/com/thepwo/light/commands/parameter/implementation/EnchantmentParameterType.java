package com.thepwo.light.commands.parameter.implementation;

import net.evilkingdom.prison.commands.constants.Messages;
import net.evilkingdom.prison.commands.parameter.ParameterType;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantmentParameterType implements ParameterType<Enchantment> {
    @Override
    public @Nullable Enchantment transform(@NotNull CommandSender sender, @NotNull String source) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(source.toLowerCase()));
        if (enchantment == null) sender.sendMessage(Messages.INVALID_ENCHANTMENT.replace("%enchant%", source));
        return enchantment;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String source) {
        return Arrays.stream(Enchantment.values())
                .map(Enchantment::getKey)
                .map(NamespacedKey::getKey)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith(source.toUpperCase()))
                .collect(Collectors.toList());
    }
}
