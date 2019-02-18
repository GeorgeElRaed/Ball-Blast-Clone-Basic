
public class Bullet {
	private double x,y,bulletSpeed;
	
	public Bullet(double x, double y) {
		this.x = x;
		this.y = y;
		bulletSpeed = 10;
	}
	
	public Bullet(double x, double y,double bulletSpeed) {
		this.x = x;
		this.y = y;
		this.bulletSpeed = bulletSpeed;
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

	public double getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(double bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}
	
		
}
