package untref.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreationImageServiceTest {

	private CreationImageService creationImageService;

	@Before
	public void setUp() {
		creationImageService = new CreationImageServiceImpl();
	}

	@Test
	public void whenCreateBinaryImageWithCenterQuadrateThenCreateIt() {
		int black = -0xFFFFFF;
		int white = 0;
		Image binaryImageWithCenterQuadrate = creationImageService.createBinaryImageWithCenterQuadrate(4, 4);
		PixelReader pixelReader = binaryImageWithCenterQuadrate.getPixelReader();
		assertBlackQuadrate(black, pixelReader);
		assertWhiteContorn(white, pixelReader);
	}

	private void assertWhiteContorn(int white, PixelReader pixelReader) {
		Assert.assertEquals(white, pixelReader.getArgb(0, 1));
		Assert.assertEquals(white, pixelReader.getArgb(0, 2));
		Assert.assertEquals(white, pixelReader.getArgb(0, 3));
		Assert.assertEquals(white, pixelReader.getArgb(1, 0));
		Assert.assertEquals(white, pixelReader.getArgb(1, 3));
		Assert.assertEquals(white, pixelReader.getArgb(2, 0));
		Assert.assertEquals(white, pixelReader.getArgb(2, 3));
		Assert.assertEquals(white, pixelReader.getArgb(3, 0));
		Assert.assertEquals(white, pixelReader.getArgb(3, 1));
		Assert.assertEquals(white, pixelReader.getArgb(3, 2));
		Assert.assertEquals(white, pixelReader.getArgb(3, 3));
	}

	private void assertBlackQuadrate(int black, PixelReader pixelReader) {
		Assert.assertEquals(black, pixelReader.getArgb(1, 1));
		Assert.assertEquals(black, pixelReader.getArgb(1, 2));
		Assert.assertEquals(black, pixelReader.getArgb(2, 1));
		Assert.assertEquals(black, pixelReader.getArgb(2, 2));
	}
}