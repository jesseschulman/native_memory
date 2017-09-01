var counter = 0;
while (true) {
    print("loop: " + ++counter)
    if (counter % SCRIPT_LOOP_COUNT === 0) {
        //print("DOING GC");
        //GC();
        //print("DONE DOING GC");
        break;
    }
    var myJsObj = new MyJSObject();
    myJsObj.otherEnvironmentWork();
    //sleep(100);
}