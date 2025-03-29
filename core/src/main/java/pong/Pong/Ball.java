package pong.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int x, y, size, xSpeed, ySpeed;
    Color color;

    public Ball(int x, int y, int size, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.color = Color.WHITE;
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;
        if (x - size < 0 || x + size > Gdx.graphics.getWidth()) {
            xSpeed = -xSpeed;
        }
        if (y + size > Gdx.graphics.getHeight()) {
            ySpeed = -ySpeed;
        }
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.circle(x, y, size);
    }

    public void checkCollision(Rectangle rect) {
        if (collidesWith(rect)) {
            ySpeed = -ySpeed;
        }
    }

    public boolean collidesWith(Rectangle rect) {
        if (rect.x + rect.width > x - size &&
            rect.x < x + size &&
            rect.y + rect.height > y - size &&
            rect.y < y + size) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Circle: {X: " + x + ", Y: " + y + ", Radius: " + size + "}";
    }
}
