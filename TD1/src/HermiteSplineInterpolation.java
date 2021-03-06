import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import Jama.Matrix;

public class HermiteSplineInterpolation extends Interpolation{
	
	LinkedList<Vector_2> slopeList;
	double[][] coeffsX;
	double[][] coeffsY;

	public HermiteSplineInterpolation(Draw frame) {
		super(frame);
		this.slopeList = new LinkedList<Vector_2>();
		slopeList.add(new Vector_2(100,100));
		slopeList.add(new Vector_2(-100,0));
		slopeList.add(new Vector_2(0,100));
		slopeList.add(new Vector_2(-100,100));
		slopeList.add(new Vector_2(300,100));
	}
	
	public Vector_2[] linkedListArray() {
		Vector_2[] slopeListArray=new Vector_2[slopeList.size()];
		int i=0;
		for(Vector_2 v: slopeList) {
			slopeListArray[i]=v;
			i++;
		}		
		return slopeListArray;
	}

	@Override
	public void interpolate() {
		int pointsSize = this.points.length;
		int slopesSize = this.slopeList.size();
		
	//	Arrays.sort(this.points);
		
		for(int i=0;i<pointsSize;i++)
	    	drawPoint(this.points[i]);
		
		for(int i=0; i<(pointsSize-slopesSize); i++){
			Random randomGenerator = new Random();
			slopeList.add(new Vector_2(100*randomGenerator.nextInt(10)-500,100*randomGenerator.nextInt(10)-500));
		}
		plotHermite(points, this.linkedListArray());
		
		for(int i = 0; i < this.points.length - 1; i++)
			plotPolynomialCurve(this.coeffsX[i], this.coeffsY[i], 10);
		
	}
	
	
	public void plotHermite(Point_2[] p, Vector_2[] sL){
		
	double[][] array = {{1, 0, 0, 0}, {1, 1, 1, 1}, {0, 1, 0, 0}, {0, 1, 2, 3}};
	this.coeffsX = new double[p.length - 1][4];
	this.coeffsY = new double[p.length - 1][4];
	Matrix W = new Matrix(array);
	for(int i = 0; i < p.length - 1; i++)
	{
		double[][] ax = {{p[i].x}, {p[i + 1].x}, {sL[i].x}, {sL[i + 1].x}};
		Matrix X = new Matrix(ax);
		double[][] ay = {{p[i].y}, {p[i + 1].y}, {sL[i].y}, {sL[i + 1].y}};
		Matrix Y = new Matrix(ay);
		double[][] tmp = W.solve(X).getArray();
		for(int j = 0; j < 4; j++)
			coeffsX[i][j] = tmp[j][0];
		tmp = W.solve(Y).getArray();
		for(int j = 0; j < 4; j++)
			coeffsY[i][j] = tmp[j][0];
	}
		//throw new Error("To be completed: TD1");
	}
	
	public Vector_2 getSlope(int pointIndex){
		return linkedListArray()[pointIndex];
	}
	
	void plotPolynomialCurve(double[] coeffX, double[] coeffY, int n) {
		Point_2[] plotPoints = new Point_2[n+1];
		double space = (double)1./n;
		
		for(int i=0;i<=n;i++){
			double t = (double)i*space;
			double xValue = 0;
			for(int l=0;l<coeffX.length;l++)
				xValue+=coeffX[l]*Math.pow(t,l);

			double yValue = 0;
			for(int l=0;l<coeffY.length;l++)
				yValue+=coeffY[l]*Math.pow(t,l);
			
			plotPoints[i]=new Point_2(xValue,yValue);
		}
			
		for(int i=0;i<n;i++)
			drawSegment(plotPoints[i], plotPoints[i+1]);
	}
	
	
	
}
