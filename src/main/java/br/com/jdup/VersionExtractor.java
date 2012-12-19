package br.com.jdup;

import java.io.File;

public interface VersionExtractor {

	Version extractVersionFrom(File file);
}
