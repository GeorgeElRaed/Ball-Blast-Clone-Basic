import javafx.scene.image.Image;

class AnimatedImage {
	public Image[] frames;
	public double duration;

	public AnimatedImage(double duration, Image... frames) {
		this.frames = frames;
		this.duration = duration;
	}

	public Image getFrame(double time) {
		int index = (int) ((time % (frames.length * duration)) / duration);
		return frames[index];
	}
}
