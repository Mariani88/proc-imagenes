package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImageArithmeticOperationServiceTest {

	private static final int LIMIT_SCALE = 255;
	private ImageArithmeticOperationService imageArithmeticOperationService;

	@Before
	public void setUp() throws Exception {
		imageArithmeticOperationService = new ImageArithmeticOperationServiceImpl();
	}

	@Test
	public void whenPlusImagesThenPixelsContainsPlusRGBs() {
		WritableImage writableImage = new WritableImage(1, 1);
		writableImage.getPixelWriter().setColor(0, 0, Color.rgb(50, 30, 20));
		WritableImage writableImage2 = new WritableImage(1, 1);
		writableImage2.getPixelWriter().setColor(0, 0, Color.rgb(50, 30, 20));
		Image imageResult = imageArithmeticOperationService.plusImages(writableImage, writableImage2);
		Color firstPositionColor = imageResult.getPixelReader().getColor(0, 0);
		Assert.assertEquals(100, (int) (firstPositionColor.getRed() * LIMIT_SCALE));
		Assert.assertEquals(60, (int) (firstPositionColor.getGreen() * LIMIT_SCALE));
		Assert.assertEquals(40, (int) (firstPositionColor.getBlue() * LIMIT_SCALE));
	}

	@Test
	public void whenPlusImagesAndOneColorValueIsHigherTo255ThenApplyTransformation() {
		WritableImage writableImage = new WritableImage(2, 1);
		writableImage.getPixelWriter().setColor(0, 0, Color.rgb(50, 0, 0));
		writableImage.getPixelWriter().setColor(1, 0, Color.rgb(50, 0, 0));
		WritableImage writableImage2 = new WritableImage(1, 1);
		writableImage2.getPixelWriter().setColor(0, 0, Color.rgb(254, 0, 0));
		Image imageResult = imageArithmeticOperationService.plusImages(writableImage, writableImage2);
		Color firstPositionColor = imageResult.getPixelReader().getColor(0, 0);
		Assert.assertEquals(255, (int) (firstPositionColor.getRed() * LIMIT_SCALE));
		Assert.assertEquals(0, (int) (firstPositionColor.getGreen() * LIMIT_SCALE));
		Assert.assertEquals(0, (int) (firstPositionColor.getBlue() * LIMIT_SCALE));
	}

	@Test
	public void whenPlusImagesAndOnePositionIsNotPresentIntoSecondImageThenPlus0RGB() {
		WritableImage writableImage = new WritableImage(2, 1);
		writableImage.getPixelWriter().setColor(0, 0, Color.rgb(50, 0, 0));
		writableImage.getPixelWriter().setColor(1, 0, Color.rgb(130, 0, 0));
		WritableImage writableImage2 = new WritableImage(1, 1);
		writableImage2.getPixelWriter().setColor(0, 0, Color.rgb(20, 0, 0));
		Image imageResult = imageArithmeticOperationService.plusImages(writableImage, writableImage2);
		Assert.assertEquals(2, (int) imageResult.getWidth());
		Assert.assertEquals(1, (int) imageResult.getHeight());
		Color absentPositionFromSecondImage = imageResult.getPixelReader().getColor(1, 0);
		Assert.assertEquals(130, (int) (absentPositionFromSecondImage.getRed() * LIMIT_SCALE));
		Assert.assertEquals(0, (int) (absentPositionFromSecondImage.getBlue() * LIMIT_SCALE));
		Assert.assertEquals(0, (int) (absentPositionFromSecondImage.getGreen() * LIMIT_SCALE));
	}

	@Test
	public void whenPlusImagesAndOnePositionIsNotPresentIntoFirstImageThenPlus0RGB() {
		WritableImage writableImage = new WritableImage(1, 1);
		writableImage.getPixelWriter().setColor(0, 0, Color.rgb(50, 0, 0));
		WritableImage writableImage2 = new WritableImage(2, 1);
		writableImage2.getPixelWriter().setColor(0, 0, Color.rgb(20, 0, 0));
		writableImage2.getPixelWriter().setColor(1, 0, Color.rgb(130, 0, 0));
		Image imageResult = imageArithmeticOperationService.plusImages(writableImage, writableImage2);
		Assert.assertEquals(2, (int) imageResult.getWidth());
		Assert.assertEquals(1, (int) imageResult.getHeight());
		Color absentPositionFromFirstImage = imageResult.getPixelReader().getColor(1, 0);
		Assert.assertEquals(130, (int) (absentPositionFromFirstImage.getRed() * LIMIT_SCALE));
		Assert.assertEquals(0, (int) (absentPositionFromFirstImage.getBlue() * LIMIT_SCALE));
		Assert.assertEquals(0, (int) (absentPositionFromFirstImage.getGreen() * LIMIT_SCALE));
	}

	/*
	@Test
	public void whenMultiplyImageByScalarThenMultiplyIt() {
		WritableImage writableImage = new WritableImage(2, 2);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		pixelWriter.setColor(0, 0, Color.rgb(100, 50, 25));
		pixelWriter.setColor(0, 1, Color.rgb(100, 50, 25));
		pixelWriter.setColor(1, 0, Color.rgb(100, 50, 25));
		pixelWriter.setColor(1, 1, Color.rgb(100, 50, 25));
		Image image = creationImageService.multiplyImageByScalar(2, writableImage);
		PixelReader pixelReader = image.getPixelReader();
		assertRGB(200, 100, 50, pixelReader.getColor(0,0));
		assertRGB(200, 100, 50, pixelReader.getColor(0,1));
		assertRGB(200, 100, 50, pixelReader.getColor(1,0));
		assertRGB(200, 100, 50, pixelReader.getColor(1,1));
	}

	@Test
	public void whenMultiplyImageByScalarAndResultIsSuperiorToLimitScaleThenTransformIt() {
		int scalar = 4;
		WritableImage writableImage = new WritableImage(1, 1);
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		pixelWriter.setColor(0, 0, Color.rgb(200, 50, 25));
		Image image = creationImageService.multiplyImageByScalar(scalar, writableImage);
		PixelReader pixelReader = image.getPixelReader();
		assertRGB(200, 200, 100, pixelReader.getColor(0,0));
	}*/
}