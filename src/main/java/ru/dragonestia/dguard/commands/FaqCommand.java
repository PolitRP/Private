package ru.dragonestia.dguard.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.Plugin;
import ru.dragonestia.dguard.Forms;

public class FaqCommand extends PluginCommand<Plugin> {

    private final Forms forms;

    public FaqCommand(Plugin owner, Forms forms) {
        super("faq", owner);
        this.forms = forms;
        this.setDescription("Open FAQ form");
        this.setUsage("/faq");
        this.setPermission("dguard.command.faq");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }


        Player player = (Player) sender;
        forms.sendFAQForm(player);
        return true;
    }
}
