package ru.dragonestia.dguard.listeners;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import ru.dragonestia.dguard.DGuard;
import
public class ScoreboardListener implements Listener {
    private DGuard plugin;

    public ScoreboardListener(DGuard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Scoreboard scoreboard = new Scoreboard("Test Scoreboard", DisplaySlot.SIDEBAR, 20);

        scoreboard.setHandler(pl -> {
            scoreboard.addLine(pl.getName());
            scoreboard.addLine("§1"); // used for skip line
            scoreboard.addLine("random: " + Math.random());
            scoreboard.addLine("random: " + Math.random());
            scoreboard.addLine("random: " + Math.random());
            scoreboard.addLine("random: " + Math.random());
            scoreboard.addLine("random: " + Math.random());
            scoreboard.addLine("§2"); // used for skip line
            scoreboard.addLine("Online: " + Server.getInstance().getOnlinePlayers().size());
        });

        scoreboard.show(player);
    }
}
