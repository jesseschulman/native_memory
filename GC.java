import jdk.nashorn.api.scripting.AbstractJSObject;

public class GC extends AbstractJSObject {
    @Override
    public Object call(Object thiz, Object... args) {
        System.gc();
        return null;
    }

    @Override
    public boolean isFunction() {
        return true;
    }
}
