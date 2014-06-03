package org.lemsml.jlems.viz;
 
import java.io.IOException;

import org.lemsml.jlems.LemsMain;
import org.lemsml.jlems.core.expression.ParseError;
import org.lemsml.jlems.core.run.ConnectionError;
import org.lemsml.jlems.core.run.RuntimeError;
import org.lemsml.jlems.core.sim.ContentError;
import org.lemsml.jlems.core.sim.ParseException;
import org.lemsml.jlems.core.type.BuildException;
import org.lemsml.jlems.core.xml.XMLException;
import org.lemsml.jlems.io.logging.DefaultLogger;
import org.lemsml.jlems.io.out.FileResultWriterFactory;
import org.lemsml.jlems.viz.datadisplay.SwingDataViewerFactory;

public final class VizMain {
 
	 private VizMain() {
		 
	 }
	 
  
	
    public static void main(String[] argv) throws ConnectionError, ContentError, 
    	  RuntimeError, ParseError, ParseException, BuildException, XMLException, IOException {        
    	FileResultWriterFactory.initialize();
    	SwingDataViewerFactory.initialize();
		DefaultLogger.initialize();
	 
    	LemsMain.main(argv);
    }
}
