package org.plantuml.idea.overlay.util;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.EditorColorsUtil;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiElement;

import java.awt.*;

import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.CLASS_NAME;
import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.IDENTIFIER;
import static org.plantuml.idea.overlay.util.PsiUtil.isClassifierType;
import static org.plantuml.idea.overlay.util.PsiUtil.isFieldType;

public class ColorUtil {

    private ColorUtil() {
    }

    public static Color getNodeLabelTextColor(PsiElement psiElement) {
        if (isClassifierType(psiElement)) {
            return getKeywordTextColor();
        } else if (isFieldType(psiElement)) {
            return getDefaultTextColor();
        } else {
            return getDefaultTextColor();
        }
    }

    public static Color getEdgeLineColor() {
        return IDENTIFIER.getDefaultAttributes().getForegroundColor();
    }

    public static Color getNodeLineColor() {
        return IDENTIFIER.getDefaultAttributes().getForegroundColor();
    }

    public static Color getKeywordTextColor() {
        TextAttributes classNameAttributes = getDefaultClassNameAttributes();
        if (classNameAttributes != null && classNameAttributes.getForegroundColor() != null) {
            return classNameAttributes.getForegroundColor();
        } else {
            return getDefaultIdentifierAttributes().getForegroundColor();
        }
    }

    public static TextAttributes getDefaultClassNameAttributes() {
        return EditorColorsUtil.getGlobalOrDefaultColorScheme().getAttributes(
                TextAttributesKey.createTextAttributesKey(CLASS_NAME.getExternalName()));
    }

    public static TextAttributes getDefaultIdentifierAttributes() {
        return EditorColorsUtil.getGlobalOrDefaultColorScheme().getAttributes(
                TextAttributesKey.createTextAttributesKey(IDENTIFIER.getExternalName()));
    }

    public static Color getDefaultTextColor() {
        return IDENTIFIER.getDefaultAttributes().getForegroundColor();
    }

    public static Color getNodeFillColor() {
        return DefaultLanguageHighlighterColors.KEYWORD.getDefaultAttributes().getBackgroundColor();
    }

}
