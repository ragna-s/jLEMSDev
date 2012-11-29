package org.lemsml.jlems.type.structure;

import java.util.ArrayList;

import org.lemsml.jlems.expression.ParseError;
import org.lemsml.jlems.run.ApplyBuilder;
import org.lemsml.jlems.run.BuilderElement;
import org.lemsml.jlems.run.StateType;
import org.lemsml.jlems.sim.ContentError;
import org.lemsml.jlems.type.Children;
import org.lemsml.jlems.type.Component;
import org.lemsml.jlems.type.ComponentType;
import org.lemsml.jlems.type.Lems;

public class Apply extends BuildElement {

	public String components;
 
	public Children r_children;
 	
	
	public BuilderElement makeBuilder(Component cpt) throws ContentError, ParseError {
		ArrayList<StateType> acb = new ArrayList<StateType>();
		
		ArrayList<Component> cs = cpt.getChildrenAL(components);
		for (Component c : cs) {
			acb.add(c.getStateType());
		}
		
		ApplyBuilder ret = new ApplyBuilder(components, acb);
		super.makeChildBuilders(cpt, ret);
		return ret;
	}		

	
	@Override
	public void resolveLocal(Lems lems, ComponentType ct) throws ContentError, ParseError {
		r_children = ct.getChildrenByName(components);
		if (r_children == null) {
			throw new ContentError("Children " + components + " not found in " + ct);
		} 
		
		
	
	}
	
	
}
