package io.github.singlerr.esquissev2.network;

import io.github.singlerr.esquissev2.network.handlers.HandlerChangeFFmpegFileName;
import io.github.singlerr.esquissev2.network.handlers.HandlerCommandVideoPlay;
import io.github.singlerr.esquissev2.network.packets.PacketChangeFfmpegFileName;
import io.github.singlerr.esquissev2.network.packets.PacketCommandVideoPlay;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class NetworkChannelHandler {
    private static final String CHANNEL_NAME = "esquissev2";
    public static final SimpleNetworkWrapper NETWORK_INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL_NAME);

    public static void registerPackets() {
        int id = 0;
        NETWORK_INSTANCE.registerMessage(HandlerChangeFFmpegFileName.class, PacketChangeFfmpegFileName.class, id++, Side.CLIENT);
        NETWORK_INSTANCE.registerMessage(HandlerCommandVideoPlay.class, PacketCommandVideoPlay.class, id++, Side.CLIENT);
        NETWORK_INSTANCE.registerMessage(HandlerChangeFFmpegFileName.class, PacketChangeFfmpegFileName.class, id++, Side.SERVER);
        NETWORK_INSTANCE.registerMessage(HandlerCommandVideoPlay.class, PacketCommandVideoPlay.class, id, Side.SERVER);
    }
}
