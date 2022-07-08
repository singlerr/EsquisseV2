package io.github.singlerr.esquissev2.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraftforge.fml.common.network.ByteBufUtils;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class PacketCommandVideoPlay implements INetworkPacket {
    private String videoName;

    @Override
    public void fromBytes(ByteBuf buf) {
        this.videoName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, videoName);
    }
}
