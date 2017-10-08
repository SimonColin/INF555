/**
 * An interface defining primivites and operations on vectors in euclidean space
 *
 * @author Luca Castelli Aleardi (INF555, 2012)
 */
public interface Vector_ {
	
	/**
	 * return the i-th cartesian coordinate of the point
	 */
	  public double getCartesian(int i);  

		/**
		 * set the i-th cartesian coordinate
		 */
	  public void setCartesian(int i, double x);
	      
		/**
		 * Return an array (float numbers) containing the coordinates of the point
		 */	  
	  public float[] toFloat();

		/**
		 * Return an array containing the coordinates of the point
		 */	  
	  public double[] toDouble();

		/**
		 * Return the vector sum (v+u), where u is the current vector
		 */
	  public Vector_ sum(Vector_ v);

	  	/**
		 * Return the vector difference (v-u), where u is the current vector
		 */
	  public Vector_ difference(Vector_ v);

	  	/**
		 * Return the opposite of the current vector
		 */
	  public Vector_ opposite();

	  	/**
		 * Return the inner product of two vectors
		 */
	  public double innerProduct(Vector_ v);

	  	/**
		 * Return a new vector obtained by scalar division
		 */
	  public Vector_ divisionByScalar(double s);

	  	/**
		 * Return a new vector obtained by multiplying by a scalar
		 */
	  public Vector_ multiplyByScalar(double s);
	  
	  	/**
		 * Return the square length of a vector
		 */
	  public double squaredLength();
	   
	  	/**
		 * Return the dimension of the space
		 */
	  public int dimension();

	  	/**
		 * Return a String representing vector coordinates
		 */
	  public String toString();
}
