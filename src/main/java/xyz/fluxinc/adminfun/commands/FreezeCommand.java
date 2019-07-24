package xyz.fluxinc.adminfun.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.fluxinc.adminfun.AdminFun;

import java.util.HashMap;

public class FreezeCommand implements CommandExecutor {

    private AdminFun instance;

    public FreezeCommand(AdminFun inst) {
        this.instance = inst;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.instance.verifyPlayer(sender, args)) { return true; }
        Player target = this.instance.getServer().getPlayer(args[0]);

        if (this.instance.freezeTarget(target)) {
            sender.sendMessage(this.instance.generateMessage("freezeSuccess", new HashMap<String,String>() {{put("target",target.getName());put("dtarget", target.getDisplayName());}}));
            target.setAllowFlight(true);
        } else {
            sender.sendMessage(this.instance.generateMessage("unfreezeSuccess", new HashMap<String,String>() {{put("target",target.getName());put("dtarget", target.getDisplayName());}}));
            target.setAllowFlight(false);
        }

        return true;
    }
}
