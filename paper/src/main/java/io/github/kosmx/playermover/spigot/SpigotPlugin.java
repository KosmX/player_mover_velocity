package io.github.kosmx.playermover.spigot;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import dev.jorel.commandapi.executors.CommandExecutor;
import io.github.kosmx.playermover.common.MoveMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class SpigotPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        new CommandAPICommand("moveplayer")
          .withArguments(new PlayerArgument("player"))
          .withArguments(new GreedyStringArgument("server"))
          .withPermission(CommandPermission.OP)
          .executes(this::moveCommand)
          .register();

    }

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getMessenger().registerOutgoingPluginChannel(this, MoveMessage.channelID);
        //getCommand("moveplayer").setExecutor(this::moveCommand);
    }


    private void moveCommand(CommandSender sender, Object[] args){

        if(args.length > 2)return;
        if(args[0] instanceof Player player && args[1] instanceof String str) {
            var message = new MoveMessage();
            message.setServer(str);
            message.setPlayer(player.getUniqueId());
            try {
                player.sendPluginMessage(this, MoveMessage.channelID, message.write());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    public boolean moveCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 2)return false;
        if(args.length >= 1){
            Player player;
            var message = new MoveMessage();
            if(args.length == 2) {
                player = getServer().getPlayer(args[0]);
                if (player == null) {
                    getLogger().log(Level.WARNING, "No player: \"" + args[0] + "\"");
                    return false;
                }
            }
            else {
                if(sender instanceof Player player1){
                    player = player1;
                }
                else {
                    return false;
                }
            }
            message.setServer(args[args.length-1]);
            message.setPlayer(player.getUniqueId());
            try {
                player.sendPluginMessage(this, MoveMessage.channelID, message.write());
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

     */
}
