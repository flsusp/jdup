package br.com.jdup;

import java.util.List;

public interface UpgradeScriptExplorer {

	List<UpgradeScript> searchScriptsNewerThan(Version version);
}
