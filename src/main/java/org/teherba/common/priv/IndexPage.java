/*  IndexPage.java - main web page for Common
 *  @(#) $Id: 57d01d0860aef0c2f2783647be70c3c381710c86 $
 *  2016-10-11: IOException
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
import  org.teherba.common.web.MetaInfPage;
import  java.io.File;
import  java.io.IOException;
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
            ) throws IOException {
        if (true) { // try {
            PrintWriter out = basePage.writeHeader(request, response, language);
            out.write("<title>" + basePage.getAppName() + " Main Page</title>\n");
            out.write("</head>\n<body>\n");

            out.write("<h3>Common Version " + (new MetaInfPage()).getVersionString(this, "common") + "<h3>\n");
            out.write("<p>This project collects a series of classes and "
                    + "methods which are useful in several subprojects.</p>\n");
            out.write("<h3>Parameter Test</h3>\n");
            out.write("<form action=\"servlet\" method=\"POST\" enctype=\"multipart/form-data\">\n");
            out.write("<input name=\"view\" type=\"hidden\" value=\"index\" />\n");

            // first all normal fields
            out.write("<h4>Form Fields</h4>\n");
            String key = null;
            int ifld = 0;
            Iterator<String> fiter = basePage.getFormIterator();
            while (fiter.hasNext()) {
                key   = fiter.next();
                String value = basePage.getFormField(key);
                out.write("[field" + String.valueOf(ifld) + "] " + key
                        + ":  <input name=\"" + key + "\" type=\"text\" size=\"16\" value=\""
                        + value + "\" /><br />\n");
                ifld ++;
                if (value.equals("null")) {
                	throw new IOException("artificial null pointer exception");
                }
            } // while fiter

            // then the uploaded files
            out.write("<h4>Form Files</h4>\n");
            int fileCount = basePage.getFormFileCount();
            int
            ifile = 0;
            while (ifile < 2) {
                if (ifile < fileCount) {
                    FileItem fitem = basePage.getFormFile(ifile);
                    File location = ((DiskFileItem) fitem).getStoreLocation();
                    // out.write("<!-- fitem is located in " + location.getAbsolutePath() + " -->\n");
                    key = fitem.getName();
                } else {
                    key = "file" + String.valueOf(ifile);
                }
                out.write("[file" + String.valueOf(ifile) + "]<em> " + key
                         + "</em>:  <input name=\"" + key + "\" type=\"file\" size=\"16\""
                         + "\" style=\"font-family: Courier, monospace\"/><br />\n");
                ifile ++;
            } // while ifile

            out.write("  <input type=\"submit\" value=\"Submit\"><br />\n");
            out.write("</form>\n");

            ifile = 0;
            while (ifile < fileCount) {
                FileItem fitem = basePage.getFormFile(ifile);
                key = fitem.getName();
                out.write("<h4>Content of File " + String.valueOf(ifile) + ":<em>" + key +"</em></h4>\n");
                out.write(fitem.getString() + "\n</pre>\n");
                ifile ++;
            } // while ifile

            out.write(basePage.getOtherAuxiliaryLinks(language, "main"));
            basePage.writeTrailer(language, "quest");
    /*
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
    */
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
