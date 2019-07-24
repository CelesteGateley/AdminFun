package xyz.fluxinc.adminfun;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.fluxinc.adminfun.commands.FreezeCommand;
import xyz.fluxinc.adminfun.commands.SlapCommand;
import xyz.fluxinc.adminfun.listeners.PlayerMoveListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AdminFun extends JavaPlugin {

    private YamlConfiguration lang;
    private List<Player> frozenPlayers;

    @Override
    public void onEnable() {

        lang = new YamlConfiguration();
        saveResource("lang.yml", false);
        try {
            lang.load(new File(getDataFolder(), "lang.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().severe("[AdminFun] Invalid Lang File, Disabling!!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);

        this.getServer().getPluginCommand("slap").setExecutor(new SlapCommand(this));
        frozenPlayers = new ArrayList<>();
        this.getServer().getPluginCommand("freeze").setExecutor(new FreezeCommand(this));

    }

    @Override
    public void onDisable() {

    }

    public String generateMessage(String langKey, Map<String, String> variables) {
        String prefix = lang.getString("prefix");
        String msg = lang.getString(langKey);
        if (prefix == null || msg == null) { getLogger().severe("Invalid Lang File, missing elements prefix or " + langKey); return ""; }
        for (Map.Entry<String,String> pair : variables.entrySet()) { msg = msg.replaceAll("%" + pair.getKey() + "%", pair.getValue()); }
        return ChatColor.translateAlternateColorCodes('&', prefix + msg);
    }

    public String generateMessage(String langKey) {
        String prefix = lang.getString("prefix");
        String msg = lang.getString(langKey);
        if (prefix == null || msg == null) { getLogger().severe("Invalid Lang File, missing elements prefix or " + langKey); return ""; }
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + msg);
    }

    public boolean verifyPlayer(CommandSender sender, String[] args) {
        if (args[0].length() < 1) {
            sender.sendMessage(generateMessage("invalidUsage", new HashMap<String,String>() {{put("usage", "/slap (name)");}}));
            return true;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(generateMessage("userNotFound", new HashMap<String,String>() {{put("target", args[0]);}}));
            return true;
        }
        return false;
    }

    public List<Player> getFrozenPlayers() { return frozenPlayers; }

    public boolean isFrozen(Player player) { return frozenPlayers.contains(player); }

    public boolean freezeTarget(Player player) {
        if (frozenPlayers.contains(player)) {
            frozenPlayers.remove(player);
            return false;
        } else {
            frozenPlayers.add(player);
            return true;
        }
    }





}
