package org.plantuml.idea.overlay.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class PlantUmlDocument implements TextRepresentation {

    private final Collection<UmlClass> umlClasses = new HashSet<>();
    private final Collection<Association> associations = new HashSet<>();

    private final Map<String, UmlClass> nodeMap = new HashMap<>();

    public Collection<UmlClass> getUmlClasses() {
        return umlClasses;
    }

    public Collection<Association> getAssociations() {
        return associations;
    }

    public boolean hasUmlClass(String name) {
        return getUmlClasses().stream().anyMatch(umlClass -> umlClass.getName().equals(name));
    }

    @Override
    public String getTextRepresentation() {
        if (isEmpty()) {
            return "@startuml\n@enduml";
        }

        String umlClassesText = getUmlClasses().stream()
                .map(UmlClass::getTextRepresentation)
                .collect(Collectors.joining()) + "\n";

        String associationsText = getAssociations().stream()
                .map(Association::getTextRepresentation)
                .collect(Collectors.joining()) + "\n";

        return "@startuml\n"
                + "left to right direction\n"
                + "\n"
                + umlClassesText
                + associationsText
                + "@enduml";
    }

    private boolean isEmpty() {
        return getUmlClasses().isEmpty() && getAssociations().isEmpty();
    }
}
