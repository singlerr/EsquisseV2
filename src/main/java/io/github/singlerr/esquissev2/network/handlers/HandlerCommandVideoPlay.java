package io.github.singlerr.esquissev2.network.handlers;

import io.github.singlerr.esquissev2.EsquisseV2;
import io.github.singlerr.esquissev2.ForgeUtils;
import io.github.singlerr.esquissev2.network.INetworkPacketHandler;
import io.github.singlerr.esquissev2.network.packets.INetworkPacket;
import io.github.singlerr.esquissev2.network.packets.PacketCommandVideoPlay;
import net.indiespot.media.AudioRenderer;
import net.indiespot.media.RenderListener;
import net.indiespot.media.VideoPlayback;
import net.indiespot.media.impl.FFmpegVideoPlayback;
import net.indiespot.media.impl.OpenALAudioRenderer;
import net.indiespot.media.impl.OpenGLVideoRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.File;


public final class HandlerCommandVideoPlay implements INetworkPacketHandler<PacketCommandVideoPlay> {
    @Override
    public INetworkPacket onMessage(PacketCommandVideoPlay message, MessageContext ctx) {
        if (message.getVideoName() != null) {
            try {
                ForgeUtils.runMinecraftTask(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            OpenGLVideoRenderer videoRenderer = new OpenGLVideoRenderer("test");
                            AudioRenderer audioRenderer = new OpenALAudioRenderer();

                            VideoPlayback playback = new FFmpegVideoPlayback(new File(EsquisseV2.DESTINATION, "intro.mp4"));

                            playback.setEndListener(new RenderListener() {
                                @Override
                                public void onRender() {
                                    try {
                                        ForgeUtils.stopRendering(false);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });


                            ForgeUtils.stopRendering(true);
                            ForgeUtils.setSkipDisplay(false);
                            GlStateManager.disableCull();
                            playback.startVideo(videoRenderer, audioRenderer);
                            ForgeUtils.setRenderHandler(new Runnable() {
                                @Override
                                public void run() {

                                    if (playback.loopEnd()) {
                                        playback.loop();

                                    }else{
                                        playback.finalizeLoop();
                                    }
                                }
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
