package engine.world;
public class Tile {
    public int type;
    public byte renderCode;
    public int x;
    public int y;
    public Tile(int type, byte renderCode, int x, int y) {
        this.type = type;
        this.renderCode = renderCode;
        this.x = x;
        this.y = y;
    }
}
