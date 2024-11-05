package engine.util;

import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import engine.App;

public class WindowSizeCallback implements GLFWWindowSizeCallbackI{

    private App app;

    public WindowSizeCallback(App app) {
        this.app = app;
    }

    @Override
    public void invoke(long windowID, int width, int height) {
        this.app.renderer.setViewport(width, height);
        this.app.width = width;
        this.app.height = height;
    }

}
