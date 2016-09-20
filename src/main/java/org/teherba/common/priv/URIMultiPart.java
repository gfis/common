/*  Test program for a multipart/form-data POST request
    @(#) $Id$
    2016-09-14: Dr. Georg Fischer: copied from URIReader
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
import  org.teherba.common.RegressionTester;
import  org.teherba.common.URIReader;
import  java.io.BufferedReader;
import  java.io.File;
import  java.io.InputStreamReader;
import  java.io.OutputStream;
import  java.io.OutputStreamWriter;
import  java.io.PrintWriter;
import  java.net.URL;
import  java.net.URLConnection;
import  java.net.HttpURLConnection;
import  java.nio.channels.Channels;
import  java.nio.channels.ReadableByteChannel;
import  java.nio.file.Files;
import  org.apache.log4j.Logger;

/** This is an experimental test program for multipart/form-dat posting to an URL.
 *  The general method is incorporated in {@link URIReader},
 *  and the commandline arguments are interpreted by {@link RegressionTester}.

 *  @author Dr. Georg Fischer
 */
public class URIMultiPart {
    public final static String CVSID = "@(#) $Id: 662096ff3e2d74af4f150ad456ad013960a4ae70 $";

    /** log4j logger (category) */
    private Logger log;

    /** With Apache HttpClient this could be simple, but that tool needs about 10 additional JARs
        HttpEntity entity = MultipartEntityBuilder
            .create()
            .addTextBody("number", "5555555555")
            .addTextBody("clip", "rickroll")
            .addBinaryBody("upload_file", new File(filePath), ContentType.create("application/octet-stream"), "filename")
            .addTextBody("tos", "agree")
            .build();

        HttpPost httpPost = new HttpPost("http://some-web-site");
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity result = response.getEntity();
    */
    //======================
    // Main method (test)
    //======================

    /** Test method: send a multipart/form-data request to an URI.
     *  @param args command line arguments: filename, URL
     *  <pre>
     *  java -cp dist/common.jar org.teherba.dbat.common.URIMultiPart [filename [uri]]
     *  </pre>
     *  Without any argument, the program takes a default input file.
     *  With 1 orgument, the program constructs a multipart/form-data body and prints that on STDOUT.
     *  With 2 arguemnts, it sends the multipart request to the specified URI and prints the
     *  response to STDOUT.
     *  This code is adopted from
     *  <a href="http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests?rq=1">stackoverflow.com/questions/2793150</a>.
     */
    public static void main(String[] args) {
        System.setProperty("jdk.net.registerGopherProtocol", "true"); // does not work, not soon enough?
        Logger log = Logger.getLogger(URIMultiPart.class.getName());

        HttpURLConnection httpCon = null;
        OutputStream   output     = null;
        String url = "";
        String fileName = "../gramword/test/quixote0.html";
        int iarg = 0;
        if (iarg < args.length) {
            fileName = args[iarg ++];
            if (iarg < args.length) {
                url = args[iarg ++];
            }
        }
        // "http://localhost:8080/gramword/servlet";
        File   textFile = new File(fileName);
        // File binaryFile = new File("/path/to/file.bin");
        String boundary = "---------------------------29061947" 
                + Long.toHexString(System.currentTimeMillis()); // a unique random value
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        String charset = "UTF-8";
        try {
            if (url.length() <= 0) { // test output of multipart/form-data to STDOUT
                output = System.out;
            } else {
                httpCon = (HttpURLConnection) (new URL(url)).openConnection();
                httpCon.setDoOutput(true);
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                output = httpCon.getOutputStream();
            }
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
            String mmBoundaryCRLF = "--" + boundary + CRLF;
            // Send normal param.
            writer.append(mmBoundaryCRLF);
            writer.append("Content-Disposition: form-data; name=\"view\"").append(CRLF);
            writer.append(CRLF).append("index").append(CRLF).flush();

            // Send text file.
            writer.append(mmBoundaryCRLF);
            writer.append("Content-Disposition: form-data; name=\"infile\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
            writer.append(CRLF).flush();
            Files.copy(textFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

        /*
            // Send binary file.
            writer.append(mmBoundaryCRLF);
            writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(binaryFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
        */
            writer.append(mmBoundaryCRLF);
            writer.append("Content-Disposition: form-data; name=\"enc\"").append(CRLF);
            writer.append(CRLF).append("ISO-8859-1").append(CRLF).flush();

            writer.append(mmBoundaryCRLF);
            writer.append("Content-Disposition: form-data; name=\"format\"").append(CRLF);
            writer.append(CRLF).append("html").append(CRLF).flush();

            writer.append(mmBoundaryCRLF);
            writer.append("Content-Disposition: form-data; name=\"lang\"").append(CRLF);
            writer.append(CRLF).append("de").append(CRLF).flush();

            writer.append(mmBoundaryCRLF);
            writer.append("Content-Disposition: form-data; name=\"strat\"").append(CRLF);
            writer.append(CRLF).append("all").append(CRLF).flush();

            // End of multipart/form-data.
            writer.append("--" + boundary + "--").append(CRLF);
            writer.flush();

            if (url.length() > 0) { // do a real request
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpCon.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } // real request

        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // main

} // URIReader
