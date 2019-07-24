package xyz.fluxinc.adminfun.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.fluxinc.adminfun.AdminFun;

public class PlayerMoveListener implements Listener {

    AdminFun instance;

    public PlayerMoveListener(AdminFun instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.instance.isFrozen(event.getPlayer()) && compareMovement(event.getFrom(), event.getTo())) { event.setCancelled(true); }
    }

    private boolean compareMovement(Location from, Location to) {
        return (from.getBlockX() != to.getBlockX()) || (from.getBlockY() != to.getBlockY()) || (from.getBlockZ() != to.getBlockZ());
    }
}
