package io.github.singlerr.esquissev2.init;

import io.github.singlerr.esquissev2.EsquisseV2;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Logger;

public final class ModInitializer {
    private static ModInitializer instance;

    private ModInitializer() {
    }

    public static ModInitializer getInstance() {
        if (instance == null)
            return (instance = new ModInitializer());
        return instance;
    }

    public void initialize(String source, InputStream resourceInfo, File dest) {
        if (!dest.exists())
            dest.mkdirs();

        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceInfo));
        String[] resourceNames = new String[0];
        try {
            resourceNames = reader.readLine().split(" ");
        } catch (IOException e) {
            Logger.getGlobal().info(e.getCause().getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Logger.getGlobal().info(e.getCause().getMessage());
            }
        }
        for (String resourceName : resourceNames) {
            String path = String.join("/", source, resourceName);
            InputStream in = EsquisseV2.class.getResourceAsStream(path);

            try (OutputStream os = Files.newOutputStream(new File(dest, resourceName).toPath())) {
                int len;
                byte[] buf = new byte[4096];
                while ((len = in.read(buf)) != -1)
                    os.write(buf, 0, len);
            } catch (IOException e) {
                Logger.getGlobal().info(e.getCause().getMessage());
            }
        }
    }
}
