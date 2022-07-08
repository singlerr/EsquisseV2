package io.github.singlerr.esquissev2.network.handlers;

import io.github.singlerr.esquissev2.EsquisseV2;
import io.github.singlerr.esquissev2.network.INetworkPacketHandler;
import io.github.singlerr.esquissev2.network.packets.INetworkPacket;
import io.github.singlerr.esquissev2.network.packets.PacketChangeFfmpegFileName;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class HandlerChangeFFmpegFileName implements INetworkPacketHandler<PacketChangeFfmpegFileName> {

    @Override
    public INetworkPacket onMessage(PacketChangeFfmpegFileName message, MessageContext ctx) {
        if (message.getFileName() != null)
            EsquisseV2.setFfmpegFileName(message.getFileName());
        return null;
    }
}
