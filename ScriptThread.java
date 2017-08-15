import jdk.nashorn.api.scripting.NashornScriptEngine;

import java.io.File;

public class ScriptThread extends Thread {

    private final NashornScriptEngine engine;

    public ScriptThread(NashornScriptEngine engine) {
        this.engine = engine;
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
        scriptEnvironment.eval(new File("ScriptLoop.js"));
    }
}
