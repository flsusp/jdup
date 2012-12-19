package br.com.jdup;

public interface ScriptExecutor {

	void execute(UpgradeScript script) throws ScriptExecutionException;
}
