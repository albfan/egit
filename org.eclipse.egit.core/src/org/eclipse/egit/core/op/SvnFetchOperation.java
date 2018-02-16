/*******************************************************************************
 * Copyright (C) 2011, Mathias Kinzler <mathias.kinzler@sap.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.egit.core.op;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.SvnRemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;

/**
 * Used to fetch from another Repository
 */
public class SvnFetchOperation {

	private FetchResult operationResult;

	private CredentialsProvider credentialsProvider;

	/**
	 * Constructs a FetchOperation based on URI and RefSpecs
	 *
	 * @param repository
	 * @param uri
	 * @param refSpecs
	 * @param timeout
	 * @param dryRun
	 *
	 */
	public SvnFetchOperation(Repository repository, URIish uri,
                             List<RefSpec> refSpecs, int timeout, boolean dryRun) {
	}

	/**
	 * Constructs a FetchOperation based on a RemoteConfig
	 *
	 * @param repository
	 * @param config
	 * @param timeout
	 * @param dryRun
	 */
	public SvnFetchOperation(Repository repository, SvnRemoteConfig config,
                             int timeout, boolean dryRun) {
	}

	/**
	 * @param credentialsProvider
	 */
	public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}

	/**
	 * @return the operation's credentials provider
	 */
	public CredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}

	/**
	 * @param monitor
	 * @throws InvocationTargetException
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException {
		if (operationResult != null)
			throw new IllegalStateException(CoreText.OperationAlreadyExecuted);

		// EclipseGitProgressTransformer gitMonitor = new
		// EclipseGitProgressTransformer(
		// monitor);
		ArrayList<String> args = new ArrayList<>();
		args.add("svn"); //$NON-NLS-1$
		args.add("fetch"); //$NON-NLS-1$
		args.add("all"); //$NON-NLS-1$
		FS.detect().newInstance().runInShell("git", //$NON-NLS-1$
				args.toArray(new String[] {}));

//		try (Git git = new Git(repository)) {
//			SvnFetchCommand command;
//			if (rc == null)
//				command = git.fetch().setRemote(uri.toPrivateString())
//						.setRefSpecs(specs);
//			else
//				command = git.fetch().setRemote(rc.getName());
//			command.setCredentialsProvider(credentialsProvider)
//					.setTimeout(timeout).setDryRun(dryRun)
//					.setProgressMonitor(gitMonitor);
//			if (tagOpt != null)
//				command.setTagOpt(tagOpt);
//			try {
//				operationResult = command.call();
//			} catch (JGitInternalException e) {
//				throw new InvocationTargetException(
//						e.getCause() != null ? e.getCause() : e);
//			} catch (Exception e) {
//				throw new InvocationTargetException(e);
//			}
//		}
	}

	/**
	 * @return the result, or <code>null</code> if the operation has not been
	 *         executed
	 */
	public FetchResult getOperationResult() {
		return operationResult;
	}
}
