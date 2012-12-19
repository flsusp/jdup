package br.com.jdup;

public interface Version extends Comparable<Version> {

	boolean isNewerThan(Version version);

	String toString();

	Integer getHash();
}
