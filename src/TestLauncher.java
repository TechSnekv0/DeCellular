public class TestLauncher {
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.init();
        app.loop();
        app.exit();
    }
}
