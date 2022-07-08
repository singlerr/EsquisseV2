package io.github.singlerr.esquissev2.network;

import io.github.singlerr.esquissev2.network.packets.INetworkPacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public interface INetworkPacketHandler<T extends INetworkPacket> extends IMessageHandler<T, INetworkPacket> {
}
