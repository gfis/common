/*  IndexPage.java - main web page for Common
 *  @(#) $Id: 57d01d0860aef0c2f2783647be70c3c381710c86 $
 *  2016-09-03: Dr. Georg Fischer: copied from Ramath
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
import  java.io.File;
import  java.io.PrintWriter;
import  java.io.Serializable;
import  java.util.Iterator;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  org.apache.commons.fileupload.disk.DiskFileItem;
import  org.apache.commons.fileupload.FileItem;
import  org.apache.log4j.Logger;

/** Common main dialog page
 *  @author Dr. Georg Fischer
 */
public class IndexPage implements Serializable {
    public final static String CVSID = "@(#) $Id: 57d01d0860aef0c2f2783647be70c3c381710c86 $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;

    /** No-args Constructor
     */
    public IndexPage() {
        log = Logger.getLogger(IndexPage.class.getName());
    } // Constructor

    /** Output the main dialog page for RaMath
     *  @param request request with header fields
     *  @param response response with writer
     *  @param basePage refrence to common methods and error messages
     *  @param language 2-letter code en, de etc.
     */
    public void dialog(HttpServletRequest request, HttpServletResponse response
            , BasePage basePage
            , String language
            ) {
        try {
            PrintWriter out = basePage.writeHeader(request, response, language);
            out.write("<title>" + basePage.getAppName() + " Main Page</title>\n");
            out.write("</head>\n<body>\n");

            out.write("<h2>Common</h2>\n");
            out.write("<p>This project collects a series of classes and "
                    + "methods which are useful in several subprojects.</p>\n");
            out.write("<h3>Parameter Test</h3>\n");
            out.write("<form action=\"servlet\" method=\"post\" enctype=\"multipart/form-data\">\n");
            // first 2 text fields
            out.write("param1:  <input name=\"param1\" type=\"text\" size=\"10\" value=\"" 
                    + basePage.getFormField("param1") + "\" /><br />\n");
            out.write("param2:  <input name=\"param2\" type=\"text\" size=\"10\" value=\"" 
                    + basePage.getFormField("param2") + "\" /><br />\n");
            // then 2 files
            int ifile = 0;
            FileItem fitem1 = basePage.getFormFile(ifile ++);
            FileItem fitem2 = basePage.getFormFile(ifile ++);
            if (fitem1 != null) {
                File location = ((DiskFileItem) fitem1).getStoreLocation();
                out.write("<!-- fitem1 is located in " + location.getAbsolutePath() + " -->\n");
            }
            out.write("file1:  <input name=\"file1\"  type=\"file\" size=\"10\" value=\"" 
            		+ fitem1.getName()
                    + "\" style=\"font-family: Courier, monospace\"/><br />\n");
            out.write("file2:  <input name=\"file2\"  type=\"file\" size=\"10\" value=\"" 
            		+ fitem2.getName()
                    + "\" style=\"font-family: Courier, monospace\"/><br />\n");
            out.write("  <input type=\"submit\" value=\"Submit\"><br />\n");
            out.write("</form>\n");
            out.write("<h4>Content of file1</h4>\n<pre>");
            out.write(fitem1.getString() + "\n</pre>\n");
            out.write("<h4>Content of file2</h4>\n<pre>");
            out.write(fitem2.getString() + "\n</pre>\n");
            basePage.writeAuxiliaryLinks(language, "main");
            basePage.writeTrailer(language, "quest");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // dialog

    //================
    // Main method
    //================

    /** Test driver
     *  @param args language code: "en", "de"
     */
    public static void main(String[] args) {
        IndexPage help = new IndexPage();
        System.out.println("no messages");
    } // main

} // IndexPage
