package com.ovanilla.utils.chat;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ChatMessage {

    private final Player target;
    private final TextComponent component = new TextComponent();

    public ChatMessage(Player target) { this.target = target; }

    /***
     * Useful for modifying the message deeper than the provided methods
     * @return The TextComponent object
     */
    public TextComponent getComponent() { return component; }

    public Text getAsText() { return new Text(component); }

    public ChatMessage append(String text) {
        component.addExtra(text);
        return this;
    }

    public ChatMessage setBold(boolean bold) {
        component.setBold(bold);
        return this;
    }

    public ChatMessage setItalic(boolean italic) {
        component.setItalic(italic);
        return this;
    }

    public ChatMessage setUnderlined(boolean underlined) {
        component.setUnderlined(underlined);
        return this;
    }

    public ChatMessage setStrikethrough(boolean strikethrough) {
        component.setStrikethrough(strikethrough);
        return this;
    }

    public ChatMessage setObfuscated(boolean obfuscated) {
        component.setObfuscated(obfuscated);
        return this;
    }

    public ChatMessage setColor(ChatColor color) {
        component.setColor(net.md_5.bungee.api.ChatColor.getByChar(color.getChar())); // Convert to Bungee's ChatColor
        return this;
    }

    public ChatMessage setColor(net.md_5.bungee.api.ChatColor color) {
        component.setColor(color);
        return this;
    }

    public ChatMessage setClickEvent(Action action, String value) {
        component.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(getClickAction(action), value));
        return this;
    }

    public ChatMessage setClickEvent(net.md_5.bungee.api.chat.ClickEvent event) {
        component.setClickEvent(event);
        return this;
    }

    public ChatMessage setHoverEvent(Action action, @Nonnull ChatMessage... value) {
        Content[] components = new Content[value != null ? value.length : 0];
        for(int i = 0; i < components.length; i++) components[i] = value[i].getAsText();

        component.setHoverEvent(new net.md_5.bungee.api.chat.HoverEvent(getHoverAction(action), components));
        return this;
    }

    public ChatMessage setHoverEvent(net.md_5.bungee.api.chat.HoverEvent event) {
        component.setHoverEvent(event);
        return this;
    }

    public ChatMessage setInsertion(String insertion) {
        component.setInsertion(insertion);
        return this;
    }

    public void send() { target.spigot().sendMessage(component); }

    private net.md_5.bungee.api.chat.ClickEvent.Action getClickAction(Action action) {
        switch(action) {
            case OPEN_URL: return net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL;
            case OPEN_FILE: return net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_FILE;
            case RUN_COMMAND: return net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND;
            case SUGGEST_COMMAND: return net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND;
            case CHANGE_PAGE: return net.md_5.bungee.api.chat.ClickEvent.Action.CHANGE_PAGE;
            default: return null;
        }
    }

    private net.md_5.bungee.api.chat.HoverEvent.Action getHoverAction(Action action) {
        switch(action) {
            case SHOW_TEXT: return net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT;
            case SHOW_ITEM: return net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_ITEM;
            case SHOW_ENTITY: return net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_ENTITY;
            default: return null;
        }
    }

    public static enum Action {
        OPEN_URL, OPEN_FILE, RUN_COMMAND, SUGGEST_COMMAND, CHANGE_PAGE, // Click Actions
        SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM, SHOW_ENTITY // Hover Actions
    }
}
