package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class CreationImageServiceTest {

	private static final int LIMIT_SCALE = 255;
	private CreationImageService creationImageService;

	@Before
	public void setUp() {
		creationImageService = new CreationImageServiceImpl();
	}

	@Test
	public void whenCreateBinaryImageWithCenterQuadrateThenCreateIt() {
		Image binaryImageWithCenterQuadrate = creationImageService.createBinaryImageWithCenterQuadrate(4, 4);
		PixelReader pixelReader = binaryImageWithCenterQuadrate.getPixelReader();
		assertWhiteQuadrate(pixelReader);
		assertBlackContorn(pixelReader);
	}

	@Test
	public void whenPlusImagesThenPixelsContainsPlusRGBs() {
		WritableImage writableImage = new WritableImage(1, 1);
		writableImage.getPixelWriter().setColor(0, 0, Color.rgb(50, 30, 20));
		WritableImage writableImage2 = new WritableImage(1, 1);
		writableImage2.getPixelWriter().setColor(0, 0, Color.rgb(50, 30, 20));
		Image imageResult = creationImageService.plusImages(writableImage, writableImage2);
		Color firstPositionColor = imageResult.getPixelReader().getColor(0, 0);
		Assert.assertEquals(100, (int) (firstPositionColor.getRed() * LIMIT_SCALE));
		Assert.assertEquals(60, (int) (firstPositionColor.getGreen() * LIMIT_SCALE));
		Assert.assertEquals(40, (int) (firstPositionColor.getBlue() * LIMIT_SCALE));
	}

	@Test
	public void whenPlusImagesAndOneColorValueIsHigherTo255ThenApplyTransformation() {
		WritableImage writableImage = new WritableImage(1, 1);
		writableImage.getPixelWriter().setColor(0, 0, Color.rgb(50, 0, 0));
		WritableImage writableImage2 = new WritableImage(1, 1);
		writableImage2.getPixelWriter().setColor(0, 0, Color.rgb(206, 0, 0));
		Image imageResult = creationImageService.plusImages(writableImage, writableImage2);
		Color firstPositionColor = imageResult.getPixelReader().getColor(0, 0);
		Assert.assertEquals(128, (int) (firstPositionColor.getRed() * LIMIT_SCALE));
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
		Image imageResult = creationImageService.plusImages(writableImage, writableImage2);
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
		Image imageResult = creationImageService.plusImages(writableImage, writableImage2);
		Assert.assertEquals(2, (int) imageResult.getWidth());
		Assert.assertEquals(1, (int) imageResult.getHeight());
		Color absentPositionFromFirstImage = imageResult.getPixelReader().getColor(1, 0);
		Assert.assertEquals(130, (int) (absentPositionFromFirstImage.getRed() * LIMIT_SCALE));
		Assert.assertEquals(0, (int) (absentPositionFromFirstImage.getBlue() * LIMIT_SCALE));
		Assert.assertEquals(0, (int) (absentPositionFromFirstImage.getGreen() * LIMIT_SCALE));
	}

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
	}

	private void assertRGB(int red, int green, int blue, Color color) {
		Assert.assertEquals( red,(int)(color.getRed()*255));
		Assert.assertEquals( green,(int)(color.getGreen()*255));
		Assert.assertEquals( blue,(int)(color.getBlue()*255));
	}

	private void assertBlackContorn(PixelReader pixelReader) {
		Assert.assertEquals(BLACK, pixelReader.getColor(0, 0));
		Assert.assertEquals(BLACK, pixelReader.getColor(0, 1));
		Assert.assertEquals(BLACK, pixelReader.getColor(0, 2));
		Assert.assertEquals(BLACK, pixelReader.getColor(0, 3));
		Assert.assertEquals(BLACK, pixelReader.getColor(1, 0));
		Assert.assertEquals(BLACK, pixelReader.getColor(1, 3));
		Assert.assertEquals(BLACK, pixelReader.getColor(2, 0));
		Assert.assertEquals(BLACK, pixelReader.getColor(2, 3));
		Assert.assertEquals(BLACK, pixelReader.getColor(3, 0));
		Assert.assertEquals(BLACK, pixelReader.getColor(3, 1));
		Assert.assertEquals(BLACK, pixelReader.getColor(3, 2));
		Assert.assertEquals(BLACK, pixelReader.getColor(3, 3));
	}

	private void assertWhiteQuadrate(PixelReader pixelReader) {
		Assert.assertEquals(WHITE, pixelReader.getColor(1, 1));
		Assert.assertEquals(WHITE, pixelReader.getColor(1, 2));
		Assert.assertEquals(WHITE, pixelReader.getColor(2, 1));
		Assert.assertEquals(WHITE, pixelReader.getColor(2, 2));
	}
}