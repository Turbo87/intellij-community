/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.java.decompiler;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.Gray;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

abstract class LegalNoticeDialog extends DialogWrapper {
  private JEditorPane myMessage;

  public LegalNoticeDialog(Project project) {
    super(project);
    setTitle(IdeaDecompilerBundle.message("legal.notice.title"));
    setOKButtonText(IdeaDecompilerBundle.message("legal.notice.action.accept"));
    setCancelButtonText(IdeaDecompilerBundle.message("legal.notice.action.postpone"));
    init();
    pack();
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    JPanel iconPanel = new JBPanel(new BorderLayout());
    iconPanel.add(new JBLabel(AllIcons.General.WarningDialog), BorderLayout.NORTH);

    myMessage = new JEditorPane();
    myMessage.setEditorKit(UIUtil.getHTMLEditorKit());
    myMessage.setEditable(false);
    myMessage.setPreferredSize(JBUI.size(500, 100));
    myMessage.setBorder(BorderFactory.createLineBorder(Gray._200));
    String text = "<div style='margin:5px;'>" + IdeaDecompilerBundle.message("legal.notice.text") + "</div>";
    myMessage.setText(text);

    JPanel panel = new JBPanel(new BorderLayout(10, 0));
    panel.add(iconPanel, BorderLayout.WEST);
    panel.add(myMessage, BorderLayout.CENTER);
    return panel;
  }

  @NotNull
  @Override
  protected Action[] createActions() {
    return new Action[]{getOKAction(), new DeclineAction(), getCancelAction()};
  }

  @Nullable
  @Override
  public JComponent getPreferredFocusedComponent() {
    return myMessage;
  }

  @Override
  protected void doOKAction() {
    super.doOKAction();
    accepted();
  }

  @Override
  public void doCancelAction() {
    super.doCancelAction();
    canceled();
  }

  private class DeclineAction extends DialogWrapperAction {
    protected DeclineAction() {
      super(IdeaDecompilerBundle.message("legal.notice.action.reject"));
    }

    @Override
    protected void doAction(ActionEvent e) {
      doCancelAction();
      declined();
    }
  }

  protected abstract void accepted();
  protected abstract void declined();
  protected abstract void canceled();
}