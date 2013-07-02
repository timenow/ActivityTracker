package acTracker.entry;

import static org.junit.Assert.*;
import org.junit.Test;

public class DomainTest {

    @Test
    public void testCreation() {
        final String name = "Programming";
        final String description = "Program to solve the problems.";
        Domain domain = new Domain(name, description);
        assertEquals(name, domain.name());
        assertEquals(description, domain.description());
    }

}
