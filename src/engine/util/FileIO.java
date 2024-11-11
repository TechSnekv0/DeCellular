package engine.util;

import static org.lwjgl.stb.STBImage.stbi_load;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.system.MemoryUtil;

public class FileIO {
    public static String readFile(String filePath) {
        String str;
        try {
            str = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException excp) {
            throw new RuntimeException("Error reading file [" + filePath + "]", excp);
        }
        return str;
    }

    public static ByteBuffer loadImage(String path) {
        IntBuffer imageWidth = MemoryUtil.memAllocInt(1);
        IntBuffer imageHeight = MemoryUtil.memAllocInt(1);
        IntBuffer imageChannels = MemoryUtil.memAllocInt(1);

        ByteBuffer loaded = stbi_load(path, imageWidth, imageHeight, imageChannels, 0);
        return loaded.duplicate();
    }

    public static ByteBuffer loadImage(String path, IntBuffer imageWidth, IntBuffer imageHeight, IntBuffer imageChannels) {
        ByteBuffer loaded = stbi_load(path, imageWidth, imageHeight, imageChannels, 0);
        return loaded.duplicate();
    }
}
