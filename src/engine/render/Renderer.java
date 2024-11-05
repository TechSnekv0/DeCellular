package engine.render;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

import org.lwjgl.opengl.GL46;

public class Renderer {

    private long windowID;
    private int windowWidth;
    private int windowHeight;

    float[] positions = {
        0.0f, 0.0f,
        0.0f, 0.5f,
        0.5f, 0.5f,
        0.5f, 0.5f,
        0.5f, 0.0f,
        0.0f, 0.0f
    };
    
    private int vao;
    private int vbo;
        
    public Renderer(long windowID) {
        this.windowID = windowID;
        GL46.glClearColor(0.2f, 0.6f, 0.5f, 1.0f);

        this.vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);
        
        this.vbo = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, positions, GL46.GL_STATIC_DRAW);

        GL46.glVertexAttribPointer(0, 2, GL46.GL_FLOAT, false, 0, 0);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
        GL46.glBindVertexArray(0);
    }

    private float[] getPositionsFromRectangle(int x, int y, int width, int height) {
        float[] positions = new float[12];

        positions[0] = positions[10] = -1.0f + (float) x / this.windowWidth * 2.0f;
        positions[1] = positions[11] = 1.0f - (float) y / this.windowHeight * 2.0f;
        positions[2] = -1.0f + (float) (x+width) / this.windowWidth * 2.0f;
        positions[3] = 1.0f - (float) (y) / this.windowHeight * 2.0f;
        positions[4] = positions[6] = -1.0f + (float) (x+width) / this.windowWidth * 2.0f;
        positions[5] = positions[7] = 1.0f - (float) (y+height) / this.windowHeight * 2.0f;
        positions[8] = -1.0f + (float) (x) / this.windowWidth * 2.0f;
        positions[9] = 1.0f - (float) (y+height) / this.windowHeight * 2.0f;

        return positions;
    }

    public void setViewport(int width, int height){
        GL46.glViewport(0, 0, width, height);
        this.windowWidth = width;
        this.windowHeight = height;
    }

    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, getPositionsFromRectangle(10, 10, 50, 50), GL46.GL_STATIC_DRAW);
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);

        GL46.glBindVertexArray(vao);
        GL46.glEnableVertexAttribArray(0);

        GL46.glDrawArrays(GL46.GL_TRIANGLES, 0, 6);

        GL46.glDisableVertexAttribArray(0);
        GL46.glBindVertexArray(0);

        glfwSwapBuffers(this.windowID);
    }
}
