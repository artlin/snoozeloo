# snoozeloo

```di (koin)

core
--domain
  |-- Application.kt
  |-- BaseActivity.kt (core android )
--data
-- ui (reusable compose components)

feature [nazwa featura]
--domain (logika budzika, alarmów)
--data
--ui (per feature composables)

manager (crud on alarms) ->
-- (UI for crud on alarm metadata)
-- data (alarm metadata -> DB)
-- domain (crud on actual alarm event via alarm manager( operation on new or existing alarm ))

feature (alarm screen / notification)
-- UI( ui for notification)
-- data ()
-- domain ( dismiss and snooze logic via alarm manager )
```
