package engine.render;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static engine.util.FileIO.*;
import static engine.util.Constants.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.TimerTask;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

public class Renderer {

    private long windowID;
    private int windowWidth;
    private int windowHeight;

    private int TILESIZE = 16;
    
    private ByteBuffer image;
    private IntBuffer imageWidth = MemoryUtil.memAllocInt(1);
    private IntBuffer imageHeight = MemoryUtil.memAllocInt(1);
    private IntBuffer imageChannels = MemoryUtil.memAllocInt(1);
    private ByteBuffer[] tilesheet;
        
    public Renderer(long windowID) {
        this.windowID = windowID;
        GL46.glClearColor(40.0f/256, 0, 33.0f/256, 1.0f);

        this.image = loadImage("res\\tile\\CELL_cuboidal.png", imageWidth, imageHeight, imageChannels);

        int width = imageWidth.get(0);
        int height = imageHeight.get(0);
        int channel = imageChannels.get(0);
        System.out.println(width*height*channel);
        System.out.println(this.image.capacity());

        this.tilesheet = new ByteBuffer[width*height/TILESIZE/TILESIZE];

        tilesheet[0] = getTileImageFromSheet(width, height, channel, 0, 0);

        this.image.rewind();

        GL46.glEnable(GL46.GL_ALPHA_TEST);
        GL46.glAlphaFunc(GL46.GL_EQUAL, 1);
    }

    private ByteBuffer getTileImageFromSheet(int width, int height, int channel, int x, int y) {
        ByteBuffer out = BufferUtils.createByteBuffer(TILESIZE*TILESIZE*channel);        
        for (int a = y*TILESIZE; a < (y+1)*TILESIZE; a++) {
            for (int b = x*TILESIZE; b < (x+1)*TILESIZE; b++) {
                for (int c = 0; c < channel; c++) {
                    // System.out.println(this.image.limit());
                    // System.out.println(c+(b+a*width)*channel);
                    this.image.position(c+(b+a*width)*channel);
                    out.put(this.image.get());
                }
            }
        }
        out.flip();
        return out;
    }

    private float[] getPositionsFromRectangle(int x, int y, int width, int height) {
        float[] positions = new float[12];

        positions[0] = positions[10] = convertXPixelToGLCoord(x);
        positions[1] = positions[11] = convertYPixelToGLCoord(y);
        positions[2] = convertXPixelToGLCoord(x+width);
        positions[3] = convertYPixelToGLCoord(y);
        positions[4] = positions[6] = convertXPixelToGLCoord(x+width);
        positions[5] = positions[7] = convertYPixelToGLCoord(y+height);
        positions[8] = convertXPixelToGLCoord(x);
        positions[9] = convertYPixelToGLCoord(y+height);

        return positions;
    }

    private float convertXPixelToGLCoord(int x) {
        return -1.0f + (float) x / this.windowWidth*2.0f;
    }
    private float convertYPixelToGLCoord(int y) {
        return 1.0f - (float) y / this.windowHeight*2.0f;
    }

    public void setViewport(int width, int height){
        GL46.glViewport(0, 0, width, height);
        this.windowWidth = width;
        this.windowHeight = height;
    }

    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);

        GL46.glRasterPos2i((int)convertXPixelToGLCoord(0), (int)convertYPixelToGLCoord(0));
        // GL46.glDrawPixels(imageWidth.get(0), imageHeight.get(0), GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, this.image);
        GL46.glRasterPos2i((int)convertXPixelToGLCoord(0), (int)convertYPixelToGLCoord(0));
        GL46.glDrawPixels(TILESIZE, TILESIZE, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, this.tilesheet[0]);
        GL46.glPixelZoom(SCALE, -SCALE);

        glfwSwapBuffers(this.windowID);
    }
}
