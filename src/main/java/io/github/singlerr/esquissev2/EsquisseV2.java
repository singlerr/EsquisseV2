package io.github.singlerr.esquissev2;

import io.github.singlerr.esquissev2.commands.EsquisseCommand;
import io.github.singlerr.esquissev2.init.ModInitializer;
import io.github.singlerr.esquissev2.network.NetworkChannelHandler;
import io.github.singlerr.esquissev2.player.VideoPlayer;
import net.indiespot.media.AudioRenderer;
import net.indiespot.media.RenderListener;
import net.indiespot.media.VideoPlayback;
import net.indiespot.media.impl.FFmpeg;
import net.indiespot.media.impl.FFmpegVideoPlayback;
import net.indiespot.media.impl.OpenALAudioRenderer;
import net.indiespot.media.impl.OpenGLVideoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.SharedDrawable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

@Mod(
        modid = EsquisseV2.MOD_ID,
        name = EsquisseV2.MOD_NAME,
        version = EsquisseV2.VERSION
)

public class EsquisseV2 {

    public static final String MOD_ID = "esquissev2";
    public static final String MOD_NAME = "EsquisseV2";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final File DESTINATION = new File(System.getProperty("user.dir"), "mods\\ffmpeg");
    private static final String SOURCE = "/resources";
    private static final String SOURCE_INFO = "/resources.txt";
    @Mod.Instance(MOD_ID)
    public static EsquisseV2 INSTANCE;
    private static String FFMPEG_FILE_NAME = "ffmpeg64.exe";

    public static void setFfmpegFileName(String fileName) {
        FFMPEG_FILE_NAME = fileName;
        FFmpeg.FFMPEG_PATH = Paths.get(DESTINATION.getPath(), FFMPEG_FILE_NAME).toString();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkChannelHandler.registerPackets();
        FFmpeg.FFMPEG_PATH = new File(DESTINATION, FFMPEG_FILE_NAME).getPath();
        ModInitializer.getInstance().initialize(SOURCE, getClass().getResourceAsStream(SOURCE_INFO), DESTINATION);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) throws NoSuchFieldException, IllegalAccessException, IOException, LWJGLException, InvocationTargetException, NoSuchMethodException {
      //  ForgeUtils.stopRendering(true);
       // ForgeUtils.setTest(true);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    ForgeUtils.runMinecraftTask(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                OpenGLVideoRenderer videoRenderer = new OpenGLVideoRenderer("test");
                                AudioRenderer audioRenderer = new OpenALAudioRenderer();

                                VideoPlayback playback = new FFmpegVideoPlayback(new File(DESTINATION, "intro.mp4"));

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
        }.start();

    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent e) {
        e.registerServerCommand(new EsquisseCommand());

    }


}
