package com.thepwo.light.commands.parameter.implementation;

import net.evilkingdom.prison.commands.constants.Messages;
import net.evilkingdom.prison.commands.parameter.ParameterType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerParameterType implements ParameterType<Player> {
    @Override
    public @Nullable Player transform(@NotNull CommandSender sender, @NotNull String source) {
        if (sender instanceof Player player && source.equals("self")) return player;
        Player player = Bukkit.getPlayer(source);
        if (player == null) sender.sendMessage(Messages.INVALID_PLAYER.replace("%player%", source));
        return player;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String source) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(string -> string.toLowerCase().startsWith(source.toLowerCase()))
                .collect(Collectors.toList());
    }
}
