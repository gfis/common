/*  Common.java - Test program and commandline interface for common 
 *  @(#) $Id: 5e304452bf835121fdfa9220b4a46dfe19327a86 $
 *  2016-09-16, Georg Fischer_ copied from Dbat.java
 */
/*
 * Copyright 2006 Dr. Georg Fischer <punctum at punctum dot kom>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.teherba.common.priv;
import  org.teherba.common.web.MetaInfPage;
import  java.io.Serializable;
import  org.apache.log4j.Logger;

/** Commandline interface for common.
 *  @author Dr. Georg Fischer
 */
public class Common implements Serializable {
    public final static String CVSID = "@(#) $Id: 5e304452bf835121fdfa9220b4a46dfe19327a86 $";
    /** log4j logger (category) */
    private Logger log;
    /** Debugging switch */
    private int debug = 0;
    
    /** No-args Constructor
     */
    public Common() {
        log = Logger.getLogger(Common.class.getName());
    } // Constructor
    
    //======================
    // Main method
    //======================
    
    /** Common - test program
     *  The result is printed to STDOUT.
     *  @param args command line arguments: options, strings, table- or filenames
     */
    public static void main(String[] args) {
        Logger log = Logger.getLogger(Common.class.getName());
        Common commonCommand = new Common();
        if (args.length == 0) {
            args = new String[] { "-h" }; // usage message
        }
        try {
            System.out.println("Common " + (new MetaInfPage()).getVersionString("common"));
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // main

} // Common
