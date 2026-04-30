/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.jxpath.ri.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.StringReader;
import java.io.PrintStream;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class XPathParserTokenManagerTest {

    @TempDir
    Path temp_dir;

    // case 1
    @Test
    void testConstructor_WithValidState() {
    SimpleCharStream stream = new SimpleCharStream(new StringReader("test"), 1, 1);

    assertDoesNotThrow(() -> {
        new XPathParserTokenManager(stream, 0);
        });
    }

    // case 2
    @Test
    void testReInit_ResetsState() {
        SimpleCharStream stream1 = new SimpleCharStream(new StringReader("input1"), 1, 1);
        XPathParserTokenManager manager = new XPathParserTokenManager(stream1, 0);

        SimpleCharStream stream2 = new SimpleCharStream(new StringReader("input2"), 1, 1);

        assertDoesNotThrow(() -> {
            manager.ReInit(stream2, 0);
        });
    }

    // case 3
    @Test
    void testSwitchTo_ValidState() {
        SimpleCharStream stream = new SimpleCharStream(new StringReader("data"), 1, 1);
        XPathParserTokenManager manager = new XPathParserTokenManager(stream, 0);
    
        assertDoesNotThrow(() -> {
            manager.SwitchTo(0);
        });
    }

    // case 4
    @Test
    void testSwitchTo_InvalidStateThrowsError() {
        SimpleCharStream stream = new SimpleCharStream(new StringReader("data"), 1, 1);
        XPathParserTokenManager manager = new XPathParserTokenManager(stream, 0);
    
        org.junit.jupiter.api.Assertions.assertThrows(TokenMgrError.class, () -> {
            manager.SwitchTo(-1);
        });
    }


    // case 5
    @Test
    void testSetDebugStream_AssignsStream() throws Exception {
        SimpleCharStream input_stream = new SimpleCharStream(new StringReader(""), 1, 1);
        XPathParserTokenManager token_manager = new XPathParserTokenManager(input_stream);

        Path testDebugFile = temp_dir.resolve("testDebugFile.txt");
        try (java.io.PrintStream testOutputStream = new PrintStream(testDebugFile.toFile())) {
            assertDoesNotThrow(() -> {token_manager.setDebugStream(testOutputStream);});
        }
    }

}
