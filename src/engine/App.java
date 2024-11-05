package engine;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL;

public class App {
    public long windowID;
    public Renderer renderer;

    public int width = 600;
    public int height = 400;

    public void init() throws Exception {
        if (!glfwInit()) {
            throw new Exception("APP: GLFW FAILED TO INITIALISE!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        this.windowID = glfwCreateWindow(400, 400, "DECELLULAR", 0, 0);
        glfwMakeContextCurrent(this.windowID);
        GL.createCapabilities();
        
        WindowSizeCallback windowSizeCallback = new WindowSizeCallback(this);
        glfwSetWindowSizeCallback(this.windowID, windowSizeCallback);        
        
        this.renderer = new Renderer(this.windowID);
    }

    public void loop() {
        while (!glfwWindowShouldClose(this.windowID)) {
            // loop
            glfwPollEvents();

            this.renderer.render();
        }
        glfwHideWindow(this.windowID);
    }

    public void exit() {
        GL.destroy();
        glfwDestroyWindow(this.windowID);
        glfwTerminate();
    }
}
