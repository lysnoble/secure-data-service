/*
 * Copyright 2012 Shared Learning Collaborative, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.slc.sli.scaffold;

import org.w3c.dom.Document;

import java.io.File;

/**
 * User: wscott
 */
public class XslTransformDocument {
    private final DocumentManipulator handler = new DocumentManipulator();

    public XslTransformDocument() {
        handler.init();
    }

    public void transform(File wadlFile, File xsltFile, String outputFile) {
        try {
            Document wadlDoc = handler.parseDocument(wadlFile);

            handler.serializeDocumentToHtml(wadlDoc, new File(outputFile), xsltFile);
        } catch (DocumentManipulatorException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param args wadl, xslt, output
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            return;
        }

        XslTransformDocument xslt = new XslTransformDocument();
        xslt.transform(new File(args[0]), new File(args[1]), args[2]);
    }
}
