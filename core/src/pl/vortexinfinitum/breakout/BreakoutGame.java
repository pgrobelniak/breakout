package pl.vortexinfinitum.breakout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BreakoutGame extends ApplicationAdapter {

	int PADDLE_SPEED = 450;

	int PADDLE_WIDTH = 150;

	int BLOCK_HEIGHT = 30;

	int BALL_SIZE = 30;

	int BALL_SPEED = 400;

	int ROWS = 4;

	int COLS = 10;

	int EMPTY_ROWS = 3;

	Random random = new Random();

	SpriteBatch batch;

	Texture img;

	Rectangle paddleRect;

	Rectangle ballRect;

	Vector2 ballVel;

	boolean gameRunning;

	List<Rectangle> blocks = new ArrayList<>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		resetGame();
	}

	@Override
	public void render () {
		updatePaddle();
		updateBall();
		draw();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	private void resetGame() {
		gameRunning = false;
		paddleRect = new Rectangle((Gdx.graphics.getWidth() - PADDLE_WIDTH) / 2f, 0, PADDLE_WIDTH, BLOCK_HEIGHT);
		ballRect = new Rectangle((Gdx.graphics.getWidth() - BALL_SIZE) / 2f, BLOCK_HEIGHT, BALL_SIZE, BALL_SIZE);
		ballVel = new Vector2(random.nextFloat() * 2 - 1, 1);
		blocks.clear();
		int blockWidth = Gdx.graphics.getWidth() / COLS;
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				blocks.add(new Rectangle(c * blockWidth + 1, Gdx.graphics.getHeight() - EMPTY_ROWS * BLOCK_HEIGHT - r *
						BLOCK_HEIGHT - BLOCK_HEIGHT - 1, blockWidth - 2, BLOCK_HEIGHT - 2));
			}
		}
	}

	private void updatePaddle() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			paddleRect.x -= Gdx.graphics.getDeltaTime() * PADDLE_SPEED;
			gameRunning = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			paddleRect.x += Gdx.graphics.getDeltaTime() * PADDLE_SPEED;
			gameRunning = true;
		}
	}

	private void updateBall() {
		if (gameRunning) {
			ballRect.x += ballVel.x * Gdx.graphics.getDeltaTime() * BALL_SPEED;
			ballRect.y += ballVel.y * Gdx.graphics.getDeltaTime() * BALL_SPEED;
			if (ballRect.overlaps(paddleRect)) {
				ballVel.x = (ballRect.x + BALL_SIZE / 2f - paddleRect.x - PADDLE_WIDTH / 2f) / (PADDLE_WIDTH / 2f);
				ballVel.y = -ballVel.y;
				ballRect.y = paddleRect.y + BLOCK_HEIGHT;
			} else if (ballRect.y < 0) {
				resetGame();
			} else {
				List<Rectangle> bouncedBlocks = new ArrayList<>();
				for (Rectangle block : blocks) {
					if (block.overlaps(ballRect)) {
						bouncedBlocks.add(block);
					}
				}
				if (bouncedBlocks.size() > 0) {
					for (Rectangle block : bouncedBlocks) {
						blocks.remove(block);
					}
					if (blocks.size() == 0) {
						resetGame();
					} else {
						ballVel.y = -ballVel.y;
					}
				}
				if (ballRect.y + BALL_SIZE > Gdx.graphics.getHeight()) {
					ballRect.y = Gdx.graphics.getHeight() - BALL_SIZE;
					ballVel.y = -ballVel.y;
				}
				if (ballRect.x < 0) {
					ballRect.x = 0;
					ballVel.x = -ballVel.x;
				}
				if (ballRect.x + BALL_SIZE > Gdx.graphics.getWidth()) {
					ballRect.x = Gdx.graphics.getWidth() - BALL_SIZE;
					ballVel.x = -ballVel.x;
				}
			}
		}
	}

	private void draw() {
		ScreenUtils.clear(0, 0, 1, 1);
		batch.begin();
		batch.draw(img, paddleRect.x, paddleRect.y, paddleRect.width, paddleRect.height);
		batch.draw(img, ballRect.x, ballRect.y, ballRect.width, ballRect.height);
		for (Rectangle rectangle : blocks) {
			batch.draw(img, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
		batch.end();
	}
}
