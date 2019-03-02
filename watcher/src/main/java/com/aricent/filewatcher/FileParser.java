package com.aricent.filewatcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import com.aricent.filewatcher.FileInfo;
import javax.xml.namespace.QName;
public class FileParser {
    static final String NAME = "name";
    static final String FILE_INFO = "file_info";
    static final String PATH = "path";
    static final String TYPE = "type";
    static final String SIZE = "size";
    static final String MODIFIED_TIME = "modified_date";
    static final String CREATED_TIME = "created_date";


    public List<FileInfo> parseXML(String fileName) {
        List<FileInfo> empList = new ArrayList<>();
        FileInfo emp = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){
                   StartElement startElement = xmlEvent.asStartElement();
                   if(startElement.getName().getLocalPart().equals(FILE_INFO)){
                       emp = new FileInfo();
                   
                       Attribute idAttr = startElement.getAttributeByName(new QName(NAME));
                       if(idAttr != null){
                       emp.setFile_name(idAttr.getValue().toString());
                       }
                   }

                   //set the other varibles from xml elements
                   else if(startElement.getName().getLocalPart().equals(PATH)){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setFile_path(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals(TYPE)){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setFile_type(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals(CREATED_TIME)){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setFile_create_date(xmlEvent.asCharacters().getData());
                   }else if(startElement.getName().getLocalPart().equals(SIZE)){
                       xmlEvent = xmlEventReader.nextEvent();
                       emp.setFile_size(xmlEvent.asCharacters().getData());
                   }
               }
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
                   if(endElement.getName().getLocalPart().equals(FILE_INFO)){
                       empList.add(emp);
                   }
               }
            }
            
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return empList;
    }





    
    @SuppressWarnings({ "unchecked", "null" })
    public List<FileInfo> readConfig(String configFile) {
        List<FileInfo> items = new ArrayList<FileInfo>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            FileInfo item = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart().equals(NAME)) {
                        item = new FileInfo();
                        // We read the attributes from this tag and add the date
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(NAME)) {
                                item.setFile_name(attribute.getValue());
                            }

                        }
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(PATH)) {
                            event = eventReader.nextEvent();
                            item.setFile_path(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(TYPE)) {
                        event = eventReader.nextEvent();
                        item.setFile_type(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(SIZE)) {
                        event = eventReader.nextEvent();
                        item.setFile_size(event.asCharacters().getData());
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                    .equals(CREATED_TIME)) {
                event = eventReader.nextEvent();
                item.setFile_create_date(event.asCharacters().getData());
                continue;
            }
          


                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(FILE_INFO)) {
                        items.add(item);
                    }
                }

            }  
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }

}

