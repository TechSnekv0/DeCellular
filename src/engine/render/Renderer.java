package engine.render;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWaitEventsTimeout;
import static org.lwjgl.stb.STBImage.*;

import static engine.util.FileIO.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

public class Renderer {

    private long windowID;
    private int windowWidth;
    private int windowHeight;

    // 0.0f, 0.0f, 0, 0,
    // 0.0f, 0.5f, 0, 1,
    // 0.5f, 0.5f, 1, 1,
    // 0.5f, 0.5f, 1, 1,
    // 0.5f, 0.0f, 1, 0,
    // 0.0f, 0.0f, 0, 0

    float[] positions = {
        0.0f, 0.0f,
        0.0f, 0.5f,
        0.5f, 0.5f,
        0.5f, 0.5f,
        0.5f, 0.0f,
        0.0f, 0.0f
    };

    float[] textureCoords = {
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, 0.0f,
        0.0f, 0.0f
    };
    
    private int vao;
    private int vbo;
    private int shaderProgramID;
    private int textureID;
    private int texvbo;
    private ByteBuffer image;
        
    public Renderer(long windowID) {
        this.windowID = windowID;
        GL46.glClearColor(0.2f, 0.6f, 0.5f, 1.0f);

        IntBuffer imageWidth = MemoryUtil.memAllocInt(1);
        IntBuffer imageHeight = MemoryUtil.memAllocInt(1);
        IntBuffer imageChannels = MemoryUtil.memAllocInt(1);
        this.image = ByteBuffer.allocate(imageWidth.get(0)*imageHeight.get(0)*imageChannels.get(0));
        this.image.flip();
        
        ByteBuffer loaded = stbi_load("res/tile/CELL_cuboidal.png", imageWidth, imageHeight, imageChannels, 0);
        this.image = loaded.duplicate(); //! THIS IS THE LINE YOU NEED

        this.image.get(0);

        System.out.println(this.image.capacity());

        ByteBuffer a = ByteBuffer.allocate(120);

        // System.out.println(this.image.limit());
        // System.out.println(this.image.position());
        // System.out.println(this.image.capacity());

        // this.textureID = GL46.glGenTextures();
        // GL46.glBindTexture(GL46.GL_TEXTURE_2D, this.textureID);
        // GL46.glEnable(GL46.GL_TEXTURE_2D);
        // GL46.glTexImage2D(GL46.GL_TEXTURE_2D, 0, GL46.GL_RGBA, imageWidth.get(0), imageHeight.get(0), 0, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, image);
        // GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0);

        // this.shaderProgramID = GL46.glCreateProgram();

        // int vertexShader = GL46.glCreateShader(GL46.GL_VERTEX_SHADER);
        // GL46.glShaderSource(vertexShader, readFile("src\\engine\\shader\\vertexShader.glsl"));
        // GL46.glCompileShader(vertexShader);
        // GL46.glAttachShader(this.shaderProgramID, vertexShader);

        // int fragmentShader = GL46.glCreateShader(GL46.GL_FRAGMENT_SHADER);
        // GL46.glShaderSource(fragmentShader, readFile("src\\engine\\shader\\fragmentShader.glsl"));
        // GL46.glCompileShader(fragmentShader);
        // GL46.glAttachShader(this.shaderProgramID, fragmentShader);

        // GL46.glLinkProgram(this.shaderProgramID);
        // GL46.glValidateProgram(this.shaderProgramID);

        this.vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);
        
        this.vbo = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, positions, GL46.GL_STATIC_DRAW);
        GL46.glVertexAttribPointer(0, 2, GL46.GL_FLOAT, false, 0, 0);

        this.texvbo = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, texvbo);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, textureCoords, GL46.GL_STATIC_DRAW);
        GL46.glVertexAttribPointer(1, 2, GL46.GL_FLOAT, false, 0, 0);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
        GL46.glBindVertexArray(0);
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
        return -1.0f + (float) y / this.windowHeight*2.0f;
    }

    public void setViewport(int width, int height){
        GL46.glViewport(0, 0, width, height);
        this.windowWidth = width;
        this.windowHeight = height;
    }

    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);


        ByteBuffer subimagebuffer = ByteBuffer.allocate(32*48*4);

        GL46.glRasterPos2i(-1, 1);
        GL46.glDrawPixels(64, 96, GL46.GL_RGBA, GL46.GL_UNSIGNED_BYTE, this.image);
        GL46.glPixelZoom(2, -2);

        glfwSwapBuffers(this.windowID);
    }
}
