package pl.blackwaterapi.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import pl.blackwaterapi.commands.APICommand;
import pl.blackwaterapi.utils.ActionBarUtil;
import pl.blackwaterapi.utils.Ticking;
import pl.blackwaterapi.utils.Util;

public class TPSLiveTimeTask extends BukkitRunnable{

	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			if(APICommand.getTpsUsers().contains(p.getName())){
				ActionBarUtil.sendActionBar(p, Util.fixColor("&2BW-API &8» &eLiveTime TPS: " + Ticking.getTPS()));
			}
		}
		
	}

}
