package pong.Pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;

/*
TODO:
    - Add levels
    - Add win screen
DONE:
    - Add sounds
 */

public class Main extends ApplicationAdapter {

    ShapeRenderer shape;
    Ball ball;
    Paddle paddle;
    ArrayList<Block> blocks = new ArrayList<>();
    Sound brickSound, loseSound, paddleSound, winSound;
    String winMessage = "You Win!";
    Boolean lost = false;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        // make ball
        int ballX = Gdx.graphics.getWidth() / 2 - 200;
        int ballY = Gdx.graphics.getHeight() / 2;
        int ballXSpeed = 4;
        int ballYSpeed = 5;
        ball = new Ball(ballX, ballY, 10, ballXSpeed, ballYSpeed);
        // make paddle
        paddle = new Paddle(Gdx.graphics.getWidth()/2, 20, 100, 10);

        // make bricks
        int brickRows = 6;
        int brickColumns = 8;
        for (int row = 0; row < brickRows; row++) {
            for (int column = 0; column < brickColumns; column++) {
                int width = (Gdx.graphics.getWidth() - 5 * brickColumns) / brickColumns;
                int height = 20;
                blocks.add(new Block(column * (width + 5), Gdx.graphics.getHeight() - 25 - row * (height + 5), width, height));
            }
        }
        brickSound = Gdx.audio.newSound(Gdx.files.internal("audio/brick.mp3"));
        System.out.println(System.getProperty("user.dir"));
        paddleSound = Gdx.audio.newSound(Gdx.files.internal("audio/paddle.mp3"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("audio/lose.mp3"));
        //winSound = Gdx.audio.newSound(Gdx.files.internal("audio/win.mp3"));
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
            paddleSound.play();
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
                brickSound.play();
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
        if (ball.y - ball.size < 0 && !lost) {
            loseSound.play();
            lost = true;
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
