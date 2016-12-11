/*  Messages.java - Static help texts and other language specific messages for Common.
 *  Caution: must be UTF-8 - äöüÄÖÜß
 *  @(#) $Id: 57d01d0860aef0c2f2783647be70c3c381710c86 $
 *  2016-10-01: link to common
 *  2016-09-03, Dr. Georg Fischer: copied from Ramath
 */
/*
 * Copyright 2016 Dr. Georg Fischer <punctum at punctum dot kom>
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
import  org.teherba.common.web.BasePage;
import  java.io.Serializable;

/** Language specific message texts and formatting for Common's user interface.
 *  Apart from the language specific processing is found in the JSPs (in dbat/web),
 *  all internationalization of the Java source code is assembled in this class.
 *  Currently implemented natural languages (denoted by 2-letter ISO <em>country</em> codes) are:
 *  <ul>
 *  <li>en - English</li>
 *  <li>de - German</li>
 *  <li>fr - Français</li>
 *  </ul>
 *  <p />
 *  All methods in this class are not stateful, and therefore are
 *  <em>static</em> for easier activation.
 *  @author Dr. Georg Fischer
 */
public class Messages implements Serializable {
    public final static String CVSID = "@(#) $Id: 57d01d0860aef0c2f2783647be70c3c381710c86 $";

    /** No-args Constructor
     */
    public Messages() {
    } // Constructor
    
    /** Sets the application-specific ((error) message) texts
     *  @param basePage reference to the hash for message texts
     */
    public static void addMessageTexts(BasePage basePage) {
        String appLink = "<a title=\"main\" href=\"servlet?view=index&lang={parm}\">" 
                + basePage.getAppName() + "</a>";
        //--------
        basePage.add("en", "001", appLink);
        basePage.add("en", "002"
                , " <a href=\"mailto:punctum@punctum.com"
                + "?&subject=" + basePage.getAppName()
                + "\">Dr. Georg Fischer</a>"
                );
        //--------
        String laux = basePage.LANG_AUX;  // pseudo language code for links to auxiliary information
        int imess   = basePage.START_AUX; // start of messages    for links to auxiliary information
    /*
        String
        smess = String.format("%03d", imess ++);
        basePage.add(laux, smess, "<a title=\"main\"        href=\"servlet?view=index\">");
        basePage.add("en", smess, "{parm}Common</a> Main Page");
        basePage.add("de", smess, "{parm}Common</a>-Startseite");
        basePage.add("fr", smess, "Page d'accueil de {parm}Common</a>");
    */
        imess = basePage.addStandardLinks(imess);
    } // addMessageTexts

    //================
    // Main method
    //================

    /** Test driver 
     *  @param args language code: "en", "de"
     */
    public static void main(String[] args) {
        Messages help = new Messages();
        System.out.println("no messages");
    } // main

} // Messages
