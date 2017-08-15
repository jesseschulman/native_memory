# native_memory

This demonstrates an ever growing Internal memory category as reported by NMT (Native Memory Tracking).  This is not cleaned up by a System.gc() call even once all threads and NashornScriptEngine are dereferenced.

Effectively this will spawn X number of java threads in serial, which each create a new MyScriptEnvironment to evaluate a ScriptLoop.js javascript file that will loop Y number of times, in each loop ScriptLoop.js will invoke an implementation of JSObject (MyJSObject) that creates a new/separate instance of MyScriptEnvironment.

In the MyJSObject class you can change the 2nd argument passed to the new MyScriptEnvironment constructor to enable/disable evaluation of Class.js and notice the following differences:

With evaluation of Class.js and 400 threads
