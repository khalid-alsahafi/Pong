package pong.Pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Rectangle {
    int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void draw(ShapeRenderer shape) {
        shape.setColor(Color.WHITE);
        shape.rect(x, y, width, height);
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y + ", Width: " + width + ", Height: " + height;
    }
}
