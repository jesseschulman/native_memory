import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.Undefined;

import java.io.File;

public class MyJSObject extends AbstractJSObject {

    private final MyScriptEnvironment environment;

    public MyJSObject(MyScriptEnvironment env, Object[] args) {
        this.environment = env;
    }

    @Override
    public Object getMember(String name) {
        if (name.equals("getComplexObject")) {
            return new AbstractJSObject() {
                @Override
                public Object call(Object thiz, Object... args) {
                    MapConverter converter = new MapConverter(MyJSObject.this.environment.globalBindings);
                    return converter.getScriptObject();
                }

                @Override
                public boolean isFunction() {
                    return true;
                }
            };
        }

        if (name.equals("otherEnvironmentWork")) {
            return new AbstractJSObject() {
                @Override
                public Object call(Object thiz, Object... args) {
                    MyScriptEnvironment scriptEnvironment = new MyScriptEnvironment(MyJSObject.this.environment.engine, false, true, true, true);
                    //ScriptObjectMirror aScriptClass = scriptEnvironment.require(new File("AScript.js"));
                    //ScriptObjectMirror aScript = (ScriptObjectMirror) aScriptClass.newObject();
                    //return aScript.callMember("doIt");
                    return Undefined.getUndefined();
                }

                @Override
                public boolean isFunction() {
                    return true;
                }
            };
        }

        return null;
    }
}
