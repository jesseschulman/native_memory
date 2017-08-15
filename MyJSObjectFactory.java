import jdk.nashorn.api.scripting.AbstractJSObject;

public class MyJSObjectFactory extends AbstractJSObject {

    private final MyScriptEnvironment environment;

    public MyJSObjectFactory(MyScriptEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public Object newObject(Object ... args) {
        return new MyJSObject(this.environment, args);
    }

}
