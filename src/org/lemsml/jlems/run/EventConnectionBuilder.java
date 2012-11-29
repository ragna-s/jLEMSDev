package org.lemsml.jlems.run;

import java.util.ArrayList;
import java.util.HashMap;

import org.lemsml.jlems.eval.DoubleEvaluator;
import org.lemsml.jlems.logging.E;
import org.lemsml.jlems.sim.ContentError;

public class EventConnectionBuilder extends AbstractPostBuilder {

	String from;
	String to;
	
	StateType receiverCB;
	
	String destAttachments;

    String sourcePortId;
    String targetPortId;

    double delay = 0;

	ArrayList<ExpressionDerivedVariable> edvAL = new ArrayList<ExpressionDerivedVariable>();

	
	public EventConnectionBuilder(String sf, String st) {
		super();
		from = sf;
		to = st;
 
	}

	 
	public void postBuild(StateInstance base, HashMap<String, StateInstance> sihm, BuildContext bc) throws ConnectionError, ContentError, RuntimeError {
  		StateInstance sf = sihm.get(from);
		StateInstance st = sihm.get(to);
		
	
		if (sf == null) {
			sf = base.getChild(from);
		}
		if (st == null) {
			st = base.getChild(to);
		}
		
		
		if (sf == null) {
			throw new ConnectionError("The source state instance is null when getting " + from + " on " + base);
		}
		if (st == null) {
			throw new ConnectionError("The target state instance is null when getting " + to + " on " + base);
		}
		
        //E.info("postBuild for "+base+". from: "+from+" ("+sourcePortId+"), to: "+to+" ("+targetPortId+")");
        //E.info("sihm: "+sihm);
	 	
		HashMap<String, DoublePointer> baseHM = base.getVariables();

		if (receiverCB != null) {
			StateInstance rsi = (StateInstance)(receiverCB.newInstance());

            for (ExpressionDerivedVariable edv: edvAL) {
			    //   E.info("Evaluating " + edv + " using "+ baseHM);
            		try {
					double d = edv.evalptr(baseHM);
					
					String vnm = edv.getVarName();
					rsi.setNewVariable(vnm, d);
					//E.info("Set new var " + vnm + " " + d);
            		} catch (RuntimeError re) {
            			throw new ConnectionError(re);
            		}
			}

			InPort inPort = null;
            if (targetPortId!=null) {
                inPort = rsi.getInPort(targetPortId);
            } else {
                inPort = rsi.getFirstInPort();
            }
			if (inPort == null) {
				E.error("No input port ("+targetPortId+") on " + rsi);
			}

            OutPort op = null;
            if (sourcePortId!=null) {
                op = sf.getOutPort(sourcePortId);
            } else {
                op = sf.getFirstOutPort();
            }
			if (inPort == null) {
				E.error("No input port ("+sourcePortId+") on " + st);
			}

			op.connectTo(inPort, delay, base.getEventManager());
			st.addAttachment(destAttachments, rsi);
			
		} else {
			InPort inPort = null;
            if (targetPortId != null) {
                inPort = st.getInPort(targetPortId);
            }
            else {
                inPort = st.getFirstInPort();
            }
			if (inPort == null) {
				E.error("No input port ("+targetPortId+") on " + st);
			}

            OutPort op = null;
            if (sourcePortId != null) {
                op = sf.getOutPort(sourcePortId);
            } else {
                op = sf.getFirstOutPort();
            }
			if (inPort == null) {
				E.error("No input port ("+sourcePortId+") on " + st);
			}

			
			for (ExpressionDerivedVariable edv: edvAL) {
				//   E.info("Evaluating " + edv + " using "+ baseHM);
				try {
					double d = edv.evalptr(baseHM);
					String vnm = edv.getVarName();
					st.setNewVariable(vnm, d);
					//E.info("Set new var " + vnm + " " + d);
				} catch (RuntimeError re) {
					throw new ConnectionError(re);
				}
			}
		
			if (op == null) {
				E.error("Event connection: no connection made from " + from + " to " + to + 
						" (" + sourcePortId + ", " + targetPortId + ")");
			} else {
				op.connectTo(inPort, delay, base.getEventManager());
			}
		}
	}

	 
	public void setSourcePortID(String sourcePort) {
		this.sourcePortId = sourcePort;
		
	}


	public void setTargetPortID(String targetPort) {
		this.targetPortId = targetPort;
		
	}


	public void setReceiverStateType(StateType cb) {
		receiverCB = cb;
	}


	public void setReceiverContainer(String sv) {
		destAttachments = sv;
		
	}

    public void setDelay(double delay) {
        this.delay = delay;
    }

	public void addAssignment(String property, DoubleEvaluator de) throws ContentError {
		// TOOD - de, or de.makeCopy() ?
		ExpressionDerivedVariable edv = new ExpressionDerivedVariable(property, de);
		edvAL.add(edv);
	}

 
	@Override
	public void consolidateStateTypes() {
		 if (receiverCB != null) {
			 receiverCB = receiverCB.getConsolidatedStateType("(receiver)");
		 }	
	}

}
