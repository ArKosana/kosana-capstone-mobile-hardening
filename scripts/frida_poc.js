Java.perform(function(){
    try {
        var prefs = Java.enumerateClassLoadersSync();
        console.log('[*] enumerated classloaders: ' + prefs.length);
    } catch (e){
        console.log('[!] error enumerating classloaders: ' + e);
    }
    Java.perform(function(){
        try {
            var Context = Java.use('android.content.Context');
            var ActivityThread = Java.use('android.app.ActivityThread');
            var app = ActivityThread.currentApplication();
            var ctx = app.getApplicationContext();
            var sp = ctx.getSharedPreferences('default', 0);
            var keys = sp.getAll().keySet().toArray();
            console.log('[*] SharedPreferences keys: ' + keys);
            for (var i=0;i<keys.length;i++){
                var k = keys[i];
                console.log(k + " => " + sp.getString(k, "NULL"));
            }
        } catch (e){
            console.log('[!] frida exception: ' + e);
        }
    });
});
