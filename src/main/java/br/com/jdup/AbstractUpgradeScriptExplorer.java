package br.com.jdup;

import java.io.File;

public abstract class AbstractUpgradeScriptExplorer implements UpgradeScriptExplorer {

	private ScriptFilter filter;
	private VersionExtractor versionExtractor;

	public AbstractUpgradeScriptExplorer(ScriptFilter filter, VersionExtractor versionExtractor) {
		super();
		this.filter = filter;
		this.versionExtractor = versionExtractor;
	}

	protected Version extractVersionFrom(File file) {
		return versionExtractor.extractVersionFrom(file);
	}

	protected boolean accept(File file) {
		return filter.accept(file);
	}
}
