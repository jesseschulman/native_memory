import jdk.nashorn.api.scripting.NashornScriptEngine;

import java.io.File;

public class ScriptThread extends Thread {

    private final NashornScriptEngine engine;
    private final int scriptLoopCount;

    public ScriptThread(NashornScriptEngine engine, int scriptLoopCount) {
        this.engine = engine;
        this.scriptLoopCount = scriptLoopCount;
    }

    @Override
    public void run() {
        /*
        for (int i = 0; i < 500; i++) {
            MyScriptEnvironment scriptEnvironment = new MyScriptEnvironment(engine);
            scriptEnvironment.globalBindings.setMember("counter", i);
            scriptEnvironment.eval("var foo = 'bar'; print('loop: ' + counter);");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
        MyScriptEnvironment scriptEnvironment = new MyScriptEnvironment(engine);
        scriptEnvironment.globalBindings.put("SCRIPT_LOOP_COUNT", this.scriptLoopCount);
        scriptEnvironment.eval(new File("ScriptLoop.js"));
    }
}
