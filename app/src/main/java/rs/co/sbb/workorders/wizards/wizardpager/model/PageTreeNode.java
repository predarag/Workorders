/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rs.co.sbb.workorders.wizards.wizardpager.model;

import java.util.ArrayList;

/**
 * Represents a node in the page tree. Can either be a single page, or a page container.
 */
public interface PageTreeNode {
    Page findByKey(String key);
    void flattenCurrentPageSequence(ArrayList<Page> dest);
}
