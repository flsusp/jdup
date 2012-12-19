package br.com.jdup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassloaderUpgradeScriptExplorer extends AbstractUpgradeScriptExplorer implements UpgradeScriptExplorer {

	private Class<?> klass;

	public ClassloaderUpgradeScriptExplorer(Class<?> klass, ScriptFilter filter, VersionExtractor versionExtractor) {
		super(filter, versionExtractor);
		this.klass = klass;
	}

	@Override
	public List<UpgradeScript> searchScriptsNewerThan(Version version) {
		try {
			List<UpgradeScript> list = exploreScriptsFromClasspath(version);
			Collections.sort(list);
			return list;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private List<UpgradeScript> exploreScriptsFromClasspath(Version version) throws IOException {
		CodeSource src = klass.getProtectionDomain().getCodeSource();
		List<UpgradeScript> list = new ArrayList<UpgradeScript>();

		if (src != null) {
			URL jar = src.getLocation();
			File file = new File(jar.getFile());
			if (file.isDirectory()) {
				list.addAll(exploreScriptsFromDirectory(file, version));
			} else {
				InputStream stream = jar.openStream();
				try {
					ZipInputStream zip = new ZipInputStream(stream);
					ZipEntry ze = null;

					while ((ze = zip.getNextEntry()) != null) {
						String entryName = ze.getName();
						// FIXME: Is there another way to access files in a JAR archive?
						File entryFile = new File("../dataaccess/target/classes", entryName);
						if (accept(entryFile)) {
							Version fileVersion = extractVersionFrom(entryFile);
							if (fileVersion.isNewerThan(version)) {
								list.add(new FileUpgradeScript(fileVersion, entryFile));
							}
						}
					}
				} finally {
					stream.close();
				}
			}
		}

		return list;
	}

	private Collection<? extends UpgradeScript> exploreScriptsFromDirectory(File directory, Version version) {
		List<UpgradeScript> list = new ArrayList<UpgradeScript>();

		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				list.addAll(exploreScriptsFromDirectory(file, version));
			} else if (accept(file)) {
				Version fileVersion = extractVersionFrom(file);
				if (fileVersion.isNewerThan(version)) {
					list.add(new FileUpgradeScript(fileVersion, file));
				}
			}
		}

		return list;
	}
}
