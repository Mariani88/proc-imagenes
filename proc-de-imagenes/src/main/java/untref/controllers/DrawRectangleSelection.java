package untref.controllers;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class DrawRectangleSelection {

	final DragContext dragContext = new DragContext();
	Point pointInitial, pointEnd;
	Rectangle rect = new Rectangle();
	int click;

	Group group;

	public DrawRectangleSelection(Group group) {
		pointInitial=new Point();
		pointEnd=new Point();
		this.group = group;
		this.click=0;

		rect = new Rectangle(0, 0, 0, 0);
		rect.setStroke(Color.BLUE);
		rect.setStrokeWidth(1);
		rect.setStrokeLineCap(StrokeLineCap.ROUND);
		 rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

		group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
		group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
		group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

	}

	public Bounds getBounds() {
		return rect.getBoundsInParent();
	}

	EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent event) {

			if (event.isSecondaryButtonDown())
				return;

			/*
			 * rect.setX(0); rect.setY(0); rect.setWidth(0); rect.setHeight(0);
			 */

			group.getChildren().remove(rect);
			if (click == 0)
			pointInitial.setPoint(event.getX(), event.getY());
			else{
				pointEnd.setPoint(event.getX(), event.getY());
				rect.setX(pointInitial.getX());
				rect.setY(pointInitial.getY());
				rect.setWidth(pointEnd.getX()-pointInitial.getX());
				rect.setHeight(pointEnd.getY()-pointInitial.getY());
				group.getChildren().add(rect);
			}
			click++;
		/*	//dragContext.mouseAnchorX = event.getX();
			dragContext.mouseAnchorY = event.getY();

			rect.setX(dragContext.mouseAnchorX);
			rect.setY(dragContext.mouseAnchorY);
			rect.setWidth(0);
			rect.setHeight(0);
*/
			

		}
	};

	EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent event) {

			if (event.isSecondaryButtonDown())
				return;

		/*	double offsetX = event.getX() - dragContext.mouseAnchorX;
			double offsetY = event.getY() - dragContext.mouseAnchorY;

			if (offsetX > 0)
				rect.setWidth(offsetX);
			else {
				rect.setX(event.getX());
				rect.setWidth(dragContext.mouseAnchorX - rect.getX());
			}

			if (offsetY > 0) {
				rect.setHeight(offsetY);
			} else {
				rect.setY(event.getY());
				rect.setHeight(dragContext.mouseAnchorY - rect.getY());
			}*/
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

	private static final class DragContext {

		public double mouseAnchorX;
		public double mouseAnchorY;

	}
}