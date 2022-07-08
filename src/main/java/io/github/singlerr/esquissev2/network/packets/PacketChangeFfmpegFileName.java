package io.github.singlerr.esquissev2.network.packets;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraftforge.fml.common.network.ByteBufUtils;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class PacketChangeFfmpegFileName implements INetworkPacket {

    private String fileName;

    @Override
    public void fromBytes(ByteBuf buf) {
        fileName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, fileName);
    }
}
