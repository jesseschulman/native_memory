import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MyScriptEnvironment {

    public final NashornScriptEngine engine;
    public final ScriptObjectMirror globalBindings;
    private boolean evalClass;
    private boolean createSleep;
    private boolean createMyJsObject;
    private boolean createGc;

    public MyScriptEnvironment(NashornScriptEngine engine) {
        this(engine, true, true, true, true);
    }

    public MyScriptEnvironment(NashornScriptEngine engine, boolean evalClass, boolean createSleep, boolean createMyJsObject, boolean createGc) {
        this.engine = engine;
        this.globalBindings = (ScriptObjectMirror) engine.createBindings();
        this.evalClass = evalClass;
        this.createSleep = createSleep;
        this.createMyJsObject = createMyJsObject;
        this.createGc = createGc;
        setup();
    }

    public void eval(File f) {
        try {
            eval(fileToString(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eval(String script) {
        try {
            this.engine.eval(script, globalBindings);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        try {
            if (evalClass)
                eval(fileToString(new File("Class.js")));

            if (createSleep)
                globalBindings.put("sleep", new Sleep());

            if (createMyJsObject)
                globalBindings.put("MyJSObject", new MyJSObjectFactory(this));

            if (createGc)
                globalBindings.put("GC", new GC());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileToString(File f) throws IOException {
        if (!f.exists())
            throw new RuntimeException("File does not exist " + f);

        StringBuilder sb = new StringBuilder();
        for (String s : Files.readAllLines(f.toPath())) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public ScriptObjectMirror require(File file) {
        try {
            ScriptObjectMirror moduleObj = createGlobalObject("Object");
            ScriptObjectMirror functionObj = createGlobalObject("Function", "module", fileToString(file));
            functionObj.call(functionObj, moduleObj);
            if (moduleObj.hasMember("exports"))
                return (ScriptObjectMirror) moduleObj.getMember("exports");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ScriptObjectMirror createGlobalObject(String objName, Object ... args) {
        return (ScriptObjectMirror) ((ScriptObjectMirror) globalBindings.getMember(objName)).newObject(args);
    }

}
