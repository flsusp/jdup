package br.com.jdup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class FileUpgradeScript extends UpgradeScript {

	private File file;

	public FileUpgradeScript(Version version, File file) {
		super(version);
		this.file = file;
	}

	@Override
	public Reader getReader() {
		try {
			return new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public File getFile() {
		return file;
	}
}
