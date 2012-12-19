package br.com.jdup;

import java.io.Reader;

public abstract class UpgradeScript implements Comparable<UpgradeScript> {

	private Version version;

	public UpgradeScript(Version version) {
		super();
		this.version = version;
	}

	public Version getVersion() {
		return version;
	}

	public abstract Reader getReader();

	@Override
	public int compareTo(UpgradeScript o) {
		return this.version.compareTo(o.version);
	}

	@Override
	public String toString() {
		return version.toString();
	}
}
