package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class CreationImageServiceTest {

	private CreationImageService creationImageService;

	@Before
	public void setUp() {
		creationImageService = new CreationImageServiceImpl();
	}

	@Test
	public void whenCreateBinaryImageWithCenterQuadrateThenCreateIt() {
		Image binaryImageWithCenterQuadrate = creationImageService.createBinaryImageWithCenterQuadrate(4, 4);
		PixelReader pixelReader = binaryImageWithCenterQuadrate.getPixelReader();
		assertBlackQuadrate(pixelReader);
		assertWhiteContorn(pixelReader);
	}

	private void assertWhiteContorn(PixelReader pixelReader) {
		Assert.assertEquals(WHITE, pixelReader.getColor(0, 0));
		Assert.assertEquals(WHITE, pixelReader.getColor(0, 1));
		Assert.assertEquals(WHITE, pixelReader.getColor(0, 2));
		Assert.assertEquals(WHITE, pixelReader.getColor(0, 3));
		Assert.assertEquals(WHITE, pixelReader.getColor(1, 0));
		Assert.assertEquals(WHITE, pixelReader.getColor(1, 3));
		Assert.assertEquals(WHITE, pixelReader.getColor(2, 0));
		Assert.assertEquals(WHITE, pixelReader.getColor(2, 3));
		Assert.assertEquals(WHITE, pixelReader.getColor(3, 0));
		Assert.assertEquals(WHITE, pixelReader.getColor(3, 1));
		Assert.assertEquals(WHITE, pixelReader.getColor(3, 2));
		Assert.assertEquals(WHITE, pixelReader.getColor(3, 3));
	}

	private void assertBlackQuadrate(PixelReader pixelReader) {
		Assert.assertEquals(BLACK, pixelReader.getColor(1, 1));
		Assert.assertEquals(BLACK, pixelReader.getColor(1, 2));
		Assert.assertEquals(BLACK, pixelReader.getColor(2, 1));
		Assert.assertEquals(BLACK, pixelReader.getColor(2, 2));
	}
}