package org.plantuml.idea.overlay.service;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import org.jetbrains.annotations.NotNull;
import org.plantuml.idea.overlay.model.Association;
import org.plantuml.idea.overlay.model.PlantUmlDocument;
import org.plantuml.idea.overlay.model.UmlClass;

import java.util.Collection;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PSI_ELEMENT;
import static org.plantuml.idea.overlay.util.PsiUtil.getName;
import static org.plantuml.idea.overlay.util.PsiUtil.getParentClassifierType;

public class UsagesVisualizer {

    private UsagesVisualizer() {
    }

    public static void addUsages(@NotNull final AnActionEvent event) {
        OverlayService overlayService = ServiceManager.getService(event.getProject(), OverlayService.class);

        PsiElement selectedElement = event.getData(PSI_ELEMENT);
        if (selectedElement != null) {
            String selectedName = getName(selectedElement);

            if (!overlayService.getPlantUmlDocument().hasUmlClass(selectedName)) {
                addUsages(overlayService.getPlantUmlDocument(), selectedElement);
            }
        }
    }

    private static void addUsages(PlantUmlDocument umlDocument, PsiElement psiElement) {
        UmlClass selectedClass = new UmlClass(getName(psiElement));
        umlDocument.getUmlClasses().add(selectedClass);

        Collection<PsiReference> allReferences = ReferencesSearch.search(psiElement).findAll();
        for (PsiReference reference : allReferences) {
            PsiNamedElement parentClassifierType = getParentClassifierType(reference.getElement());
            if (parentClassifierType != null) {
                UmlClass referencingClass = new UmlClass(getName(parentClassifierType));

                // String labelText = getMethodCall(reference.getElement());
                Association association = new Association(referencingClass, selectedClass);
                umlDocument.getAssociations().add(association);
            }
        }
    }

    private static String getMethodCall(PsiElement psiElement) {
        String labelText = null;
        PsiElement rightNavigation = psiElement.getParent();
        if (rightNavigation != null && rightNavigation.getText().contains(".")) {
            int lastDotPosition = rightNavigation.getText().lastIndexOf('.');
            if (lastDotPosition != -1) {
                labelText = rightNavigation.getText().substring(lastDotPosition + 1);
                PsiElement navigationCall = rightNavigation.getParent();
                if (navigationCall != null && navigationCall.getNode() != null
                        && navigationCall.getNode().getElementType().toString().toLowerCase().contains("call")) {
                    labelText += "()";
                }
            }
        }
        return labelText;
    }

}
