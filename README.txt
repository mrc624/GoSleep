Contributors: Matthew Cloutier & Molly Olsen

Usage of Sensors:
The gyroscope and accelerometer are used to see if the user is awake and using their phone. If so a timestamp is logged for the last time the user was awake
The calendar is also used to notify the user, if they are awake, that they should go to sleep if they have an event coming up.

Instructions:
Ensure both notifications and calendar access is allowed.
To test the APP you must shake the phone as the APP is launching, within the first 5 seconds of launching it will check to see if a notification should be sent.
Sleep hours is the amount of sleep the user would like to get.
Time get ready is the amount of time the user takes to get ready.
Event hours are the hours that contain your first event for the day.
	This is to ensure we are not notifying users if they have an event at 1:00 AM but they don't consider that their first event.
	This also allows flexibility for night workers.

Background Task:
Every 15 minutes a background worker checks if the user should be notified of an upcoming event.
If the user should be notified, a notification is pushed to the user.

Resources:
https://developer.android.com/jetpack/androidx/releases/room
https://developer.android.com/reference/android/provider/CalendarContract
https://developer.android.com/reference/android/app/NotificationManager
https://developer.android.com/reference/kotlin/androidx/work/CoroutineWorker

Github:
https://github.com/mrc624/GoSleep