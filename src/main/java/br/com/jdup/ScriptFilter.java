package br.com.jdup;

import java.io.File;

public interface ScriptFilter {

	boolean accept(File file);
}
