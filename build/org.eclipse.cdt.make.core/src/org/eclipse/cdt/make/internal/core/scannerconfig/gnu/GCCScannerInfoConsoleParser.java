/**********************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.cdt.make.internal.core.scannerconfig.gnu;

import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.cdt.make.core.scannerconfig.IScannerInfoCollector;
import org.eclipse.cdt.make.core.scannerconfig.IScannerInfoConsoleParser;
import org.eclipse.cdt.make.internal.core.scannerconfig.IScannerInfoConsoleParserUtility;
import org.eclipse.cdt.make.internal.core.scannerconfig.util.TraceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses gcc and g++ output for -I and -D parameters.
 * 
 * @author vhirsl
 */
public class GCCScannerInfoConsoleParser implements IScannerInfoConsoleParser {

	private IProject fProject = null;
	private IScannerInfoConsoleParserUtility fUtil = null;
	private IScannerInfoCollector fCollector = null;
	
	/* (non-Javadoc)
	 * @see org.eclipse.cdt.make.core.scannerconfig.IScannerInfoConsoleParser#startup(org.eclipse.core.resources.IProject, org.eclipse.cdt.make.internal.core.scannerconfig.IScannerInfoConsoleParserUtility, org.eclipse.cdt.make.core.scannerconfig.IScannerInfoCollector)
	 */
	public void startup(IProject project, IScannerInfoConsoleParserUtility util, IScannerInfoCollector collector) {
		fProject = project;
		fUtil = util;
		fCollector = collector;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.make.core.scannerconfig.ScannerInfoConsoleParserUtility#processLine(java.lang.String)
	 */
	public boolean processLine(String line) {
		boolean rc = false;
		// make\[[0-9]*\]:  error_desc
		int firstColon= line.indexOf(':');
		String make = line.substring(0, firstColon + 1);
		if (firstColon != -1 && make.indexOf("make") != -1) { //$NON-NLS-1$
			boolean enter = false;
			String msg = line.substring(firstColon + 1).trim();		
			if ((enter = msg.startsWith("Entering directory")) || //$NON-NLS-1$
			    (msg.startsWith("Leaving directory"))) { //$NON-NLS-1$
			    int s = msg.indexOf('`');
			    int e = msg.indexOf('\'');
			    if (s != -1 && e != -1) {
			    	String dir = msg.substring(s+1, e);
			    	fUtil.changeMakeDirectory(dir, getDirectoryLevel(line), enter);
			    	return rc;
				}
			}
		}
		// Known patterns:
		// (a) gcc|g++ ... -Dxxx -Iyyy ...
		StringTokenizer scanner = new StringTokenizer(line);
		if (scanner.countTokens() <= 1)
			return false;
		String token = scanner.nextToken().toLowerCase();
		if (token.endsWith("gcc") || token.endsWith("g++")) {//$NON-NLS-1$ //$NON-NLS-2$
			// Recognized gcc or g++ compiler invocation
			List includes = new ArrayList();
			List symbols = new ArrayList();
			List targetSpecificOptions = new ArrayList();

			rc = true;
			String fileName = null;
			String cashedToken = null;
			while (scanner.hasMoreTokens()) {
				if (cashedToken == null) {
					token = scanner.nextToken();
				}
				else {
					token = cashedToken;
					cashedToken = null;
				}
				if (token.startsWith("-D")) {//$NON-NLS-1$
					String symbol = token.substring(2);
					if (symbol.length() == 0) {
						if (scanner.hasMoreTokens()) {
							symbol = scanner.nextToken();
							if (symbol.startsWith("-")) {	//$NON-NLS-1$
								cashedToken = symbol;
								continue;
							}
						}
						else {
							continue;
						}
					}
					if (!symbols.contains(symbol))
						symbols.add(symbol);
				}
				else if (token.startsWith("-I")) {//$NON-NLS-1$
					String iPath = token.substring(2);
					if (iPath.length() == 0) {
						if (scanner.hasMoreTokens()) {
							iPath = scanner.nextToken();
							if (iPath.startsWith("-")) {	//$NON-NLS-1$
								cashedToken = iPath;
								continue;
							}
						}
						else {
							continue;
						}
					}
					String nPath = fUtil.normalizePath(iPath);
					if (!includes.contains(nPath))
						includes.add(nPath);
				}
				else if (token.equals("-mwin32") ||		//$NON-NLS-1$
						 token.equals("-mno-win32") ||	//$NON-NLS-1$
						 token.equals("-mno-cygwin") ||	//$NON-NLS-1$
						 token.equals("-ansi") ||		//$NON-NLS-1$
						 token.equals("-nostdinc") ||	//$NON-NLS-1$
						 token.equals("-posix") ||		//$NON-NLS-1$
						 token.equals("-pthread")) {	//$NON-NLS-1$
					if (!targetSpecificOptions.contains(token))
						targetSpecificOptions.add(token);
				}
				else {
					String possibleFileName = token.toLowerCase();
					if (possibleFileName.startsWith("..") ||	//$NON-NLS-1$
						possibleFileName.startsWith(".") ||		//$NON-NLS-1$
						possibleFileName.startsWith("/") ||		//$NON-NLS-1$
						possibleFileName.endsWith(".c") || 		//$NON-NLS-1$
						possibleFileName.endsWith(".cpp") ||	//$NON-NLS-1$
						possibleFileName.endsWith(".cc") ||		//$NON-NLS-1$
						possibleFileName.endsWith(".cxx")) {	//$NON-NLS-1$
						
						fileName = token;
					}
				}
			}
			
			IProject project = fProject;   
			IFile file = null;
			List translatedIncludes = includes;
			if (includes.size() > 0) {
				if (fileName != null) {
					file = fUtil.findFile(fileName);
					if (file != null) {
						project = file.getProject();
						translatedIncludes = fUtil.translateRelativePaths(file, fileName, includes);
					}
				}
				else {
					TraceUtil.outputError("Unable to find file name: ", line);	//$NON-NLS-1$
					fUtil.generateMarker(fProject, -1, "Unable to find file name: " + line, //$NON-NLS-1$
							IMarkerGenerator.SEVERITY_ERROR_RESOURCE, null);
				}
			}
			// Contribute discovered includes and symbols to the ScannerInfoCollector
			if (translatedIncludes.size() > 0 || symbols.size() > 0) {
				fCollector.contributeToScannerConfig(project, translatedIncludes, symbols, targetSpecificOptions);
				
				TraceUtil.outputTrace("Discovered scanner info for file \'" + fileName + '\'',	//$NON-NLS-1$
						"Include paths", includes, translatedIncludes, "Defined symbols", symbols);	//$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return rc;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.make.core.scannerconfig.IScannerInfoConsoleParser#shutdown()
	 */
	public void shutdown() {
		if (fUtil != null) {
			fUtil.reportProblems();
		}
	}

	private int getDirectoryLevel(String line) {
		int s = line.indexOf('[');
		int num = 0;
		if (s != -1) {
			int e = line.indexOf(']');
			String number = line.substring(s + 1, e).trim();		
			try {
				num = Integer.parseInt(number);
			} catch (NumberFormatException exc) {
			}
		}
		return num;
	}
}
