package org.lemsml.jlems.tests;
  
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.lemsml.jlems.core.expression.ParseError;
import org.lemsml.jlems.core.logging.E;
import org.lemsml.jlems.core.run.ConnectionError;
import org.lemsml.jlems.core.run.RuntimeError;
import org.lemsml.jlems.core.sim.ContentError;
import org.lemsml.jlems.core.sim.LemsProcess;
import org.lemsml.jlems.core.sim.ParseException;
import org.lemsml.jlems.core.sim.Sim;
import org.lemsml.jlems.core.type.BuildException;
import org.lemsml.jlems.core.type.Lems;
import org.lemsml.jlems.core.xml.XMLElementReader;
import org.lemsml.jlems.core.xml.XMLException;
import org.lemsml.jlems.io.logging.DefaultLogger;
import org.lemsml.jlems.io.reader.FileInclusionReader;
import org.lemsml.jlems.io.xmlio.XMLSerializer;
 


public class LemsExamplesReaderTest {
 
	@Test
	public void testReadFromString() throws ParseException, BuildException,
			ContentError, XMLException, IOException, ParseError, ConnectionError, RuntimeError {

		File fdir = new File("examples");
		File[] fa = fdir.listFiles();
		Arrays.sort(fa);
	
		for (File fx : fa) {
			E.info("Reading " + fx);
			
			if (fx.getName().startsWith("example")) {
 				FileInclusionReader fir = new FileInclusionReader(fx);
				String fullText = fir.read();
				
				Sim sim = new Sim(fullText);

				sim.readModel();
				Lems lems = sim.getLems();

				String sout = XMLSerializer.serialize(lems);	 
		 
				LemsProcess lp2 = new LemsProcess(sout);

				try {
					lp2.readModel();
				} catch (ContentError ex) {
					E.info("Reread failed for:\n" + sout);
					throw ex;
				}
				
				Lems lems2 = lp2.getLems();
			
				String sout2 = XMLSerializer.serialize(lems2);	 		
	 
				if (sout.equals(sout2)) {
					E.info("--- Lems Read/write OK for " + fx);
				} else {
					E.info("Read/write failure for " + fx.getName());
					E.info("Exported:  " + XMLElementReader.deSpace(sout));
					E.info("Reloaded:  " + XMLElementReader.deSpace(sout2));
					E.info("Reread failed on " + sout);
				}
	 		
				assertEquals("string read", sout, sout2);


                sim.build();

                E.info("Lems build OK for " + fx);

			}
		}
	}
	

	public static void main(String[] args) {
		DefaultLogger.initialize();
		LemsExamplesReaderTest ct = new LemsExamplesReaderTest();
		try {
			ct.testReadFromString();
		} catch (Exception ex) {
			E.report("", ex);
		}
			
		//Result r = org.junit.runner.JUnitCore.runClasses(ct.getClass());
		//MainTest.checkResults(r);

	}

}