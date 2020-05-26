package org.plantuml.idea.overlay.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.plantuml.idea.overlay.service.UsagesVisualizer;
import org.plantuml.idea.rendering.LazyApplicationPoolExecutor;
import org.plantuml.idea.rendering.RenderCommand;
import org.plantuml.idea.toolwindow.PlantUmlToolWindow;
import org.plantuml.idea.util.UIUtils;

public class VisualizeAction extends AnAction  {

    @Override
    public void update(@NotNull final AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(true);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        UsagesVisualizer.addUsages(event);

        final Project project = event.getProject();
        if (project != null) {
            PlantUmlToolWindow plantUmlToolWindow = UIUtils.getPlantUmlToolWindow(project);
            if (plantUmlToolWindow != null) {
                plantUmlToolWindow.renderLater(LazyApplicationPoolExecutor.Delay.NOW, RenderCommand.Reason.REFRESH);
            }
        }
    }
}
