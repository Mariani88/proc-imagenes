package untref.service;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImageEditionServiceTest {

	private ImageEditionService imageEditionService;

	@Before
	public void setUp()  {
		imageEditionService = new ImageEditionServiceImpl();
	}

	@Test
	public void whenChangePixelValueThenChangeIt() throws IOException, URISyntaxException {
		File file = new File("src/test/resources/test-image.png");
		BufferedImage bufferedImage = ImageIO.read(file);
		Image image = SwingFXUtils.toFXImage(bufferedImage, null);
		String zero = Integer.toString(0);
		Integer intZero = Integer.valueOf(zero);
		Image modifiedImage = imageEditionService.modifyPixelValue(image, zero, zero, zero);
		Assert.assertEquals(intZero.intValue(), modifiedImage.getPixelReader().getArgb(intZero,intZero));
	}
}
