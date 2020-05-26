package org.plantuml.idea.overlay.util;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

public class PsiUtil {

    private PsiUtil() {
    }

    public static Collection<PsiNamedElement> getUsagesInClassifiersBottomUp(PsiElement psiElement) {
        Collection<PsiNamedElement> usages = new HashSet<>();

        Collection<PsiReference> allReferences = ReferencesSearch.search(psiElement).findAll();
        for (PsiReference reference : allReferences) {
            // System.out.println(reference.getElement().getContainingFile().getVirtualFile().getCanonicalPath());

            PsiNamedElement parentClassifierType = getParentClassifierType(reference.getElement());
            if (parentClassifierType != null) {
                usages.add(parentClassifierType);
            }
        }

        return usages;
    }

    public static Collection<PsiNamedElement> getUsagesInClassifiersTopDown(PsiElement psiElement) {
        Collection<PsiNamedElement> usages = new HashSet<>();

        Collection<PsiReference> allReferences = ReferencesSearch.search(psiElement).findAll();
        for (PsiReference reference : allReferences) {
            // System.out.println(reference.getElement().getContainingFile().getVirtualFile().getCanonicalPath());

            @NotNull PsiElement[] containingFileChildren = reference.getElement().getContainingFile().getChildren();
            for (PsiElement topLevelElement : containingFileChildren) {
                if (isClassifierType(topLevelElement) && topLevelElement instanceof PsiNamedElement) {
                    PsiNamedElement namedElement = (PsiNamedElement) topLevelElement;
                    usages.add(namedElement);
                }
            }
        }

        return usages;
    }

    public static String getName(PsiElement psiElement) {
        if (psiElement instanceof PsiNamedElement) {
            PsiNamedElement namedElement = (PsiNamedElement) psiElement;
            return namedElement.getName();
        }
        return "<no-name>";
    }

    public static PsiNamedElement getParentClassifierType(PsiElement element) {
        if (isClassifierType(element) && element instanceof PsiNamedElement) {
            return (PsiNamedElement) element;
        } else if (element.getParent() != null) {
            return getParentClassifierType(element.getParent());
        } else {
            return null;
        }
    }

    public static boolean isClassifierType(@NotNull PsiElement psiElement) {
        ASTNode astNode = psiElement.getNode();
        if (astNode == null) return false;

        String typeName = astNode.getElementType().toString().toLowerCase();
        return typeName.contains("interface") || typeName.contains("class");
    }

    public static boolean isFieldType(PsiElement psiElement) {
        String typeName = psiElement.getNode().getElementType().toString().toLowerCase();
        return typeName.contains("field") || typeName.contains("attribute") || typeName.contains("property");
    }

}
