package io.github.singlerr.esquissev2;

import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ForgeUtils {
    public static void stopRendering(boolean flag) throws NoSuchFieldException, IllegalAccessException {
        Field isVideoRendering = Minecraft.class.getDeclaredField("isRenderingVideo");
        Field skipDisplay = Minecraft.class.getDeclaredField("skipDisplayUpdate");

        isVideoRendering.setAccessible(true);
        skipDisplay.setAccessible(true);

        isVideoRendering.set(Minecraft.getMinecraft(), flag);
        skipDisplay.set(Minecraft.getMinecraft(), flag);
    }
    public static void setTest(boolean b) throws NoSuchFieldException, IllegalAccessException{
        Field field = Minecraft.class.getDeclaredField("test");
        field.setAccessible(true);
        field.set(Minecraft.getMinecraft(),b);
    }
    public static void setRenderHandler(Runnable runnable) throws NoSuchFieldException, IllegalAccessException {
        Field renderHandler = Minecraft.class.getDeclaredField("renderHandler");
        renderHandler.setAccessible(true);
        renderHandler.set(Minecraft.getMinecraft(), runnable);
    }

    public static void setSkipDisplay(boolean flag) throws NoSuchFieldException, IllegalAccessException {
        Field skipDisplay = Minecraft.class.getDeclaredField("skipDisplayUpdate");

        skipDisplay.setAccessible(true);
        skipDisplay.set(Minecraft.getMinecraft(), flag);
    }

    public static void runMinecraftTask(Runnable runnable) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Minecraft.class.getDeclaredMethod("addStandaloneTask", Runnable.class);
        method.setAccessible(true);
        method.invoke(Minecraft.getMinecraft(), runnable);
    }
    public static void clearScheduledTask() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Minecraft.class.getDeclaredMethod("clearScheduledTask");
        method.setAccessible(true);
        method.invoke(Minecraft.getMinecraft());
    }
}
