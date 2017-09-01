import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptException;
import java.io.IOException;

public class ReproducerScriptThread {

    private static int THREAD_COUNT = 500;
    private static int SCRIPT_LOOP_COUNT = 500;

    public static void main(String[] args) throws IOException, ScriptException, InterruptedException {
        new ReproducerScriptThread().reproduce();
    }

    private void reproduce() throws IOException, ScriptException, InterruptedException {
        System.out.println("Starting reproducer in 5 seconds, run NMT baseline now");
        Thread.sleep(5000);
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        NashornScriptEngine engine = getEngine(factory);
        for (int i = 0; i < THREAD_COUNT; i++) {
            System.out.println("Thread " + i + " starting");
            ScriptThread thread = new ScriptThread(engine, SCRIPT_LOOP_COUNT);
            thread.start();
            thread.join();
            System.out.println("Thread " + (i + 1) + " complete, doing GC");
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
        System.out.println("Program will end in 5 seconds, run NMT summary.diff");
        Thread.sleep(5000);
        System.out.println("END!");
    }


    private NashornScriptEngine getEngine(NashornScriptEngineFactory factory) {
        return (NashornScriptEngine) factory.getScriptEngine(new String[]{"--no-java", "-strict",
                "--no-syntax-extensions", "--language=es6"});
    }

}
