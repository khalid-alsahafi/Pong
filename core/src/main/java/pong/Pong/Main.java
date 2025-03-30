package pong.Pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.Random;



public class Main extends ApplicationAdapter {

    ShapeRenderer shape;
    Ball ball;
    Paddle paddle;
    ArrayList<Block> blocks = new ArrayList<>();

    @Override
    public void create() {
        shape = new ShapeRenderer();
        // make ball
        int ballX = Gdx.graphics.getWidth() / 2 - 200;
        int ballY = Gdx.graphics.getHeight() / 2;
        int xSpeed = 4;
        int ySpeed = 5;
        ball = new Ball(ballX, ballY, 10, xSpeed, ySpeed);
        // make paddle
        paddle = new Paddle(Gdx.graphics.getWidth()/2, 20, 100, 10);

        // make bricks
        int rows = 6;
        int cols = 8;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                int width = (Gdx.graphics.getWidth() - 5 * cols) / cols;
                int height = 20;
                blocks.add(new Block(column * (width + 5), Gdx.graphics.getHeight() - 25 - row * (height + 5), width, height));
            }
        }
    }

    @Override
    public void render() {
        // fill screen with black
        ScreenUtils.clear(Color.BLACK);
        // begin drawing
        shape.begin(ShapeRenderer.ShapeType.Filled);
        // update objects
        ball.update();
        paddle.update(Gdx.input.getX() - paddle.width/2, Gdx.graphics.getHeight() - 20);
        ball.checkCollision(paddle);
        // ball-paddle collision
        if (ball.collidesWith(paddle)) {
            int distToPaddleCenter;
            int paddleCenter = paddle.x + paddle.width/2;
            distToPaddleCenter = ball.x - paddleCenter;
            int bounceEffectForce = 3;
            double bounceVector = (double) distToPaddleCenter /(paddle.width/2.0) * bounceEffectForce;
            System.out.printf("%d/%f * %d = %f\n", distToPaddleCenter, paddle.width/2.0, bounceEffectForce, bounceVector);
            if (Math.abs(bounceVector) >= 1) {
            ball.xSpeed = (int)bounceVector;
            }
        }
        // ball-block collision loop
        for (int i = 0; i < blocks.size(); i++) {
            if (ball.collidesWith(blocks.get(i))) {
                // right-left collision
                if (ball.y > blocks.get(i).y && ball.y < blocks.get(i).y + blocks.get(i).height &&
                    !(ball.x > blocks.get(i).x && ball.x < blocks.get(i).x + blocks.get(i).width)) {
                    ball.xSpeed = -ball.xSpeed;
                }
                // top-bottom collision
                else {
                    ball.ySpeed = -ball.ySpeed;
                }
                blocks.remove(blocks.get(i));
            }
        }
        // draw objects
        ball.draw(shape);
        paddle.draw(shape);
        for (Block block : blocks) {
            block.draw(shape);
        }
        // finish drawing
        shape.end();
    }

    @Override
    public void dispose() {
    }
}
