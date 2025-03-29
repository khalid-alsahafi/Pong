package pong.Pong;

import com.badlogic.gdx.Gdx;

public class Paddle extends Rectangle {

    Paddle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update(int x, int y) {
        super.update(x, y);

        this.y = -this.y + Gdx.graphics.getHeight() - height/2;

        if (this.x < 0) {
            this.x = 0;
        } else if (this.x + this.width > Gdx.graphics.getWidth()) {
            this.x = Gdx.graphics.getWidth() - this.width;
        }
    }
}
