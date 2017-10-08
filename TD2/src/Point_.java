/**
 * An interface defining primivites and operations on points in euclidean space
 *
 * @author Luca Castelli Aleardi (INF555, 2012)
 */
public interface Point_ extends Comparable<Point_>{

	/**
	 * return the i-th cartesian coordinate of the point
	 */
	  public double getCartesian(int i);  

	/**
	 * set the i-th cartesian coordinate
	 */
	  public void setCartesian(int i, double x);

		/**
		 * set the coordinates of the origin
		 */	  
	  public void setOrigin();
	  
		/**
		 * Return an array (float numbers) containing the coordinates of the point
		 */	  
	  public float[] toFloat();

		/**
		 * Return an array containing the coordinates of the point
		 */	  
	  public double[] toDouble();
	    
		/**
		 * Translate the current point according to a vector v
		 */	  
	  public void translateOf(Vector_ v);

		/**
		 * Return the vector (p-q), where q is the current point
		 */	  
	  public Vector_ minus(Point_ p);

		/**
		 * Set the current point as the barycenter of an array of points
		 */	  
	  public void barycenter(Point_ [] points);
	  //public void linearCombination(Point_[] points, double[] coefficients);
	  
		/**
		 * Return the dimension of the space
		 */	  
	  public int dimension();

	  /**
		 * Return a String representing point coordinates
		 */	  
	  public String toString();
	  
}


