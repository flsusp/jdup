package br.com.jdup;

public interface VersionRepository {

	Version getCurrentVersion();

	void registerNewVersion(Version version);
}
