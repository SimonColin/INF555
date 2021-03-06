import Jama.*;
import java.lang.Math;
import java.util.Arrays;

/**
 * Lagrange interpolation
 * 
 * @author Luca Castelli Aleardi (INF555, 2012)
 *
 */
public class LagrangeInterpolation extends Interpolation {
	
	double[] coeff=null;
	
	public LagrangeInterpolation(Draw frame) {
		super(frame);
	}
	
	public static double[] computeCoefficients(Point_2[] P) {
		Arrays.sort(P);
		double[] solution=new double[P.length];
		Matrix W = new Matrix(P.length, P.length); // linear system to solve
		Matrix Y = new Matrix(P.length, 1); // y coordinates of points
		for(int i = 0; i < P.length; i++)
		{
			for(int j = 0; j < P.length; j++)
			{
				W.set(i, j, Math.pow(P[i].getX(), j));
			}
			Y.set(i, 0, P[i].getY());
		}
		double[][] res = W.solve(Y).getArray();
		for(int i = 0; i < res.length; i++)
			solution[i] = res[i][0];
		return solution;
		//throw new Error("To be completed: TD1");
	}
	
	/**
	 * Evaluate polynomial a[0]+a[1]x+a[2]x^2+...+a[n]x^n, at point x
	 */
	public static double evaluate(double[] a, double x) {
		if(a==null || a.length==0) throw new Error("polynomial not defined");
		double result=a[0];
		double p=1.;
		for(int i=1;i<a.length;i++) {
			p=p*x;
			//System.out.println(""+a[i]+"*"+p+"="+(a[i]*p));
			result=result+(a[i]*p);
		}
		return result;
	}

	public void interpolate() {		
		if(this.points.length>2)  {
			this.coeff=computeCoefficients(this.points);
			plotPolynomial(this.coeff, this.points[0].getX(), this.points[this.points.length-1].getX(), 300);
			//System.out.println("Interpolating polynomial: "+polynomialToString(this.coeff));
		}
		for(Point_2 q:this.points) {
	    	drawPoint(q); // draw input points
	    }
	}
	
	public void plotPolynomial(double[] a, double min, double max, int n) {
		double dx=(max-min)/n;
		
		double x=min;
		for(int i=0;i<n;i++) {
			Point_2 p=new Point_2(x, evaluate(a, x));
			Point_2 q=new Point_2(x+dx, evaluate(a, x+dx));
			this.drawSegment(p, q);
			x=x+dx;
		}
		
	}
	
}
 
