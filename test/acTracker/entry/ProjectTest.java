package acTracker.entry;

import static org.junit.Assert.*;
import org.junit.Test;

public class ProjectTest {

    @Test
    public void testCreation() {
        final String name = "";
        final String description = "";
        Project project = new Project(name, description);
        assertEquals(name, project.name());
        assertEquals(description, project.description());
    }

}
