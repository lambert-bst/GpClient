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
package de.onyxbits.weave.util;

/**
 * Marks an object that contains volatile data of significant importance that
 * should not be discarded without user confirmation.
 * 
 * @author patrick
 * 
 */
public interface ContentHolder {

	/**
	 * Check if there is unsaved content.
	 * 
	 * @return true if the object should not be disposed of without confirmation.
	 */
	public boolean isDirty();

	/**
	 * Attempt/perform cleanup without user interaction.
	 */
	public void autoClean();
}
