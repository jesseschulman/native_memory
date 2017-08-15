import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptException;
import java.io.IOException;

public class ReproducerScriptThread {

    public static void main(String[] args) throws IOException, ScriptException, InterruptedException {
        new ReproducerScriptThread().reproduce();
    }

    private void reproduce() throws IOException, ScriptException, InterruptedException {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        NashornScriptEngine engine = getEngine(factory);
        for (int i = 0; i < 100; i++) {
            System.out.println("Thread " + i + " starting");
            ScriptThread thread = new ScriptThread(engine);
            thread.start();
            thread.join();
            System.out.println("Thread " + i + " complete, doing GC");
            System.gc();
        }
        System.out.println("All done with threads!");
        System.out.println("Making engine go away");
        engine = null;
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);
        System.gc();
        System.out.println("All gone!");
        Thread.sleep(6000);
        System.out.println("END!");
    }


    private NashornScriptEngine getEngine(NashornScriptEngineFactory factory) {
        return (NashornScriptEngine) factory.getScriptEngine(new String[]{"--no-java", "-strict",
                "--no-syntax-extensions", "--language=es6"});
    }

}
