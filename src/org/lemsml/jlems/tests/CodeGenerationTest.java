package org.lemsml.jlems.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.lemsml.jlems.core.codger.StateTypeGenerator;
import org.lemsml.jlems.core.expression.ParseError;
import org.lemsml.jlems.core.logging.E;
import org.lemsml.jlems.core.run.ConnectionError;
import org.lemsml.jlems.core.run.RuntimeError;
import org.lemsml.jlems.core.sim.ContentError;
import org.lemsml.jlems.core.sim.ParseException;
import org.lemsml.jlems.core.sim.Sim;
import org.lemsml.jlems.core.type.BuildException;
import org.lemsml.jlems.core.type.Component;
import org.lemsml.jlems.core.type.Lems;
import org.lemsml.jlems.core.xml.XMLException;
import org.lemsml.jlems.generation.JavaGenerator;
import org.lemsml.jlems.io.logging.DefaultLogger;
import org.lemsml.jlems.io.reader.FileInclusionReader;

// N.B. - not a test yet, just for development


public class CodeGenerationTest {

	
	
	 
	 
    public static void main(String[] args) throws ContentError, ParseError, ConnectionError, RuntimeError, IOException, ParseException, BuildException, XMLException {
    	DefaultLogger.initialize();
       
    	 
    		CodeGenerationTest cft = new CodeGenerationTest();
    		cft.runExample1();
    		
    	 
    }
    
    @Test
    public void runExample1() throws ContentError, ConnectionError, ParseError, IOException, RuntimeError, ParseException, BuildException, XMLException {
    	File f1 = new File("examples/example1.xml");
    	
    	File f2 = new File("src");
 		boolean ret = generateFromFile(f1, "na", f2);
 		assertTrue("Example 1", ret);
    }
    
    
    
    
    public boolean generateFromFile(File f, String tgtid, File destdir) throws ContentError,
    		ConnectionError, ParseError, IOException, RuntimeError, ParseException, 
    		BuildException, XMLException {
    	E.info("Loading LEMS file from: " + f.getAbsolutePath());

        FileInclusionReader fir = new FileInclusionReader(f);
        Sim sim = new Sim(fir.read());

        sim.readModel();
     
        sim.build();

        //sim.run();

        Lems lems = sim.getLems();
        
        StateTypeGenerator cg = new StateTypeGenerator("org.lemsml.dynamic");
        
        Component cna = lems.getComponent("na");
        cg.addStateType(cna.getStateType());
        
        
        //for (Component cpt : lems.getComponents()) {
        //	E.info("Adding cpt " + cpt.getID());
        //	cg.addStateType(cpt.getStateType());
        //}
        
		
		String srcCode = cg.getCombinedJavaSource();
		
		JavaGenerator jg = new JavaGenerator();
		jg.writeSourceFiles(destdir, cg);

		// cg.getJavaSource(tgtid);
		
		E.info("Generated code:\n\n" + srcCode);
		 
		
		return true;
		
    }
}
