package org.plantuml.idea.overlay.model;

public class Association implements TextRepresentation {
    private final UmlClass sourceClass;
    private final UmlClass targetClass;

    public Association(UmlClass sourceClass, UmlClass targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    @Override
    public String getTextRepresentation() {
        return sourceClass.getName() + " --> " + targetClass.getName() + "\n";
    }

    public UmlClass getSourceClass() {
        return sourceClass;
    }

    public UmlClass getTargetClass() {
        return targetClass;
    }
}
