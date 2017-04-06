package untref.controllers;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class DrawRectangleSelection {

	//final DragContext dragContext = new DragContext();
	Point pointInitial, pointEnd;
	Rectangle rect = new Rectangle();
	int click;

	Group group;

	public DrawRectangleSelection(Group group) {
		pointInitial = new Point();
		pointEnd = new Point();
		this.group = group;
		this.click = 0;

		rect = new Rectangle(0, 0, 0, 0);
		rect.setStroke(Color.BLUE);
		rect.setStrokeWidth(1);
		rect.setStrokeLineCap(StrokeLineCap.ROUND);
		rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

		group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);

		group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

	}

	public Bounds getBounds() {
		return rect.getBoundsInParent();
	}

	private void drawingRectangle(Point pointInitial, Point pointEnd) {

	 	if (pointInitial.isMenor(pointEnd))
		 drawRectangle(pointInitial, pointEnd);
	 	else
			
	drawRectangle(pointEnd, pointInitial);
	}

	private void drawRectangle(Point pointInitial, Point pointEnd) {

		rect.setX(pointInitial.getX());
		rect.setY(pointInitial.getY());
		rect.setWidth(pointEnd.getX() - pointInitial.getX());
		rect.setHeight(pointEnd.getY() - pointInitial.getY());
		group.getChildren().add(rect);

	}

	EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent event) {

			if (event.isSecondaryButtonDown())
				return;

			/*
			 * rect.setX(0); rect.setY(0); rect.setWidth(0); rect.setHeight(0);
			 */

			group.getChildren().remove(rect);
			if (click == 0) {
				pointInitial.setPoint(event.getX(), event.getY());
				click++;
			} else {
				pointEnd.setPoint(event.getX(), event.getY());
				click = 0;
				drawingRectangle(pointInitial, pointEnd);

			}

		}
	};

	EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent event) {

			if (event.isSecondaryButtonDown())
				return;

			/*
			 * rect.setX(0); rect.setY(0); rect.setWidth(0); rect.setHeight(0);
			 * 
			 * group.getChildren().remove( rect);
			 * 
			 * /
			 */
		}
	};

	
}