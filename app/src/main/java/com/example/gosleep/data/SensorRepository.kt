package com.example.gosleep.data

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.sqrt

class SensorRepository(
    private val sensorManager: SensorManager
) {
    fun detectPhoneUsage() = callbackFlow {
        var lastEmitTime = 0L

        val accelerometer =
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val gyroscope =
            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        // 🚨 If either sensor is missing, close flow safely
        if (accelerometer == null || gyroscope == null) {
            close()
            Log.d("GoSleep: SensorRepository", "Accelerometer or Gyroscope not found")
            return@callbackFlow
        }

        val listener = object : SensorEventListener {

            var lastAccel = 0f
            var lastGyro = 0f

            override fun onSensorChanged(event: SensorEvent?) {
                event ?: return

                when (event.sensor.type) {

                    Sensor.TYPE_ACCELEROMETER -> {
                        val x = event.values[0]
                        val y = event.values[1]
                        val z = event.values[2]
                        val gForce =
                            sqrt(x*x + y*y + z*z) / SensorManager.GRAVITY_EARTH
                        lastAccel = gForce
                    }

                    Sensor.TYPE_GYROSCOPE -> {
                        val rx = event.values[0]
                        val ry = event.values[1]
                        val rz = event.values[2]
                        val rotation = sqrt(rx*rx + ry*ry + rz*rz)
                        lastGyro = rotation
                    }
                }

                val accelTriggered = lastAccel > 1.15f
                val gyroTriggered = lastGyro > 1.0f

                if (accelTriggered && gyroTriggered) {
                    val now = System.currentTimeMillis()

                    if (now - lastEmitTime > 60000) {
                        trySend(now)
                        lastEmitTime = now
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            listener,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )

        sensorManager.registerListener(
            listener,
            gyroscope,
            SensorManager.SENSOR_DELAY_UI
        )

        awaitClose { sensorManager.unregisterListener(listener) }
    }
}