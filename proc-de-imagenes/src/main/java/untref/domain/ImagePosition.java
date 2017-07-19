package untref.domain;

public class ImagePosition implements Cloneable,Comparable<ImagePosition> {

	private int row;
	private int column;

	public ImagePosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ImagePosition that = (ImagePosition) o;

		if (row != that.row)
			return false;
		return column == that.column;
	}

	@Override
	public int hashCode() {
		int result = row;
		result = 31 * result + column;
		return result;
	}

	@Override
	public int compareTo(ImagePosition imagePosition) {

		if (this.equals(imagePosition)) {
			return 0;
		}

		if (this.column < imagePosition.getColumn() && this.row < imagePosition.getRow()) {
			return -1;
		}

		return 1;
	}

	@Override
	public Object clone(){
		try {
			super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return new ImagePosition(this.row, this.column);
	}
}