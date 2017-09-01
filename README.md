# native_memory

This demonstrates an ever growing Internal memory category as reported by NMT (Native Memory Tracking).  This is not cleaned up by a System.gc() call even once all threads and NashornScriptEngine are dereferenced.

Effectively this will spawn X number of java threads in serial, each create a new MyScriptEnvironment to evaluate a ScriptLoop.js javascript file that will loop Y number of times, in each loop ScriptLoop.js will invoke an implementation of JSObject (MyJSObject) that creates a new/separate instance of MyScriptEnvironment.

In the MyJSObject class you can change the 2nd argument passed to the new MyScriptEnvironment constructor to enable/disable evaluation of Class.js.

The ReproducerScriptThread class will print at the very start of execution to run the NMT baseline command and then sleep for 5 seconds before starting.  It will do the same and say to run a summary.diff command just before it exits.  On unix systems, you can use the below commands from a separate terminal than the one running ReproducerScriptThread:

Enable NMT when running:
```
java -XX:NativeMemoryTracking=detail ReproducerScriptThread
```

NMT baseline command:
```
PID=$( ps aux | grep ReproducerScriptThread | grep -v grep | awk '{ print $2 }' ); jcmd $PID VM.native_memory baseline
```
NMT summary.diff command:
```
PID=$( ps aux | grep ReproducerScriptThread | grep -v grep | awk '{ print $2 }' ); jcmd $PID VM.native_memory summary.diff | grep Internal
```
Based on 3 sample runs with 100 and with 500 threads, testing with evalClass set to true and to false where MyJSObject calls new MyScriptEnvironment, it is clear that the more times we create a script environment the more Internal native memory is left behind after dereferencing and doing GC.  It also shows that much more is left behind when evalClass is set to true. 

With evaluation of Class.js and 100 threads:
```
Run #1 - Internal (reserved=10827KB +1232KB, committed=10827KB +1232KB)
Run #2 - Internal (reserved=10824KB +1229KB, committed=10824KB +1229KB)
Run #3 - Internal (reserved=10824KB +1229KB, committed=10824KB +1229KB)
```

Without evaluation of Class.js and 100 threads:
```
Run #1 - Internal (reserved=10353KB +758KB, committed=10353KB +758KB)
Run #2 - Internal (reserved=10353KB +757KB, committed=10353KB +757KB)
Run #3 - Internal (reserved=10353KB +758KB, committed=10353KB +758KB)
```            

With evaluation of Class.js and 500 threads:

```
Run #1 - Internal (reserved=12735KB +3140KB, committed=12735KB +3140KB)
Run #2 - Internal (reserved=12737KB +3141KB, committed=12737KB +3141KB)
Run #3 - Internal (reserved=12734KB +3138KB, committed=12734KB +3138KB)
```

Without evaluation of Class.js and 500 threads:

```
Run #1 - Internal (reserved=10408KB +813KB, committed=10408KB +813KB)
Run #2 - Internal (reserved=10406KB +810KB, committed=10406KB +810KB)
Run #3 - Internal (reserved=10407KB +812KB, committed=10407KB +812KB)
```
