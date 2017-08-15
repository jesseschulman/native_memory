import jdk.nashorn.api.scripting.AbstractJSObject;

public class Sleep extends AbstractJSObject {

    @Override
    public Object call(Object thiz, Object... args) {
        if (args.length < 1)
            return null;
        Object time = args[0];
        try {
            if (time instanceof Double)
                Thread.sleep(((Double) time).longValue());
            else if (time instanceof Long)
                Thread.sleep((Long)time);
            else if (time instanceof Integer)
                Thread.sleep((Integer)time);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isFunction() {
        return true;
    }
}
