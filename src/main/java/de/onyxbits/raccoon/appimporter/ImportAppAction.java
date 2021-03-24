/*
 * Copyright 2015 Patrick Ahlbrecht
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.onyxbits.raccoon.appimporter;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import de.onyxbits.raccoon.repo.AndroidApp;

import de.onyxbits.weave.Globals;
import de.onyxbits.weave.LifecycleManager;

/**
 * An action for importing {@link AndroidApp}s from the filesystem.
 * 
 * @author patrick
 * 
 */
public class ImportAppAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Globals globals;

	public ImportAppAction(Globals globals) {
		this.globals = globals;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		globals.get(LifecycleManager.class).getWindow(ImportAppBuilder.ID)
				.setVisible(true);
	}
}
