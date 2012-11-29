package org.lemsml.jlems.run;

import java.util.HashMap;

import org.lemsml.jlems.logging.E;
import org.lemsml.jlems.sim.ContentError;

public class PairsEventConnectionBuilder extends AbstractPostBuilder {

	String pairs;
	
	StateType receiverCB;
	
	String destAttachments;
	
	public PairsEventConnectionBuilder(String sp) {
		super();
		pairs = sp;
	}

	 
	public void postBuild(StateInstance base, HashMap<String, StateInstance> sihm, BuildContext bc) throws ConnectionError, ContentError, RuntimeError {
		InstancePairSet<StateInstance> ips = base.getInstancePairSet(pairs);
		
		
		BuildContext cbc = new BuildContext();
		bc.setWorkPairs(ips);
		postChildren(base, null, cbc);	
		
	
		int np = 0;
		for (InstancePair<StateInstance> ip : ips.getPairs()) {
			connectInstances(ip.getP(), ip.getQ());
			np += 1;
		}
		E.info("Connected " + np + " pairs");
	}
		
	
	
	
	private void connectInstances(StateInstance sf, StateInstance st) throws ContentError, ConnectionError, RuntimeError {

        //TODO: add check for named in & out ports!
		if (receiverCB != null) {
			StateInstance rsi = (StateInstance)(receiverCB.newInstance());
			InPort inPort = rsi.getFirstInPort();
			sf.getFirstOutPort().connectTo(inPort);
			st.addAttachment(destAttachments, rsi);
		//	E.info("added attachment " + rsi + " in  " + st);
			
		} else {
			InPort inPort = st.getFirstInPort();
			if (inPort == null) {
				throw new ConnectionError("no input port on " + st);
			}
			OutPort outPort = sf.getFirstOutPort();
			if (outPort == null) {
				throw new ConnectionError("no output port on " + sf);
			}
			outPort.connectTo(inPort);
		}
	}

	 
	public void setSourcePortID(String sourcePort) {
		// TODO Auto-generated method stub
		
	}


	public void setTargetPortID(String targetPort) {
		// TODO Auto-generated method stub
		
	}


	public void setReceiverStateType(StateType cb) {
		receiverCB = cb;
	}


	public void setReceiverContainer(String sv) {
		destAttachments = sv;
		
	}
 

	@Override
	public void consolidateStateTypes() {
		 if (receiverCB != null) {
			 receiverCB = receiverCB.getConsolidatedStateType("(evtcon)");
		 }	
	}

	

}
