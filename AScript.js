var AScript = Class.create({
    doIt: function() {
        var arr = [];
        var arr2 = [];
        var stuff = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";

        for (var i = 0; i < 1000; i++) {
            arr[i] = i;
            arr2[i] = stuff[Math.floor(Math.random() * stuff.length)];
        }

        function someFn(i) {
            return i * 3 * 8;
        }

        var str = "";

        arr.forEach(function (item){
            someFn(item);
            var strItem = arr2[item];
            if (!str.contains(strItem))
                str += arr2[item];
        });
        return function() { return str; };
    },

    className: "AScript"
});

module.exports = AScript;