package com.thepwo.light.commands;

import com.google.common.base.Preconditions;
import com.thepwo.light.commands.parameter.ParameterData;
import com.thepwo.light.commands.parameter.ParameterType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnegative;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents one single command or sub-command or command-argument in a hierarchical command tree.
 * Each command name or alias defined will have its own command node.
 *
 * @see com.thepwo.light.commands.Command
 */
public class CommandNode {
    private final @NotNull String name;
    private final @NotNull Map<String, CommandNode> children;
    private @NotNull String permission;
    private @Nullable CommandNode parent;
    private @NotNull List<ParameterData> parameters;
    private @Nullable Object owningInstance;
    private @Nullable Method method;
    private @Nullable String usage;
    private boolean async;

    public CommandNode(@NotNull final String name, @NotNull final String permission) {
        this.name = name;
        this.permission = permission;
        this.children = new TreeMap<>();
        this.parameters = new ArrayList<>();
    }

    /**
     * Registers a node, could be an argument, sub-command or a command as a child of the current node.
     *
     * @param commandNode ~ Node to register
     */
    public void registerCommand(@NotNull final CommandNode commandNode) {
        Preconditions.checkNotNull(commandNode, "CommandNode cannot be null!");
        commandNode.parent = this;
        this.children.put(commandNode.name, commandNode);
    }

    /**
     * Checks if the current node has a child node of a certain name.
     *
     * @param name ~ Name of child node
     * @return Whether the current node has the child node
     */
    public boolean hasCommand(@NotNull final String name) {
        return this.children.containsKey(name.toLowerCase());
    }

    /**
     * Retrieve a child node of by its name, returning null if it does not exist.
     *
     * @param name ~ Name of child node
     * @return Child node by the specified name, if present
     */
    @Nullable
    public CommandNode getCommand(@NotNull final String name) {
        return this.children.get(name.toLowerCase());
    }

    /**
     * Checks if the current node has any child nodes at all.
     *
     * @return Whether the current node has any child nodes
     */
    public boolean hasCommands() {
        return !this.children.isEmpty();
    }

    /**
     * Automatically generates the command usage based on the type (class names) of its parameters,
     * unless it is overridden by specifying its usage when defining the command.
     * It will then be formatted by the usage message from the configuration file.
     *
     * @return The formatted usage message
     */
    public String getRealUsage() {
        String usage = this.usage;
        if (this.usage == null || this.usage.length() == 0) {
            StringBuilder stringBuilder = new StringBuilder();

            CommandNode parent = this;
            while (parent != null && !parent.name.equals("root")) {
                stringBuilder.insert(0, " " + parent.name);
                parent = parent.getParent();
            }
            stringBuilder.deleteCharAt(0);

            for (ParameterData parameterData : this.parameters) {
                stringBuilder.append(" ");
                if (parameterData.defaultValue().length() == 0)
                    stringBuilder.append("<").append(parameterData.type().getSimpleName().toLowerCase()).append(">");
                else
                    stringBuilder.append("[").append(parameterData.type().getSimpleName().toLowerCase()).append("=")
                            .append(parameterData.defaultValue()).append("]");
            }

            usage = stringBuilder.toString();
        }

        return Messages.INVALID_USAGE.replace("%usage%", usage);
    }

    /**
     * Invokes the current command node, failing silently if it is not a command node.
     * Permission checks and parameter type casting are handled here.
     *
     * @param sender ~ The command executor
     * @param arguments ~ List of raw command arguments
     * @return True if the command ran successfully
     */
    public boolean invoke(@NotNull final CommandSender sender, @NotNull final String[] arguments) {
        if (this.method == null) return false;

        if (this.permission.length() > 0 && !sender.hasPermission(this.permission)) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return false;
        }

        int methodParamCount = this.method.getParameterCount();
        List<Object> objects = new ArrayList<>(methodParamCount);

        if (Player.class.isAssignableFrom(this.method.getParameterTypes()[0])) {
            if (sender instanceof Player player) objects.add(player);
            else Response.get("only-players-command").send(sender);
        } else {
            objects.add(sender);
        }

        for (int i = 0; i < this.parameters.size(); i++) {
            ParameterData parameterData = this.parameters.get(i);
            String defaultValue = parameterData.defaultValue();

            String argument;
            if (i < arguments.length) argument = arguments[i];
            else {
                if (defaultValue.length() == 0) {
                    sender.sendMessage(getRealUsage());
                    return false;
                } else argument = defaultValue;
            }

            ParameterType<?> type = Commands.getParameterType(parameterData.type());
            assert type != null;
            Object result = type.transform(sender, argument);
            if (result == null) return false;
            objects.add(parameterData.index(), result);
        }

        try {
            this.method.invoke(this.owningInstance, objects.toArray());
        } catch (IllegalAccessException | InvocationTargetException ignored) {
            sender.sendMessage(Messages.ERROR);
            return false;
        }

        return true;
    }

    /**
     * Returns the sub-commands of this node for which a CommandSender has permission to use.
     *
     * @param sender ~ The command executor
     * @return The list of sub-commands
     */
    @NotNull
    public List<String> getSubCommands(@NotNull final CommandSender sender) {
        ArrayList<String> commands = new ArrayList<>();
        this.children.values()
                .stream()
                .filter(commandNode -> canUse(sender))
                .forEach(commandNode -> commands.add(commandNode.getName()));
        return commands;
    }

    /**
     * Checks if a CommandSender has permission to use this command.
     *
     * @param sender ~ The command executor
     * @return Whether the command executor has permission to use this command
     */
    public boolean canUse(@NotNull final CommandSender sender) {
        if (this.permission.length() == 0) return true;
        return sender.hasPermission(this.permission);
    }

    public @NotNull Map<String, CommandNode> getChildren() {
        return children;
    }

    public @NotNull List<ParameterData> getParameters() {
        return parameters;
    }

    public CommandNode setParameters(@NotNull final List<ParameterData> parameters) {
        this.parameters = parameters;
        return this;
    }

    public @Nullable CommandNode getParent() {
        return parent;
    }

    public CommandNode setParent(@Nullable final CommandNode parent) {
        this.parent = parent;
        return this;
    }

    public @Nullable Object getOwningInstance() {
        return owningInstance;
    }

    public CommandNode setOwningInstance(@Nullable final Object owningInstance) {
        this.owningInstance = owningInstance;
        return this;
    }

    public @Nullable Method getMethod() {
        return method;
    }

    public CommandNode setMethod(@Nullable final Method method) {
        this.method = method;
        return this;
    }

    public @Nullable String getUsage() {
        return usage;
    }

    public CommandNode setUsage(@Nullable final String usage) {
        this.usage = usage;
        return this;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getPermission() {
        return permission;
    }

    public CommandNode setPermission(@NotNull final String permission) {
        this.permission = permission;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public CommandNode setAsync(final boolean async) {
        this.async = async;
        return this;
    }
}
