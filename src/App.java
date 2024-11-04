import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL;

public class App {
    public long windowID;

    public void init() throws Exception {
        if (!glfwInit()) {
            throw new Exception("APP: GLFW FAILED TO INITIALISE!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        this.windowID = glfwCreateWindow(100, 100, "DECELLULAR", 0, 0);
        glfwMaximizeWindow(this.windowID);
        glfwMakeContextCurrent(this.windowID);
        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(this.windowID)) {
            // loop
            glfwPollEvents();
        }
    }

    public void exit() {
        GL.destroy();
        glfwDestroyWindow(this.windowID);
        glfwTerminate();
    }
    
}
