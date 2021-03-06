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
package com.intellij.ide.actions;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Philipp Smorygo
 */
public interface SearchEverywhereClassifier {
  class EP_Manager {
    private EP_Manager() {}

    public static boolean isClass(@Nullable Object o) {
      for (SearchEverywhereClassifier classifier : Extensions.getExtensions(SearchEverywhereClassifier.EP_NAME)) {
        if (classifier.isClass(o)) return true;
      }
      return false;
    }

    public static boolean isSymbol(@Nullable Object o) {
      for (SearchEverywhereClassifier classifier : Extensions.getExtensions(SearchEverywhereClassifier.EP_NAME)) {
        if (classifier.isSymbol(o)) return true;
      }
      return false;
    }

    @Nullable
    public static VirtualFile getVirtualFile(@NotNull Object o) {
      for (SearchEverywhereClassifier classifier : Extensions.getExtensions(SearchEverywhereClassifier.EP_NAME)) {
        VirtualFile virtualFile = classifier.getVirtualFile(o);
        if (virtualFile != null) return virtualFile;
      }
      return null;
    }
  }

  ExtensionPointName<SearchEverywhereClassifier> EP_NAME = ExtensionPointName.create("com.intellij.searchEverywhereClassifier");

  boolean isClass(@Nullable Object o);

  boolean isSymbol(@Nullable Object o);

  @Nullable
  VirtualFile getVirtualFile(@NotNull Object o);
}
