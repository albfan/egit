/*******************************************************************************
 * Copyright (C) 2010, Mathias Kinzler <mathias.kinzler@sap.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.egit.ui.internal.actions;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.fetch.SvnFetchOperationUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.SvnRemoteConfig;

/**
 * Action for "Simple fetch"
 */
public class SimpleSvnFetchActionHandler extends RepositoryActionHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;
		SvnRemoteConfig config = getConfiguredRemote(repository);
		if (config == null) {
			MessageDialog
					.openInformation(
							getShell(event),
							UIText.SimpleFetchActionHandler_NothingToFetchDialogTitle,
							UIText.SimpleFetchActionHandler_NothingToFetchDialogMessage);
			return null;
		}

		new SvnFetchOperationUI(repository, config, Activator.getDefault().getPreferenceStore()
						.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT), false).start();
		return null;
	}

	@Override
	public boolean isEnabled() {
		final Repository repository = getRepository();
		return repository != null
				&& getConfiguredRemote(repository) != null;
	}

	/**
	 * @param repository
	 * @return the configured remote for the current branch, or the default
	 *         remote; <code>null</code> if a local branch is checked out that
	 *         points to "." as remote
	 */
	public static SvnRemoteConfig getConfiguredRemote(Repository repository) {
		// check if we find the configured and default Remotes
		List<SvnRemoteConfig> allRemotes;
		try {
			allRemotes = SvnRemoteConfig.getAllRemoteConfigs(repository
					.getConfig());
		} catch (URISyntaxException e) {
			allRemotes = new ArrayList<>();
		}

		return allRemotes.isEmpty() ? null : allRemotes.get(0); //TODO: More than one svn-remote
	}

}
