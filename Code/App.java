import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;



@SuppressWarnings("unused")
public class App extends Application {
	

	public static final int WIDTH = 600, HEIGHT = 600;
	Long Timer = System.currentTimeMillis();
	double x = 512 / 2, y = 512 - 50 - 15;
	ArrayList<Bullet> bullets = new ArrayList<>();
	ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
	ArrayList<Balls> balls = new ArrayList<>();
	ArrayList<Balls> BallsToRemove = new ArrayList<>();
	ArrayList<Balls> BallsToAdd = new ArrayList<>();
	private boolean die;

	public static void main(String[] args) {
		launch(args);
	}

	
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("canvas Example");
		Group root = new Group();
		Group enemiesGroup = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);

		Canvas canvas = new Canvas(512, 512);
		root.getChildren().add(canvas);
		root.getChildren().add(enemiesGroup);

		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

		ArrayList<String> inputs = new ArrayList<>();

		Image sun = new Image("file:sun.png");

		setInputs(scene, graphicsContext, sun, inputs);

		Image earth = new Image("file:earth.png");
		Image space = new Image("file:space.png");
		Image pepe = new Image("file:pepe.png");
		Image heart = new Image("file:Heart.png");
		Image guitar = new Image("file:guitar.png");
		final long startNanoTimer = System.nanoTime();

		BallSpawn();

		AnimatedImage animatedImage = new AnimatedImage(0.100, sun, earth, pepe, guitar);
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				double t = (currentNanoTime - startNanoTimer) / 1000000000.0;

				
				graphicsContext.clearRect(0, 0, 512, 512);

				PlayerSpawn(graphicsContext);
				updateBalls();
				BulletMove(graphicsContext);
				BallDraw(graphicsContext);
				PlayerMove(inputs);

			}

		}.start();

		stage.show();

	}

	private void updateBalls() {
		for (Balls ball : balls) {
			ball.Update();
			if(ball.getCurrentCount() <= 0) {
				BallsToRemove.add(ball);
				BallsToAdd.add(new Balls(ball.getX(), ball.getY(), 3*ball.getRadius()/4, ball.getMaxCount()/2,ball.getxSpeed()));
				BallsToAdd.add(new Balls(ball.getX(), ball.getY(), 3*ball.getRadius()/4, ball.getMaxCount()/2,-ball.getxSpeed()));
			}
		}
		balls.addAll(BallsToAdd);
		balls.removeAll(BallsToRemove);
		BallsToRemove = new ArrayList<>();
		BallsToAdd = new ArrayList<>();
	}

	private void PlayerSpawn(GraphicsContext graphicsContext) {
		graphicsContext.setFill(Color.RED);
		graphicsContext.fillRect(x, y, 50, 50);
	}

	private void BallDraw(GraphicsContext graphicsContext) {
		for (Balls ball : balls) {
			ball.draw(graphicsContext);
		}
	}

	private void BallSpawn() {
		balls.add(new Balls(512 / 2, 0, 50, 100, 2.5));		
	}

	private void BulletMove(GraphicsContext graphicsContext) {
		for (Bullet bullet : bullets) {
			graphicsContext.setFill(Color.BLUE);
			graphicsContext.fillRect(bullet.getX(), bullet.getY(), 4, 7);
			bullet.setY(bullet.getY() - bullet.getBulletSpeed());
			for (Balls ball : balls) {

				if (isCollisionCircle(bullet.getX(), bullet.getY(), ball.getX(), ball.getY(), ball.getRadius())
						|| isCollisionCircle(bullet.getX() + 4, bullet.getY(), ball.getX(), ball.getY(),
								ball.getRadius())) {
					ball.setCurrentCount(ball.getCurrentCount() - 1);
					bulletsToRemove.add(bullet);

				}
			}

			if (!isInside(bullet.getX(), bullet.getY())) {
				if (!bulletsToRemove.contains(bullet))
					bulletsToRemove.add(bullet);
			}
		}
		bullets.removeAll(bulletsToRemove);
		bulletsToRemove = new ArrayList<>();
	}

	private boolean isInside(double X, double Y) {
		if (X >= 0 && X <= 512 - 4 && Y >= 0 && Y <= 512 - 7)
			return true;
		return false;
	}

	private boolean isCollision(double px, double py, double cx, double cy, double cw, double ch) {
		if (px > cx && px < cx + cw && py > cy && py < cy + ch) {
			return true;
		}
		return false;
	}

	private boolean isCollisionCircle(double px, double py, double cx, double cy, double radius) {
		return Math.sqrt((Math.pow(px - cx - radius / 2, 2.0) + Math.pow(py - cy - radius / 2, 2.0))) <= radius / 2;
	}

	private void PlayerMove(ArrayList<String> inputs) {
		double MoveSpeed = 3.5;
		if (inputs.contains("SHIFT")) {
			MoveSpeed = 7;
		}
		if (inputs.contains("UP")) {
			y -= MoveSpeed;
		}
		if (inputs.contains("DOWN")) {
			y += MoveSpeed;
		}
		if (inputs.contains("LEFT")) {
			x -= MoveSpeed;
		}
		if (inputs.contains("RIGHT")) {
			x += MoveSpeed;
		}
		if (inputs.contains("SPACE")) {
			shoot();
		}

		x = clamp(x, 0, 512 - 50);
		y = clamp(y, 0, 512 - 50);

	}

	private void shoot() {
		if (System.currentTimeMillis() - Timer >= 1) {
			bullets.add(new Bullet(x + 25, y - 25 / 2));
			bullets.add(new Bullet(x + 25 + 5, y - 25 / 2));
			bullets.add(new Bullet(x + 25 - 5, y - 25 / 2));
			Timer = System.currentTimeMillis();
		}
	}

	private double clamp(double x, int min, int max) {
		return x <= min ? min : x >= max ? max : x;
	}

	public void setInputs(Scene scene, GraphicsContext graphicsContext, Image sun, ArrayList<String> inputs) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				if (!inputs.contains(code))
					inputs.add(code);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				inputs.remove(code);
			}
		});
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				graphicsContext.clearRect(0, 0, 512, 512);
				graphicsContext.drawImage(sun, e.getSceneX() - sun.getWidth() / 2, e.getSceneY() - sun.getHeight() / 2);
			}
		});

	}

}
