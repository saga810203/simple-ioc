package com.star.common;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineManagerTest {
	public static void main(String[] args) throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
// manager.getEngineByName("Mozilla Rhino");
		ScriptEngine engine =manager.getEngineByExtension("js");
		engine.put("money", 12.3d);
		engine.eval("var r; if(money>3)r=money*0.8; var c=r;");
		Object c =engine.get("c");
		System.out.println(c);

	}
}
