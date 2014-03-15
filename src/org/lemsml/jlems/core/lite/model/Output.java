package org.lemsml.jlems.core.lite.model;

public class Output {

	public String variable;
	
	public String value;
	
	
	public Output(String var) {
		variable = var;
	}

	 


	public Output() {
		// called from generated factory
	}




	public void setExpression(String v) {
		value = v;
	}



	
	public String getLocalName() {
		// TODO - only works if value is a simple var name
		return value;
	}
	
	public String getExposedName() {
		return variable;
	}
	
}




