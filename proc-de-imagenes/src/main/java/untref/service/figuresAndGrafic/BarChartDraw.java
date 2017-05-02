package untref.service.figuresAndGrafic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BarChartDraw {

	CategoryAxis xAxis;
	NumberAxis yAxis;
	BarChart barChart;
	XYChart.Series dataSeries;

	public BarChartDraw(String nameAxisX, String nameAxisY) {
		 
		this.xAxis = new CategoryAxis();
		this.yAxis = new NumberAxis();
		setNameAxis(nameAxisX, nameAxisY);
		this.barChart = new BarChart(this.xAxis, this.yAxis);
		this.dataSeries = new XYChart.Series();
	}

	public void setData(String valueNameScale, double valueScale) {
		this.dataSeries.getData().add(new XYChart.Data(valueNameScale, valueScale));

	}

	public void setNameAxis(String nameAxisX, String nameAxisY) {
		xAxis.setLabel(nameAxisX);
		yAxis.setLabel(nameAxisY);

	}

	public BarChart getBarChar() {

		barChart.getData().add(dataSeries);
		return barChart;
	}

	public void drawBarChart() {
		VBox vbox = new VBox(this.getBarChar());
		Scene scene = new Scene(vbox, 400, 200);

		Stage secondaryStage = new Stage();
		secondaryStage.setTitle("Histogram");
		secondaryStage.setScene(scene);
		secondaryStage.setHeight(300);
		secondaryStage.setWidth(1200);

		secondaryStage.show();
	}
}
