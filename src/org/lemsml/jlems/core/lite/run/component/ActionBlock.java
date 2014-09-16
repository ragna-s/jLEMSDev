package org.lemsml.jlems.core.lite.run.component;

import java.util.ArrayList;

public class ActionBlock {

	
	ArrayList<Assignment> assignments = new ArrayList<Assignment>();
	
	ArrayList<ConditionBlock> conditionBlocks = new ArrayList<ConditionBlock>();
	
	ArrayList<EmitAction> emitActions = new ArrayList<EmitAction>();
	
	public void addAssignment(Assignment as) {
		 assignments.add(as);
	}



	public void addEmitAction(EmitAction ea) {
		emitActions.add(ea);
	}



	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}

	public ArrayList<EmitAction> getEmitActions() {
		return emitActions;
	}


	public void addConditionBlock(ConditionBlock cb) {
		conditionBlocks.add(cb);
	}



	public ArrayList<ConditionBlock> getConditionBlocks() {
		 return conditionBlocks;
	}
	
	
}
