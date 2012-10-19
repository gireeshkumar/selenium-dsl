/*	
 *	License
 *	
 *	This file is part of The TARGet framework
 * 
 *   	/__  ___/ // | |     //   ) )  //   ) )
 *   	  / /    //__| |    //___/ /  //         ___    __  ___
 *   	 / /    / ___  |   / ___ (   //  ____  //___) )  / /
 *   	/ /    //    | |  //   | |  //    / / //        / /
 *      / /    //     | | //    | | ((____/ / ((____    / /
 *   	 
 *	    ______     __,             _ ___              ,____                                                   
 *      (  /       /  |            ( /   )              /   )                                            
 *	     /       /-.-|             /-.-<              /  __                                  
 *  Web _/est   _/    |_utomation f/     \_amework by (___/iri
 *  -.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.
 *	

 *  TARGet is free software: you can redistribute it and/or
 *  modify it  under  the  terms  of  the  GNU  General Public License as 
 *  published  by  the  Free  Software Foundation,  either  version  3 of 
 *  the License, or any later version.
 *
 *  TARGet is distributed in the hope that it will be useful,
 *  but  WITHOUT  ANY  WARRANTY;  without  even the  implied  warranty  of
 *  MERCHANTABILITY   or   FITNESS   FOR  A  PARTICULAR  PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with The SeleniumFlex-API.
 *  If not, see http://www.gnu.org/licenses/
 *  
 * 
 *  @Author	Gireesh Kumar G - gireeshkumar.g@gmail.com
 *  @Date 	July 2010
 *
 */
package com.giri.target.ui.view;

import java.lang.reflect.Method;

/**
 * @author Gireesh Kumar G
 * @Created Jul 6, 2009
 */
public class MethodActionListener implements Listener {

	private Object object;
	private Method method;

	/**
	 * @param actionPerformed
	 * @param statusPanel
	 */
	public MethodActionListener(Method method, Object object) {
		this.object = object;
		this.method = method;
	}

	/*
	 * (non-Javadoc)
	 * @see com.giri.gwat.ui.view.Listener#invoke(java.lang.Object)
	 */
	@Override
	public void invoke(Object[] params) throws Exception {
		Class<?>[] paramTypes = method.getParameterTypes();
		Object[] args = (paramTypes == null || paramTypes.length == 0 ? null
				: params);
		this.method.invoke(object, args);
	}

}
