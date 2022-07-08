package io.github.singlerr.esquissev2.commands;

import io.github.singlerr.esquissev2.network.NetworkChannelHandler;
import io.github.singlerr.esquissev2.network.packets.INetworkPacket;
import io.github.singlerr.esquissev2.network.packets.PacketChangeFfmpegFileName;
import io.github.singlerr.esquissev2.network.packets.PacketCommandVideoPlay;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class EsquisseCommand extends CommandBase {
    @Override
    public String getName() {
        return "esquisse";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/esquisse";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1)
            return;
        if (args[0].equalsIgnoreCase("play")) {
            //Send Play Video Packet to all
            if (args.length == 2) {
                String videoFileName = args[1];
                NetworkChannelHandler.NETWORK_INSTANCE.sendToAll(new PacketCommandVideoPlay(videoFileName));
                sender.sendMessage(new TextComponentString("Successfully sent to [" + server.getPlayerList().getPlayers().stream().map(p -> p.getName()).reduce((x, y) -> x.concat(",").concat(y)).get() + "]"));
            } else {
                INetworkPacket packet = new PacketCommandVideoPlay(args[1]);
                String[] players = args[2].split(",");
                List<EntityPlayerMP> p = Arrays.stream(players).filter(s -> server.getPlayerList().getPlayerByUsername(s) != null)
                        .map(s -> server.getPlayerList().getPlayerByUsername(s)).collect(Collectors.toList());
                for (EntityPlayerMP player : p) {
                    NetworkChannelHandler.NETWORK_INSTANCE.sendTo(packet, player);
                }
                sender.sendMessage(new TextComponentString("Successfully sent to [" + p.stream().map(s -> s.getName()).reduce((x, y) -> x.concat(",").concat(y)).get() + "]"));
            }
        } else if (args[0].equalsIgnoreCase("change")) {
            //Send Play Video Packet to all
            if (args.length == 2) {
                String ffmpegName = args[1];
                NetworkChannelHandler.NETWORK_INSTANCE.sendToAll(new PacketChangeFfmpegFileName(ffmpegName));
                sender.sendMessage(new TextComponentString("Successfully sent to [" + server.getPlayerList().getPlayers().stream().map(p -> p.getName()).reduce((x, y) -> x.concat(",").concat(y)).get() + "]"));
            } else {
                INetworkPacket packet = new PacketChangeFfmpegFileName(args[1]);
                String[] players = args[2].split(",");
                List<EntityPlayerMP> p = Arrays.stream(players).filter(s -> server.getPlayerList().getPlayerByUsername(s) != null)
                        .map(s -> server.getPlayerList().getPlayerByUsername(s)).collect(Collectors.toList());
                for (EntityPlayerMP player : p) {
                    NetworkChannelHandler.NETWORK_INSTANCE.sendTo(packet, player);
                }
                sender.sendMessage(new TextComponentString("Successfully sent to [" + p.stream().map(s -> s.getName()).reduce((x, y) -> x.concat(",").concat(y)).get() + "]"));
            }
        }
    }
}
