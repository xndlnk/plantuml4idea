package org.plantuml.idea.overlay.model;

public class UmlClass implements TextRepresentation {

    private final String name;

    public UmlClass(String name) {
        this.name = name;
    }

    @Override
    public String getTextRepresentation() {
        return "class " + name + " {\n" +
                "}\n";
    }

    public String getName() {
        return name;
    }
}
