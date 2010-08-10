/*
 *   Copyright 2009 Joubin Houshyar
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *    
 *   http://www.apache.org/licenses/LICENSE-2.0
 *    
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.jredis.ri.alphazero.support;

import org.apache.commons.logging.LogFactory;

/**
 * TODO: deprecate this and use a standard logger (jdk or log4j ...)
 * 
 * @author  Joubin Houshyar (alphazero@sensesay.net)
 * @version alpha.0, Apr 02, 2009
 * @since   alpha.0
 * 
 */
public class Log {
	public static org.apache.commons.logging.Log logger = LogFactory.getLog("JREDIS");
//	public static final Logger logger = LoggerFactory.getLog("-- JREDIS --");
	public enum Category { INFO, ERROR, PROBLEM, BUG }

	public static final void log (String msg)   { _loginfo (msg); }
	public static final void error (String msg)   { _log (Category.ERROR, msg); }
	public static final void problem (String msg) { _log (Category.PROBLEM, msg); }
	public static final void bug (String msg)     { _log (Category.BUG, msg); }

	public static final void log (String format, Object...args) {
		_loginfo(format, args);
	}
	private static final void _log (Category cat, String msg) {
//		System.err.format("-- JREDIS -- %s: %s\n", cat, msg).flush();
		logger.error(String.format("%s: %s", cat, msg));
	}
	private static final void _loginfo (String format, Object...args) {
//		System.out.format("-- JREDIS -- INFO: "+format+" \n", args).flush();
		logger.info(String.format(format, args));
	}
}
