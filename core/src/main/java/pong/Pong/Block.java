package pong.Pong;

public class Block extends Rectangle {
    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public String toString() {
        return "Block: {" + super.toString() + "}";
    }
}
