/*******************************************************************************
 * Copyright (c) 2011 Institute for Software, HSR Hochschule fuer Technik  
 * Rapperswil, University of applied sciences and others
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html  
 *  
 * Contributors: 
 * Institute for Software - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.core.parser.tests.rewrite.changegenerator.replace;

import junit.framework.Test;

import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.CPPASTVisitor;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTForStatement;
import org.eclipse.cdt.core.parser.tests.rewrite.changegenerator.ChangeGeneratorTest;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;
import org.eclipse.cdt.internal.core.dom.rewrite.ASTModification;
import org.eclipse.cdt.internal.core.dom.rewrite.ASTModificationStore;





public class WhitespaceHandlingTest extends ChangeGeneratorTest {

	private boolean forReplaced = false;
	
	public WhitespaceHandlingTest(){
		super("Whitespace Handling in Replace"); //$NON-NLS-1$
	}

	@Override
	protected void setUp() throws Exception {
		source = "void foo(){\r\n\r\n  for(int i = 0; i < 10; i++){\r\n\r\n  }\r\n\r\n  for(int j = 0; j < 10; j++){\r\n\r\n  }\r\n\r\n}"; //$NON-NLS-1$
		expectedSource = "void foo(){\r\n\r\n  for(int i = 0; i < 10; i++);\r\n\r\n  for(int j = 0; j < 10; j++){\r\n\r\n  }\r\n\r\n}"; //$NON-NLS-1$
		forReplaced = false;
		super.setUp();
	}

	public static Test suite() {		
		return new WhitespaceHandlingTest();
	}


	@Override
	protected CPPASTVisitor createModificator(
			final ASTModificationStore modStore) {
		return new CPPASTVisitor() {
			{
				shouldVisitStatements = true;
			}
			
			@Override
			public int visit(IASTStatement statement) {
				if (!forReplaced  && statement instanceof ICPPASTForStatement) {
					ICPPASTForStatement forStatement = (ICPPASTForStatement) statement;
					CPPNodeFactory nf = CPPNodeFactory.getDefault();
					
					ICPPASTForStatement newFor = forStatement.copy();
					newFor.setBody(nf.newNullStatement());
					
					ASTModification modification = new ASTModification(ASTModification.ModificationKind.REPLACE, forStatement, newFor, null);
					modStore.storeModification(null, modification);
					
					forReplaced = true;
				}

				return PROCESS_CONTINUE;
			}
		};
	}
}