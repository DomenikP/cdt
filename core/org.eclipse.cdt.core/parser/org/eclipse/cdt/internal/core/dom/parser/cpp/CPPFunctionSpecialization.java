/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
/*
 * Created on Apr 22, 2005
 */
package org.eclipse.cdt.internal.core.dom.parser.cpp;

import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IFunctionType;
import org.eclipse.cdt.core.dom.ast.IParameter;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPDelegate;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunction;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPScope;
import org.eclipse.cdt.core.parser.util.ObjectMap;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPFunction.CPPFunctionDelegate;

/**
 * @author aniefer
 */
public class CPPFunctionSpecialization extends CPPSpecialization implements ICPPFunction, ICPPInternalFunction {
	private IFunctionType type = null;
	private IParameter [] specializedParams = null;

	public CPPFunctionSpecialization(IBinding orig, ICPPScope scope, ObjectMap argMap ) {
		super(orig, scope, argMap);
	}
	
	private ICPPFunction getFunction(){
		return (ICPPFunction) getSpecializedBinding();
	}

	public IParameter[] getParameters() throws DOMException {
		if( specializedParams == null ){
			ICPPFunction function = (ICPPFunction) getSpecializedBinding();
			IParameter [] params = function.getParameters();
			specializedParams = new IParameter[ params.length];
			for( int i = 0; i < params.length; i++ ){
				specializedParams[i] = new CPPParameterSpecialization( (ICPPParameter)params[i], (ICPPScope) getScope(), argumentMap );
			}
		}
		return specializedParams;
	}

	public IScope getFunctionScope() {
//		resolveAllDeclarations();
//	    if( definition != null ){
//			return definition.getFunctionScope();
//	    } 
//	        
//	    return declarations[0].getFunctionScope();
		return null;
	}

	public IFunctionType getType() throws DOMException {
		if( type == null ){
			ICPPFunction function = (ICPPFunction) getSpecializedBinding();
			type = function.getType();
			type = (IFunctionType) CPPTemplates.instantiateType( type, argumentMap );
		}
		
		return type;
	}

	public boolean isMutable() {
		return false;
	}

	public boolean isInline() throws DOMException {
		if( getDefinition() != null ){
			IASTNode def = getDefinition();
			while( !(def instanceof IASTFunctionDefinition) )
				def = def.getParent();
			return ((IASTFunctionDefinition)def).getDeclSpecifier().isInline();
		}
		return getFunction().isInline();
	}
	public boolean isStatic() {
		return isStatic( true );
	}
	public boolean isStatic(boolean resolveAll) {
		//TODO resolveAll
		ICPPInternalFunction f = (ICPPInternalFunction) getSpecializedBinding();
		if( f != null )
			return f.isStatic( resolveAll );
		return CPPFunction.hasStorageClass( this, IASTDeclSpecifier.sc_static );
	}

	public boolean isExtern() throws DOMException {
		ICPPFunction f = (ICPPFunction) getSpecializedBinding();
		if( f != null )
			return f.isExtern();
		return CPPFunction.hasStorageClass( this, IASTDeclSpecifier.sc_extern );
	}

	public boolean isAuto() throws DOMException {
		ICPPFunction f = (ICPPFunction) getSpecializedBinding();
		if( f != null )
			return f.isAuto();
		return CPPFunction.hasStorageClass( this, IASTDeclSpecifier.sc_auto );
	}

	public boolean isRegister() throws DOMException {
		ICPPFunction f = (ICPPFunction) getSpecializedBinding();
		if( f != null )
			return f.isRegister();
		return CPPFunction.hasStorageClass( this, IASTDeclSpecifier.sc_register );
	}

	public boolean takesVarArgs() throws DOMException {
		ICPPFunction f = (ICPPFunction) getSpecializedBinding();
		if( f != null )
			return f.takesVarArgs();
		
		ICPPASTFunctionDeclarator dtor = (ICPPASTFunctionDeclarator) getDefinition();
        if( dtor != null ){
            return dtor.takesVarArgs();
        }
        ICPPASTFunctionDeclarator [] ds = (ICPPASTFunctionDeclarator[]) getDeclarations();
        if( ds != null && ds.length > 0 ){
            return ds[0].takesVarArgs();
        }
        return false;
	}

	public ICPPDelegate createDelegate(IASTName name) {
		return new CPPFunctionDelegate( name, this );
	}
}
