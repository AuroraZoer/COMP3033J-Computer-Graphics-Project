package GraphicsObjects;

public class Point4f {

	public float x;
	public float y;
	public float z;
	public float a;


	// default constructor
	public Point4f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		a = 0.0f;
	}

	//initializing constructor
	public Point4f(float x, float y, float z, float a) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}

	// sometimes for different algorithms we will need to address the point using positions 0 1 2
	public float getPostion(int postion) {
		switch (postion) {
			case 0:
				return x;
			case 1:
				return y;
			case 2:
				return z;
			case 3:
				return a;
			default:
				return Float.NaN;
		}
	}

	public String toString() {
		return ("(" + x + "," + y + "," + z + "," + a + ")");
	}

	//implement Point plus a Vector

	/**
	 * Adds a Vector4f to this Point4f.
	 *
	 * @param Additonal The Vector4f to add.
	 * @return A new Point4f representing the result of the addition.
	 */
	public Point4f PlusVector(Vector4f Additonal) {
		float n_x = x + Additonal.x;
		float n_y = y + Additonal.y;
		float n_z = z + Additonal.z;
		float n_a = a + Additonal.a;

		return new Point4f(n_x, n_y, n_z, n_a);
	}

	//implement Point minus a Vector

	/**
	 * Subtracts a Vector4f from this Point4f.
	 *
	 * @param Minus The Vector4f to subtract.
	 * @return A new Point4f representing the result of the subtraction.
	 */
	public Point4f MinusVector(Vector4f Minus) {
		float n_x = x - Minus.x;
		float n_y = y - Minus.y;
		float n_z = z - Minus.z;
		float n_a = a - Minus.a;

		return new Point4f(n_x, n_y, n_z, n_a);
	}


	/// Point - Point

	/**
	 * Subtracts a Point4f from this Point4f.
	 *
	 * @param Minus The Point4f to subtract.
	 * @return A new Vector4f representing the result of the subtraction.
	 */
	public Vector4f MinusPoint(Point4f Minus) {
		float n_x = x - Minus.x;
		float n_y = y - Minus.y;
		float n_z = z - Minus.z;
		float n_a = a - Minus.a;

		return new Vector4f(n_x, n_y, n_z, n_a);
	}


	// Remember point + point  is not defined so we not write a method for it.


}

/*................................................................................
................................................................................
................................................................................
................................................................................
................................................................................
................................................................................
................................................................................
................................................................................
................................................................................
....................................=?7777+.....................................
.............................,8MMMMMMMMMMMMMMMMM7...............................
...........................$MMMMMMMMMMMMMMMMMMMMMM7.............................
........................IMMMMMMMMMMMMMMMMMMMMMMMMMMMM?..........................
......................?MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN........................
.....................MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM$......................
...................ZMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM.....................
..................MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM8....................
.................NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM...................
................IMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM..................
................MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM$.................
...............=MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMZ................
..............:MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM................
..............7MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM:...............
..............DMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMZ...............
..............MMMMMMMMMMMMMMMMMMMMMMMM MMMMMMMMMMMMMMMMMMMMMMMMM8...............
..............MMMMMMMMMMMMMMMMMMMMMMM  MMMMMMMMMMMMMMMMMMMMMMMMMN...............
..............NMMMMMMMMMMMMMMMMMMMM MM MMMMMMMMMMMMMMMMMMMMMMMMMO...............
..............$MMMMMMMMMMMMMMMMMM MMMM MMMMMMMMMMMMMMMMMMMMMMMMMI...............
..............+MMMMMMMMMMMMMMMMM          MMMMMMMMMMMMMMMMMMMMMM=...............
...............8MMMMMMMMMMMMMMMMMMMMMM MMMMMMMMMMMMMMMMMMMMMMMMM................
................MMMMMMMMMMMMMMMMMMMMMM MMMMMMMMMMMMMMMMMMMMMMMM8................
................MMMMMMMMMMMMMMMMMMMMMM MMMMMMMMMMMMMMMMMMMMMMMN,................
................=MMMMMMMMMMMMMMMMMMMMM MMMMMMMMMMMMMMMMMMMMMMM..................
.................MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMZ..................
..................MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMD...................
...................?MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM.....................
....................8MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM......................
.....................,8MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM,.......................
........................NMMMMMMMMMMMMMMMMMMMMMMMMMMMMN?.........................
..........................NMMMMMMMMMMMMMMMMMMMMMMMMMI...........................
.............................$MMMMMMMMMMMMMMMMMMM?..............................
.................................,I$NMMMMMN$?...................................
................................................................................
................................................................................
................................................................................
.......................................................................*/
