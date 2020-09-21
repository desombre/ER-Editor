package persistence;

import model.*;
import view.er_objects.EntityView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OpenMarkdowned implements Openable {

    List<Entity> entities;
    List<Relationship> relationships;
    List<DescriptionBox> descriptions;

    @Override
    public ERModel open(File f) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(f);

        ERModel model = new ERModel();

        readAllEntries(scanner);
        model.setEntities(entities);

        model.setRelationships(relationships);

        model.setDescriptions(descriptions);
        model.setFileName(f);
        model.setSaved(true);

        return model;

    }

    private void readAllEntries(Scanner scanner) {
        entities = new ArrayList<Entity>();
        relationships = new ArrayList<Relationship>();
        descriptions = new ArrayList<DescriptionBox>();
        while (scanner.hasNextLine()) {
            List<String> record = getRecordFromLine(scanner.nextLine());
            String recordType = record.get(0);
            if ("r".equals(recordType)) {
                processRelationshipRecord(record);
            } else if ("^".equals(recordType)) {
                processGeneralizationRecord(record);
            } else if ("@".equals(recordType)) {
                processAttributeRecord(record);
            } else if ("a".equals(recordType)) {
                processAggregateRecord(record);
            } else if ("w".equals(recordType)) {
                processWeakRecord(record);
            } else if ("d".equals(recordType)) {
                processDescriptionRecord(record);
            } else if ("e".equals(recordType)) {
                processEntityRecord(record);
            }

        }
    }

    private void processEntityRecord(List<String> record) {
        getOrCreateEntity(record.get(1));
    }

    private void processDescriptionRecord(List<String> record) {
        descriptions.add(new DescriptionBox(record.get(1), record.get(2)));
    }

    private void processWeakRecord(List<String> record) {
        Entity e = getOrCreateEntity(record.get(1));
        e.setWeak(true);
    }

    private void processAggregateRecord(List<String> record) {
        Entity whole = getOrCreateEntity(record.get(1));
        Entity part = getOrCreateEntity(record.get(2));
        part.setAggregatedEntity(whole);
    }

    private void processAttributeRecord(List<String> record) {
        Entity e = getOrCreateEntity(record.get(1));

        e.addAttribute(new Attribute(record.get(2), record.size() == 4));

    }

    private void processGeneralizationRecord(List<String> record) {
        Entity parent = getOrCreateEntity(record.get(1));
        Entity child = getOrCreateEntity(record.get(2));
        child.setParentEntity(parent);
    }

    private void processRelationshipRecord(List<String> record) {
        Entity e1 = getOrCreateEntity(record.get(1));
        Entity e2 = getOrCreateEntity(record.get(5));

        relationships
                .add(new Relationship(e1, e2, "n".equals(record.get(2)), "n".equals(record.get(4)), record.get(3)));
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next().trim());
            }
        }
        return values;
    }

    private Entity getOrCreateEntity(String name) {
        EntityView e = new EntityView();
        e.getErObject().setName(name);
        if (!entities.contains(e)) {
            entities.add(e.getErObject());
        } else {
            e = entities.get(entities.indexOf(e.getErObject())).getView();
        }
        return e.getErObject();
    }

}
