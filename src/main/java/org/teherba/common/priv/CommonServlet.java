/*  CommonServlet.java - Rational and Symbolic Mathematics
 *  @(#) $Id: CommonServlet.java 199 2009-07-13 20:16:23Z gfis $
 *  2016-09-03, Georg Fischer: copied from RaMathServlet
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
import  org.teherba.common.priv.IndexPage;
import  org.teherba.common.priv.Messages;
import  org.teherba.common.web.BasePage;
import  org.teherba.common.web.MetaInfPage;
import  java.io.IOException;
import  javax.servlet.ServletConfig;
import  javax.servlet.ServletException;
import  javax.servlet.http.HttpServlet;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  org.apache.log4j.Logger;

/** This servlet is only used to test the functionality of 
 *  the classes in packages <tt>org.teherba.common.*</tt>.
 *  @author Dr. Georg Fischer
 */
public class CommonServlet extends HttpServlet {
    public final static String CVSID = "@(#) $Id: CommonServlet.java 199 2009-07-13 20:16:23Z gfis $";
    public final static long serialVersionUID = 19470629;

    /** URL path to this application */
    private String applPath;
    /** log4j logger (category) */
    private Logger log;
    /** common code and messages for auxiliary web pages */
    private BasePage basePage;
    /** name of this application */
    private static final String APP_NAME = "Common";

    /** Called by the servlet container to indicate to a servlet
     *  that the servlet is being placed into service.
     *  @param config object containing the servlet's configuration and initialization parameters
     *  @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // ???
        log = Logger.getLogger(CommonServlet.class.getName());
        basePage = new BasePage(APP_NAME);
        Messages.addMessageTexts(basePage);
    } // init

    /** Processes an http GET request
     *  @param request request with header fields
     *  @param response response with writer
     *  @throws IOException
     */
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        // log.debug("doGet");
        generateResponse(request, response);
    } // doGet

    /** Processes an http POST request
     *  @param request request with header fields
     *  @param response response with writer
     *  @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // log.debug("doPost");
        generateResponse(request, response);
    } // doPost

    /** Generates the response (HTML page) for an http request
     *  @param request request with header fields
     *  @param response response with writer
     */
    public void generateResponse(HttpServletRequest request, HttpServletResponse response) {
        String view = basePage.getFilesAndFields(request, new String[] 
                { "view",   "index"
                , "param1", "value1"
                , "param2", "value2"
                } );
        String language = "en";
        try {
            if (false) {
            } else if (view.equals("index")) {
                (new IndexPage    ()).dialog(request, response, basePage, language);
                                
            } else if (view.equals("license")
                    || view.equals("manifest")
                    || view.equals("notice")
                    ) {
                (new MetaInfPage  ()).showMetaInf (request, response, basePage, language, view, this);

            } else {
                (new IndexPage    ()).dialog(request, response, basePage, language);
            //  basePage.writeMessage(request, response, language, new String[] { "401", "view", view });
            }
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // generateResponse

} // CommonServlet
