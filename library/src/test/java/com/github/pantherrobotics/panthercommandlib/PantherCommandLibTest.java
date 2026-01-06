package com.github.pantherrobotics.panthercommandlib;

import org.junit.Test;
import static org.junit.Assert.*;

public class PantherCommandLibTest {
    @Test
    public void testLibraryVersion() {
        assertNotNull("Library should have a version", PantherCommandLib.getVersion());
    }
}

