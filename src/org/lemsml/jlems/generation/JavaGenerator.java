package org.lemsml.jlems.generation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lemsml.jlems.core.codger.StateTypeGenerator;
import org.lemsml.jlems.core.codger.metaclass.CodeUnit;
import org.lemsml.jlems.core.logging.E;
import org.lemsml.jlems.io.util.FileUtil;

public class JavaGenerator {
 
	
	public void writeSourceFiles(File destdir, StateTypeGenerator stg) throws IOException {
		
		
		for (CodeUnit mc : stg.getCodeUnits()) {
			String src = mc.generateJava();
			
			File wkdest = destdir;
			ArrayList<String> subps = mc.getSubPackages();
			for (String sp : subps) {
				wkdest = new File(wkdest, sp);
				wkdest.mkdirs();
			}
			
			
			File fdest = new File(wkdest, mc.getClassName() + ".java");
			FileUtil.writeStringToFile(src, fdest);
			E.info("Written " + fdest.getAbsolutePath());
		}
		
	}
}
