package engine;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

import org.lwjgl.opengl.GL46;

public class Renderer {

    private long windowID;

    public Renderer(long windowID) {
        this.windowID = windowID;
        GL46.glClearColor(0.2f, 0.6f, 0.5f, 1.0f);
    }

    public void setViewport(int width, int height){
        GL46.glViewport(0, 0, width, height);
    }

    public void render() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);

        // GL46.glBegin(GL46.GL_QUADS);
        //     GL46.glColor3f(1.0f, 1.0f, 1.0f);
        //     GL46.glVertex2f(-0.5f, -0.5f);
        //     GL46.glVertex2f(0.5f, -0.5f);
        //     GL46.glVertex2f(0.5f, 0.5f);
        //     GL46.glVertex2f(-0.5f, 0.5f);
        // GL46.glEnd();

        glfwSwapBuffers(this.windowID);
    }
}
