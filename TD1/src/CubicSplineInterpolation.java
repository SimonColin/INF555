import java.util.Arrays;

import Jama.*;

/**
 * Cubic spline interpolation
 * 
 * @author Luca Castelli Aleardi (INF555, 2014)
 *
 */
public class CubicSplineInterpolation extends Interpolation {
	
	double[][] coeffs;
	double s0 = 0;
	double sf = 0;
	
	//throw new Error("To be completed: TD1");	

	public CubicSplineInterpolation(Draw frame) {
		super(frame);
	}
	
	public void computeCoefficients(Point_2[] P) {
	//	Arrays.sort(P);
		coeffs = new double[P.length - 1][4];
		Matrix W = new Matrix(4 * (P.length - 1), 4 * (P.length - 1));
		Matrix Y = new Matrix(4 * (P.length - 1), 1);
		if(P.length > 2)
		{
			W.set(0, 1, 1);
			W.set(0, 2, 2 * P[0].x);
			W.set(0, 3, 3 * P[0].x * P[0].x);
			Y.set(0, 0, s0);
			Y.set(1, 0, P[0].y);
			Y.set(2, 0, P[1].y);
			Y.set(3, 0, 0);
			for(int j = 0; j < 2; j++)
				for(int i = 0; i < 4; i++)
					W.set(1 + j, i, Math.pow(P[j].x, i));
			for(int i = 0; i < 3; i++)
			{
				W.set(3, 1 + i, (i + 1) * Math.pow(P[1].x, i));
				W.set(3, 5 + i, -(i + 1) * Math.pow(P[1].x, i));
			}
			for(int i = 4; i < 4 * (P.length - 2); i += 4)
			{
				Y.set(i, 0, 0);
				Y.set(i + 1, 0, P[i / 4].y);
				Y.set(i + 2, 0, P[i / 4 + 1].y);
				Y.set(i + 3, 0, 0);
				W.set(i, i - 2, 2);
				W.set(i, i - 1, 6 * P[i / 4].x);
				W.set(i, i + 2, -2);
				W.set(i, i + 3, -6 * P[i / 4].x);
				for(int j = 0; j < 2; j++)
					for(int k = 0; k < 4; k++)
						W.set(i + 1 + j, i + k, Math.pow(P[i / 4 + j].x, k));
				for(int j = 0; j < 3; j++)
				{
					W.set(i + 3, i + 1 + j, (j + 1) * Math.pow(P[i / 4 + 1].x, j));
					W.set(i + 3, i + 5 + j, -(j + 1) * Math.pow(P[i / 4 + 1].x, j));
				}
			}
			int n = 4 * (P.length - 2);
			Y.set(n, 0, 0);
			Y.set(n + 1, 0, P[P.length - 2].y);
			Y.set(n + 2, 0, P[P.length - 1].y);
			Y.set(n + 3, 0, sf);
			W.set(n, n - 2, 2);
			W.set(n, n - 1, 6 * P[P.length - 2].x);
			W.set(n, n + 2, -2);
			W.set(n, n + 3, -6 * P[P.length - 2].x);
			for(int j = 0; j < 2; j++)
				for(int k = 0; k < 4; k++)
					W.set(n + 1 + j, n + k, Math.pow(P[P.length - 2 + j].x, k));
			for(int j = 0; j < 3; j++)
			{
				W.set(n + 3, n + 1 + j, (j + 1) * Math.pow(P[P.length - 1].x, j));
			}
		}
		else
		{
			W.set(0, 1, 1);
			W.set(0, 2, 2 * P[0].x);
			W.set(0, 3, 3 * P[0].x * P[0].x);
			Y.set(0, 0, s0);
			Y.set(1, 0, P[0].y);
			Y.set(2, 0, P[1].y);
			Y.set(3, 0, 0);
			for(int j = 0; j < 2; j++)
				for(int i = 0; i < 4; i++)
					W.set(1 + j, i, Math.pow(P[j].x, i));
			for(int i = 0; i < 3; i++)
			{
				W.set(3, 1 + i, (i + 1) * Math.pow(P[1].x, i));
			}
		}
	/*	double[] tmp = new double[4 * P.length - 4];
		res = W.getMatrix(0, 4, 0, 4).solve(Y.getMatrix(0, 4, 0, 0)).getArray();
		for(int j = 0; j < 5; j++)
			 tmp[j] = res[j][0];
		for(int i = 0; i < P.length - 3; i++)
		{
			Matrix subW = W.getMatrix(5 + i * 4, 8 + i * 4, 4 + i * 4, 7 + i * 4);
			Matrix subY = Y.getMatrix(0 + i * 4, 3 + i * 4, 0, 0);
			 res = subW.solve(subY).getArray();
			 for(int j = 0; j < 4; j++)
				 tmp[5 + i * 4 + j] = res[j][0];
		} */
		double[][] res = W.solve(Y).getArray();
		for(int i = 0; i < P.length - 1; i++)
		{
			coeffs[i][0] = res[4 * i][0];
			coeffs[i][1] = res[4 * i + 1][0];
			coeffs[i][2] = res[4 * i + 2][0];
			coeffs[i][3] = res[4 * i + 3][0];
		} 
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
		//throw new Error("To be completed: TD1");
		Arrays.sort(this.points);
		computeCoefficients(this.points);
		double[] array = new double[4];
		for(int i = 0; i < this.points.length - 1; i++)
		{
			drawPoint(this.points[i]);
			for(int j = 0; j < 4; j++)
				array[j] = coeffs[i][j];
			plotPolynomial(array, this.points[i].x, this.points[i + 1].x, 20);
			
		}
		
		drawPoint(this.points[this.points.length - 1]);
		
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
