package untref;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import untref.controllers.PrincipalGraphicInterfaceController;




public class PrincipalGraphicInterface extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		AnchorPane principalPane = new PrincipalGraphicInterfaceController().initInterfaceElements();
		Scene scene = new Scene(principalPane, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}




























/*
/*public class PrincipalGraphicInterface extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		AnchorPane principalPane = new PrincipalGraphicInterfaceController().initInterfaceElements();
		Scene scene = new Scene(principalPane, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}*/
/*
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrincipalGraphicInterface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BarChart Experiments");

        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("GrayScale");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pixel Quantity");

        BarChart     barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data("Desktop", 567));
        dataSeries1.getData().add(new XYChart.Data("Phone"  , 65));
        dataSeries1.getData().add(new XYChart.Data("Tablet"  , 23));

        barChart.getData().add(dataSeries1);

        VBox vbox = new VBox(barChart);

        Scene scene = new Scene(vbox, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}*/