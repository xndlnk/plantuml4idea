package org.plantuml.idea.overlay.service;

import com.intellij.openapi.components.Service;
import org.plantuml.idea.overlay.model.PlantUmlDocument;

@Service
public final class OverlayService {

    private PlantUmlDocument plantUmlDocument = new PlantUmlDocument();

    public PlantUmlDocument getPlantUmlDocument() {
        return plantUmlDocument;
    }

    public void resetPlantUmlDocument() {
        plantUmlDocument = new PlantUmlDocument();
    }

}
