import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Balls {

	private static final double GRAVITY = 0.1;

	private double x, y, radius, MaxCount, CurrentCount, xSpeed, ySpeed;

	public Balls(double x, double y, double radius, double maxCount, double xSpeed) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		MaxCount = maxCount;
		CurrentCount = maxCount;
		this.xSpeed = xSpeed;
		this.ySpeed = 0;
	}

	public void Update() {
		x += xSpeed;
		ySpeed += GRAVITY;
		y += ySpeed;
		if(y >= 512-radius-15)
			ySpeed = -7;
		if(x>=512-radius || x<=0)
			xSpeed = - xSpeed;
	}

	public void draw(GraphicsContext graphicsContext) {
		graphicsContext.setFill(Color.DARKGREEN);
		graphicsContext.fillOval(x, y, radius, radius);
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		graphicsContext.fillText(""+CurrentCount, x+radius/2, y+radius/2);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getMaxCount() {
		return MaxCount;
	}

	public void setMaxCount(double maxCount) {
		MaxCount = maxCount;
	}

	public double getCurrentCount() {
		return CurrentCount;
	}

	public void setCurrentCount(double currentCount) {
		CurrentCount = currentCount;
	}

	public double getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public static double getGravity() {
		return GRAVITY;
	}

}
