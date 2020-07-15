package me.syes.hunters.game;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.syes.hunters.Core;

public class SpectatorHandler implements Listener {
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		if(e.isCancelled())
			return;
		Player p = (Player) e.getEntity();
		if(!Core.getInstance().gameManager.isInGame(p))
			return;
		Game g = Core.getInstance().gameManager.getGame(p);
		if(!(e.getDamager() instanceof Player) && !(e.getDamager() instanceof Projectile))
			return;
		if(e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			if(!Core.getInstance().gameManager.isInGame(d))
				return;
			if(!Core.getInstance().gameManager.getGame(d).isSpectator(d) && !Core.getInstance().gameManager.getGame(p).isSpectator(p))
				return;
			e.setCancelled(true);
		}/*else if(e.getDamager() instanceof Arrow) {
			Arrow a = (Arrow) e.getDamager();
			if(!g.isSpectator(p))
				return;
			e.setCancelled(true);
			a.remove();
			Arrow a2 = (Arrow) p.getWorld().spawnEntity(a.getLocation().add((a.getVelocity().normalize()).multiply(2)), EntityType.ARROW);
			a2.setVelocity(a.getVelocity());
			//p.getWorld().spawnArrow(a.getLocation().add((a.getVelocity().normalize()).multiply(2)), a.getVelocity(), .6f, .12f);
		}else if(e.getDamager() instanceof Projectile) {
			if(!Core.getInstance().gameManager.getGame(p).isSpectator(p))
				return;
			Team t = p.getScoreboard().getTeam(p.getName());
			if(p.getScoreboard().getTeam(p.getName()) == null) {
				t = p.getScoreboard().registerNewTeam(p.getName());
				t.setAllowFriendlyFire(false);
				t.addPlayer(p);
				g.addSpectatorTeam(t);
			}
			t.addPlayer((Player) ((Projectile) e.getDamager()).getShooter());
			
			/*Projectile pj = (Projectile) e.getDamager();
			if(!g.isSpectator(p))
				return;
			//e.setCancelled(true);
			Location firstLoc = p.getLocation().clone();
			p.teleport(p.getLocation().add(0, 5, 0));
			try {
				Projectile pj2 = (Projectile) p.getWorld().spawnEntity(pj.getLocation(), pj.getType());
				pj2.setVelocity(pj.getVelocity());
				pj2.setShooter(pj.getShooter());
			}catch(IllegalArgumentException iae) {}
			pj.remove();
			new BukkitRunnable() {
				public void run() {
					p.teleport(firstLoc);
				}
			}.runTaskLater(Core.getInstance(), 5);
		}*/
	}
    
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        if(Core.getInstance().gameManager.isInGame(p))
        	if(Core.getInstance().gameManager.getGame(p).isSpectator(p))
        		event.setCancelled(true);
    }

    
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if(Core.getInstance().gameManager.isInGame(p))
        	if(Core.getInstance().gameManager.getGame(p).isSpectator(p)) {
        		event.setCancelled(true);
        		p.updateInventory();
        	}
    }

}
