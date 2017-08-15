var counter = 0;
while (true) {
    print("loop: " + ++counter)
    if (counter % 500 === 0) {
        //print("DOING GC");
        //GC();
        //print("DONE DOING GC");
        break;
    }
    var myJsObj = new MyJSObject();
    myJsObj.otherEnvironmentWork();
    //sleep(100);
}