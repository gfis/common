/*  ClipBoard.java - access to the system's clip board
 *  @(#) $Id$
 *  2026-05-31, Georg Fischer: copied from RaMath; *GP=83
 */
/*
 * Copyright 2026 Dr. Georg Fischer <dr dot georg dot fischer at gmail...>
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
package org.teherba.common;
import  java.awt.datatransfer.StringSelection;
import  java.awt.Toolkit;
import  java.awt.datatransfer.DataFlavor;
// import java.awt.datatransfer.Clipboard;

/**
 *  Access to the system's clipboard.
 *  @author Dr. Georg Fischer
 */
public class ClipBoard {

    /**
     *  Write a string to the clipboard.
     *  @param text string to be written to the clipboard
     */
    public static void write(String text) {
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
    } // write

    /**
     *  Read the clipboard.
     *  @return text string read from the clipboard
     */
    public static String read() {
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
            return "";
        }
    } // read

    /**
     *  Main program.
     *  @param args command line arguments: a single string to be copied to the clipboard
     */
    public static void main(String[] args) {
        try {
            if (false) {
            } else if (args.length == 1) {
                ClipBoard.write(args[0]);
            } else if (args.length == 0) {
                System.out.println(ClipBoard.read());
            }
        } catch (Exception exc) {
        }
    } // main

} // ClipBoard
