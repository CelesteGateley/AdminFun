package xyz.fluxinc.adminfun.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.fluxinc.adminfun.AdminFun;

import java.util.HashMap;

public class SlapCommand implements CommandExecutor {

    private AdminFun instance;

    public SlapCommand(AdminFun inst) {
        this.instance = inst;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.instance.verifyPlayer(sender, args)) { return true; }
        Player target = this.instance.getServer().getPlayer(args[0]);
        Player caster = (Player) sender;
        Location targetLocation = target.getLocation();
        Location casterLocation = caster.getLocation();
        double xVal = (casterLocation.getX() - targetLocation.getX());
        double zVal = (casterLocation.getZ() - targetLocation.getZ());
        if (xVal < 0) { xVal = targetLocation.getX() - 10; } else if (xVal == 0) { xVal = targetLocation.getX(); } else { xVal = targetLocation.getX() + 10; }
        if (zVal < 0) { zVal = targetLocation.getX() - 10; } else if (zVal == 0) { zVal = targetLocation.getX(); } else { zVal = targetLocation.getX() + 10; }

        target.setVelocity(new Vector(xVal, target.getLocation().getY() + 5, zVal));
        sender.sendMessage(this.instance.generateMessage("slapSuccess", new HashMap<String,String>() {{put("target",target.getName());put("dtarget", target.getDisplayName());}}));
        return true;
    }
}
