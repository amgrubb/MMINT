/**
 * Copyright (c) 2012-2014 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.mmtf.repository;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;

import edu.toronto.cs.se.mmtf.MMTF;
import edu.toronto.cs.se.mmtf.MMTFException;
import edu.toronto.cs.se.mmtf.MultiModelTypeRegistry;
import edu.toronto.cs.se.mmtf.mid.relationship.ModelRel;

/**
 * A listener for dynamic installation/unistallation of extensions to the
 * Relationships extension point.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class RelationshipsExtensionListener extends MMTFExtensionListener {

	/**
	 * {@inheritDoc}
	 * Installs a new Relationships extension.
	 */
	@Override
	public void added(IExtension[] extensions) {

		IConfigurationElement[] config;
		for (IExtension extension : extensions) {
			config = extension.getConfigurationElements();
			for (IConfigurationElement elem : config) {
				MMTF.createModelRelType(elem);
			}
		}
		MMTF.storeRepository();
	}

	/**
	 * {@inheritDoc}
	 * Uninstalls a Relationships extension.
	 */
	@Override
	public void removed(IExtension[] extensions) {

		IConfigurationElement[] config;
		for (IExtension extension : extensions) {
			config = extension.getConfigurationElements();
			for (IConfigurationElement elem : config) {
				String uri = elem.getAttribute(MMTF.TYPE_ATTR_URI);
				ModelRel modelRelType = MultiModelTypeRegistry.getType(uri);
				if (modelRelType != null) {
					try {
						modelRelType.deleteType();
					}
					catch (MMTFException e) {
						// never happens by construction
					}
				}
			}
		}
		MMTF.storeRepository();
	}

}
