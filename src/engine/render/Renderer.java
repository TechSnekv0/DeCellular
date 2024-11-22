package engine.render;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static engine.util.FileIO.*;
import static engine.util.Constants.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

import engine.world.Tile;

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
    private int[][] world = new int[16][16];
    private Tile tile;
        
    public Renderer(long windowID) {
        this.windowID = windowID;
        GL46.glClearColor(40.0f/256, 0, 33.0f/256, 1.0f);

        this.image = loadImage("res\\tile\\TILE_marrow.png", imageWidth, imageHeight, imageChannels);

        int width = imageWidth.get(0);
        int height = imageHeight.get(0);
        int channel = imageChannels.get(0);
        System.out.println(width*height*channel);
        System.out.println(this.image.capacity());

        this.tilesheet = new ByteBuffer[85];

        this.tilesheet[0] = this.getTileImageFromSheet(width, height, channel, 1, 0);
        this.tilesheet[1] = this.getTileImageFromSheet(width, height, channel, 9, 0);
        this.tilesheet[2] = this.getTileImageFromSheet(width, height, channel, 1, 4);
        this.tilesheet[3] = this.getTileImageFromSheet(width, height, channel, 5, 4);
        this.tilesheet[4] = this.getTileImageFromSheet(width, height, channel, 9, 4);
        this.tilesheet[5] = this.getTileImageFromSheet(width, height, channel, 1, 8);
        this.tilesheet[6] = this.getTileImageFromSheet(width, height, channel, 5, 8);
        this.tilesheet[7] = this.getTileImageFromSheet(width, height, channel, 9, 8);
        this.tilesheet[8] = this.getTileImageFromSheet(width, height, channel, 1, 12);
        this.tilesheet[9] = this.getTileImageFromSheet(width, height, channel, 5, 12);
        this.tilesheet[10] = this.getTileImageFromSheet(width, height, channel, 9, 12);
        this.tilesheet[11] = this.getTileImageFromSheet(width, height, channel, 3, 16);
        this.tilesheet[12] = this.getTileImageFromSheet(width, height, channel, 4, 16);
        this.tilesheet[13] = this.getTileImageFromSheet(width, height, channel, 5, 16);
        this.tilesheet[14] = this.getTileImageFromSheet(width, height, channel, 6, 16);
        this.tilesheet[15] = this.getTileImageFromSheet(width, height, channel, 7, 16);

        this.tilesheet[16] = this.getTileImageFromSheet(width, height, channel, 4, 0);
        this.tilesheet[17] = this.getTileImageFromSheet(width, height, channel, 8, 1);
        this.tilesheet[18] = this.getTileImageFromSheet(width, height, channel, 4, 4);
        this.tilesheet[19] = this.getTileImageFromSheet(width, height, channel, 0, 8);
        this.tilesheet[20] = this.getTileImageFromSheet(width, height, channel, 4, 8);
        this.tilesheet[21] = this.getTileImageFromSheet(width, height, channel, 8, 8);
        this.tilesheet[22] = this.getTileImageFromSheet(width, height, channel, 4, 12);

        this.tilesheet[23] = this.getTileImageFromSheet(width, height, channel, 6, 0);
        this.tilesheet[24] = this.getTileImageFromSheet(width, height, channel, 2, 1);
        this.tilesheet[25] = this.getTileImageFromSheet(width, height, channel, 6, 4);
        this.tilesheet[26] = this.getTileImageFromSheet(width, height, channel, 2, 8);
        this.tilesheet[27] = this.getTileImageFromSheet(width, height, channel, 6, 8);
        this.tilesheet[28] = this.getTileImageFromSheet(width, height, channel, 10, 8);
        this.tilesheet[29] = this.getTileImageFromSheet(width, height, channel, 6, 12);

        this.tilesheet[30] = this.getTileImageFromSheet(width, height, channel, 1, 2);
        this.tilesheet[31] = this.getTileImageFromSheet(width, height, channel, 9, 2);
        this.tilesheet[32] = this.getTileImageFromSheet(width, height, channel, 5, 5);
        this.tilesheet[33] = this.getTileImageFromSheet(width, height, channel, 1, 9);
        this.tilesheet[34] = this.getTileImageFromSheet(width, height, channel, 5, 9);
        this.tilesheet[35] = this.getTileImageFromSheet(width, height, channel, 9, 9);
        this.tilesheet[36] = this.getTileImageFromSheet(width, height, channel, 5, 13);
        this.tilesheet[37] = this.getTileImageFromSheet(width, height, channel, 5, 17);

        this.tilesheet[38] = this.getTileImageFromSheet(width, height, channel, 5, 3);
        this.tilesheet[39] = this.getTileImageFromSheet(width, height, channel, 1, 7);
        this.tilesheet[40] = this.getTileImageFromSheet(width, height, channel, 5, 7);
        this.tilesheet[41] = this.getTileImageFromSheet(width, height, channel, 9, 7);
        this.tilesheet[42] = this.getTileImageFromSheet(width, height, channel, 5, 11);
        this.tilesheet[43] = this.getTileImageFromSheet(width, height, channel, 5, 15);

        this.tilesheet[44] = this.getTileImageFromSheet(width, height, channel, 2, 2);
        this.tilesheet[45] = this.getTileImageFromSheet(width, height, channel, 2, 5);
        this.tilesheet[46] = this.getTileImageFromSheet(width, height, channel, 2, 9);
        this.tilesheet[47] = this.getTileImageFromSheet(width, height, channel, 6, 5);
        this.tilesheet[48] = this.getTileImageFromSheet(width, height, channel, 6, 9);
        this.tilesheet[49] = this.getTileImageFromSheet(width, height, channel, 6, 13);
        this.tilesheet[50] = this.getTileImageFromSheet(width, height, channel, 10, 9);
        this.tilesheet[51] = this.getTileImageFromSheet(width, height, channel, 10, 13);

        this.tilesheet[52] = this.getTileImageFromSheet(width, height, channel, 8, 2);
        this.tilesheet[53] = this.getTileImageFromSheet(width, height, channel, 8, 5);
        this.tilesheet[54] = this.getTileImageFromSheet(width, height, channel, 8, 9);
        this.tilesheet[55] = this.getTileImageFromSheet(width, height, channel, 4, 5);
        this.tilesheet[56] = this.getTileImageFromSheet(width, height, channel, 4, 9);
        this.tilesheet[57] = this.getTileImageFromSheet(width, height, channel, 4, 13);
        this.tilesheet[58] = this.getTileImageFromSheet(width, height, channel, 0, 9);
        this.tilesheet[59] = this.getTileImageFromSheet(width, height, channel, 0, 13);

        this.tilesheet[60] = this.getTileImageFromSheet(width, height, channel, 4, 3);
        this.tilesheet[61] = this.getTileImageFromSheet(width, height, channel, 0, 7);
        this.tilesheet[62] = this.getTileImageFromSheet(width, height, channel, 4, 7);
        this.tilesheet[63] = this.getTileImageFromSheet(width, height, channel, 8, 7);
        this.tilesheet[64] = this.getTileImageFromSheet(width, height, channel, 4, 11);
        this.tilesheet[65] = this.getTileImageFromSheet(width, height, channel, 8, 11);

        this.tilesheet[66] = this.getTileImageFromSheet(width, height, channel, 6, 3);
        this.tilesheet[67] = this.getTileImageFromSheet(width, height, channel, 2, 7);
        this.tilesheet[68] = this.getTileImageFromSheet(width, height, channel, 6, 7);
        this.tilesheet[69] = this.getTileImageFromSheet(width, height, channel, 10, 7);
        this.tilesheet[70] = this.getTileImageFromSheet(width, height, channel, 6, 11);
        this.tilesheet[71] = this.getTileImageFromSheet(width, height, channel, 2, 11);
        
        this.tilesheet[72] = this.getTileImageFromSheet(width, height, channel, 11, 0);
        this.tilesheet[73] = this.getTileImageFromSheet(width, height, channel, 12, 0);
        this.tilesheet[74] = this.getTileImageFromSheet(width, height, channel, 13, 0);
        this.tilesheet[75] = this.getTileImageFromSheet(width, height, channel, 14, 0);

        this.tilesheet[76] = this.getTileImageFromSheet(width, height, channel, 11, 2);
        this.tilesheet[77] = this.getTileImageFromSheet(width, height, channel, 12, 2);
        this.tilesheet[78] = this.getTileImageFromSheet(width, height, channel, 13, 2);
        this.tilesheet[79] = this.getTileImageFromSheet(width, height, channel, 11, 3);
        this.tilesheet[80] = this.getTileImageFromSheet(width, height, channel, 12, 3);
        this.tilesheet[81] = this.getTileImageFromSheet(width, height, channel, 13, 3);
        this.tilesheet[82] = this.getTileImageFromSheet(width, height, channel, 11, 4);
        this.tilesheet[83] = this.getTileImageFromSheet(width, height, channel, 12, 4);
        this.tilesheet[84] = this.getTileImageFromSheet(width, height, channel, 13, 4);

        this.tile = new Tile(TILE_BONE, (byte)0b01011111, 0, 0);

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

    private boolean isTilePresent(byte renderCode, int position) {
        return (renderCode >> (position-1) & 1) == 1;
    }

    private void renderTile(int type, byte renderCode, float x, float y) {
        if (type == TILE_EMPTY) {
            return;
        } else if (type == TILE_BONE) {
            GL46.glRasterPos2f(x, y);
            if (isTilePresent(renderCode, 2) && isTilePresent(renderCode, 4) && isTilePresent(renderCode, 5) && isTilePresent(renderCode, 7)) {
                if (!isTilePresent(renderCode, 6)) {
                    GL46.glDrawPixels(TILESIZE, TILESIZE, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, this.tilesheet[1]);
                } else if (!isTilePresent(renderCode, 8)) {
                    GL46.glDrawPixels(TILESIZE, TILESIZE, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, this.tilesheet[0]);
                } else {
                    GL46.glDrawPixels(TILESIZE, TILESIZE, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, this.tilesheet[6]);
                }
            }
        }
    }

    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);

        renderTile(tile.type, tile.renderCode, convertXPixelToGLCoord(0), convertYPixelToGLCoord(0));

        GL46.glPixelZoom(SCALE, -SCALE);
        glfwSwapBuffers(this.windowID);
    }
}
