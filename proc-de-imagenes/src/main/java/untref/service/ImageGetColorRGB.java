package untref.service;

import javafx.scene.paint.Color;

public interface ImageGetColorRGB {

	Color getAverageChannelsRGB();

	Double getAverageGrey();

	Double getTotalValueChannelR();

	Double getTotalValueChannelG();

	Double getTotalValueChannelB();

	Double getValueChannelR(int x, int y);

	Double getValueChannelG(int x, int y);

	Double getValueChannelB(int x, int y);

	int getTotalPixel();

	int getGrayAverage(int x, int y);

}
