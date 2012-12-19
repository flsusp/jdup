package br.com.jdup;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseUpgrader {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseUpgrader.class);

	private UpgradeScriptExplorer explorer;
	private VersionRepository versionRepository;
	private ScriptExecutor scriptExecutor;

	public DatabaseUpgrader(UpgradeScriptExplorer explorer, VersionRepository versionRepository,
			ScriptExecutor scriptExecutor) {
		super();
		this.explorer = explorer;
		this.versionRepository = versionRepository;
		this.scriptExecutor = scriptExecutor;
	}

	public boolean shouldUpgrade() {
		Version version = versionRepository.getCurrentVersion();

		List<UpgradeScript> scripts = explorer.searchScriptsNewerThan(version);

		if (scripts.isEmpty()) {
			logger.info("Nothing to upgrade.");
			return true;
		} else {
			for (UpgradeScript upgradeScript : scripts) {
				logger.info("Should upgrade to {}.", upgradeScript);
			}
			return false;
		}
	}

	public boolean doUpgrade() {
		Version version = versionRepository.getCurrentVersion();

		List<UpgradeScript> scripts = explorer.searchScriptsNewerThan(version);

		if (scripts.isEmpty()) {
			logger.info("Nothing to upgrade.");
			return true;
		} else {
			for (UpgradeScript script : scripts) {
				logger.info("Upgrading to {}.", script);
				try {
					scriptExecutor.execute(script);
					versionRepository.registerNewVersion(script.getVersion());
				} catch (ScriptExecutionException e) {
					logger.error("Error upgrading to {}.", script);
					logger.error("Exception thrown.", e);
					return false;
				}
			}
			return true;
		}
	}
}
