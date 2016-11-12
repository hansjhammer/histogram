import java.awt.Color;
import java.util.Comparator;

public class Tuple implements Comparable<Tuple> {

	double x;
	double y;
	double value;
	Color c;
	
	public Tuple(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public void setColor(Color c){
		this.c=c;
	}
	public Color getColor(){
		return c;
	}
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public int compareTo(Tuple tuple) {

		double compareQuantity = tuple.getValue();

		// ascending order
		if (this.value-compareQuantity==0){
			return 0;
		}
		if (this.value-compareQuantity<0){
			return -1;
		}
		else{
			return 1;
		}

		// descending order
		// return compareQuantity - this.quantity;

	}

	public static Comparator<Tuple> TupleComparator
	= new Comparator<Tuple>() {

		public int compare(Tuple tuple1, Tuple tuple2) {

			Double value1  = tuple1.getValue();
			Double value2 = tuple2.getValue();

			//ascending order
			return value1.compareTo(value2);

		}
	};
}
