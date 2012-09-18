package org.lemsml.type.dynamics;

import org.lemsml.expression.Dimensional;
import org.lemsml.expression.Valued;
import org.lemsml.type.Dimension;
import org.lemsml.util.E;

public class ExposedVar implements Valued {

	double val = Double.NaN;
	
	String name;
	public Dimension r_dimension;
	
	public ExposedVar(String s, Dimension d) {
		name = s;
		r_dimension = d;
	}
	
	
	
	public double getValue() {
		return val;
	}

 
	public boolean isFixed() {
		return false;
	}

	 
	public String getName() {
		return name;
	}



	public Dimensional getDimensionality() {
		if (r_dimension == null) {
			E.error("null dimension in required var " + name);
		}
		return r_dimension;
	}

}