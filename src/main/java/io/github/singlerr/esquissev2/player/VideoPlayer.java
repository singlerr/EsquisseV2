package io.github.singlerr.esquissev2.player;

import io.github.singlerr.esquissev2.EsquisseV2;
import io.github.singlerr.esquissev2.ForgeUtils;
import io.netty.buffer.ByteBuf;
import net.indiespot.media.AudioRenderer;
import net.indiespot.media.RenderListener;
import net.indiespot.media.VideoPlayback;
import net.indiespot.media.impl.FFmpegVideoPlayback;
import net.indiespot.media.impl.OpenALAudioRenderer;
import net.indiespot.media.impl.OpenGLVideoRenderer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

import java.io.File;
import java.io.IOException;

public abstract class VideoPlayer {

    public static boolean end = false;
    private final File video;
    private Drawable drawable;

    public VideoPlayer(String videoPath) {
        this.video = new File(videoPath);
    }

    public VideoPlayer(File parent, String fileName) {
        this.video = new File(parent, fileName);
    }

    public abstract Drawable getDrawable() throws LWJGLException;

    public void playVideo() throws IOException, LWJGLException {
        drawable = getDrawable();
        OpenGLVideoRenderer videoRenderer = new OpenGLVideoRenderer("test");
        AudioRenderer audioRenderer = new OpenALAudioRenderer();

        VideoPlayback playback = new FFmpegVideoPlayback(video);
        playback.setEndListener(() -> {
            try {
                ForgeUtils.stopRendering(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        videoRenderer.setPostListener(new RenderListener() {
            @Override
            public void onRender() {
                GL11.glFlush();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    drawable.makeCurrent();
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                playback.startVideo(videoRenderer, audioRenderer);
            }
        }).start();

    }

}
