package acTracker.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import acTracker.entry.*;

public class DataContext {
    
    private Map<Date, DayActivityLog> dayActivityLogDepot = 
            new HashMap<Date, DayActivityLog>();
    private List<Project> projects = null;
    private List<Domain> domains = null;
    
    public void saveDayActivitiesInfo(DayActivityLog dayActivitiesInfo) {
        dayActivityLogDepot.put(dayActivitiesInfo.getDate(), dayActivitiesInfo);
    }

    public DayActivityLog getDayActivityLog(Date date) {
        return dayActivityLogDepot.get(date);
    }
    
    public List<Project> getProjects() {
        if (projects == null) {
            loadProjects();
        }
        
        return new ArrayList<Project>(projects);
    }
    
    /**
     * Load projects from xml data file.
     */
    private void loadProjects() {
        SAXReader reader = new SAXReader(true);
        
        try {
            Document doc = reader.read(new File("data/projects.xml"));
            Element rootElement = doc.getRootElement();
            
            projects = new ArrayList<Project>();
            for (Object element : rootElement.elements("project")) {
                Project project = parseToProject((Element)element);
                projects.add(project);
            }
        }
        catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to loading projects from xml file", e);
        }
    }

    private Project parseToProject(Element element) {
        String name = element.elementText("name");
        String description = element.elementText("description");
        return new Project(name, description);
    }

    public List<Domain> getDomains() {
        if (domains == null)
            loadDomains();
        
        return new ArrayList<Domain>(domains);
    }

    private void loadDomains() {
        SAXReader reader = new SAXReader();
        
        try {
            Document document = reader.read(new File("data/domains.xml"));
            Element rootElement = document.getRootElement();
            
            domains = new ArrayList<Domain>();
            for (Object element : rootElement.elements("domain" )) {
                Domain domain = parseToDomain((Element)element);
                domains.add(domain);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to loading domains from xml file", e);
        }
    }

    private Domain parseToDomain(Element element) {
        String name = element.elementText("name");
        String description = element.elementText("description");
        return new Domain(name, description);
    }

    public void addProject(Project project) {
        if (projects == null)
            loadProjects();
        if (projects.contains(project))
            return;
        
        projects.add(project);
        saveProjects();
    }
    
    private void saveProjects() {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");
        doc.addDocType("projects", null, "../schema/projects.dtd");
        Element rootElement = doc.addElement("projects");
        
        for (Project project : projects) {
            Element domainElement = rootElement.addElement("project");
            domainElement.addElement("name").setText(project.getName());
            domainElement.addElement("description").setText(project.getDescription());
        }
        
        saveToXml(doc, "data/projects.xml");
    }

    public void addDomain(Domain domain) {
        if (domains == null)
            loadDomains();
        if (domains.contains(domain))
            return;
        
        domains.add(domain);
        saveDomains();
    }

    private void saveDomains() {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");
        doc.addDocType("domains", null, "../schema/domains.dtd");
        Element rootElement = doc.addElement("domains");
        
        for (Domain domain : domains) {
            Element domainElement = rootElement.addElement("domain");
            domainElement.addElement("name").setText(domain.getName());
            domainElement.addElement("description").setText(domain.getDescription());
        }
        
        saveToXml(doc, "data/domains.xml");
    }

    private void saveToXml(Document doc, String filename) {
        XMLWriter writer = null;
        
        try {
            FileOutputStream out = new FileOutputStream(filename);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
           
            writer = new XMLWriter(out, format);
            writer.write(doc);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
        finally {
            close(writer);
        }
    }
    
    private void close(XMLWriter writer) {
        if (writer == null)
            return;
        
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
