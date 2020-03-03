# cordova-plugin-foreground-service-x
 Foreground service with notification in progress for AndroidX

Plugin for the [Cordova][cordova] framework to perform infinite background execution.

## Supported Platforms
- __Android

## Installation
The plugin can be installed via [Cordova-CLI][CLI]

Execute from the projects root folder:

    $ cordova plugin add https://github.com/rafaelgalle/cordova-plugin-foreground-service-x.git

## Usage
The plugin creates the object `cordova.plugins.foregroundService` and is accessible after the *deviceready* event has been fired.

```js
document.addEventListener('deviceready', function () {
    // cordova.plugins.foregroundService is now available
}, false);
```








### Notification
To indicate that the app is executing tasks in background and being paused would disrupt the user, the plug-in has to create a notification while in background - like a download progress bar.

#### Override defaults
The title, text and icon for that notification can be customized as below. Also, by default the app will come to foreground when tapping on the notification. That can be changed by setting resume to false. On Android 5.0+, the color option will set the background color of the notification circle. Also on Android 5.0+, setting hidden to false will make the notification visible on lockscreen.

```js
cordova.plugins.foregroundService.start(
    sucess,				
    error,
    title,
    text,
    icon,
    importance,
    notificationId,
    idFrete,
    latitude,
    longitude,
    tempo_captura,
    tempo_envio,
    distancia_captura,
    radius,
    url,
    token
)
#cordova.plugins.foregroundService.start({
#    sucess: Function,				
#    error: Function,
#    title: String,
#    text: String,
#    icon: String,
#    importance: Int,
#    notificationId: Int,
#    idFrete: Int,
#    latitude: String,
#    longitude: String,
#    tempo_captura: Int,
#    tempo_envio: Int,
#    distancia_captura: Int,
#    radius: Int,
#    url: String,
#    token: String
#})
```

To disable the background mode:
```js
cordova.plugins.foregroundService.stop();
```




### Events
Inserir eventos

```js
cordova.plugins.foregroundService.insertEvent(
    sucess,
    error,
    id_frete,
    event,
    value
);
```

Consultar eventos

```js
cordova.plugins.foregroundService.getEvents(
    sucess,
    error,
    id_frete
);
```

### Locations

Consultar localizações
```js
cordova.plugins.foregroundService.getLocations(
    success,
    error
);
```

Marcar manualmente localizações como sincronizadas
```js
cordova.plugins.foregroundService.updateSyncLocations(
    success,
    error,
    id_frete
);
```

Deletar localizações sincronizadas
```js
cordova.plugins.foregroundService.deleteSyncLocations(
    success,
    error,
    id_frete
);
```

Sincronizar localizações
```js
cordova.plugins.foregroundService.sendLocations(
    success,
    error,
    id_frete
);
```

### Unlock and wake-up
A wake-up turns on the screen while unlocking moves the app to foreground even the device is locked.

Turn screen on and show app even locked
```js
cordova.plugins.backgroundMode.unlock();
```


## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
