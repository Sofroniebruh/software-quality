package com.nhl;

import java.io.IOException;

public abstract class Accessor {
	public static final String DEMO_NAME = "Demonstration presentation";
	public static final String DEFAULT_EXTENSION = ".xml";

	public static Accessor getDemoAccessor() {
		return new DemoPresentation();
	}

	public Accessor() {
	}

	public abstract void loadFile(Presentation p, String fileName) throws IOException;

	public abstract void saveFile(Presentation p, String fileName) throws IOException;
}
