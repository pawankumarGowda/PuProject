package cargill.com.purina.utils
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat




object PermissionCheck {
  fun readAndWriteExternalStorage(context: Context?): Boolean {
    return if (ActivityCompat.checkSelfPermission(
        context!!,
        Manifest.permission.READ_EXTERNAL_STORAGE
      ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
        context, Manifest.permission.WRITE_EXTERNAL_STORAGE
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        (context as Activity?)!!,
        arrayOf(
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.WRITE_EXTERNAL_STORAGE
        ),
        1
      )
      false
    } else {
      true
    }
  }
  fun readAndWriteCalender(context: Context?): Boolean {
    return if (ActivityCompat.checkSelfPermission(
        context!!,
        Manifest.permission.READ_CALENDAR
      ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
        context, Manifest.permission.WRITE_CALENDAR
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        (context as Activity?)!!,
        arrayOf(
          Manifest.permission.READ_CALENDAR,
          Manifest.permission.WRITE_CALENDAR
        ),
        1
      )
      false
    } else {
      true
    }
  }

  fun audioRecord(context: Context?): Boolean {
    return if (ActivityCompat.checkSelfPermission(
        context!!,
        Manifest.permission.RECORD_AUDIO
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        (context as Activity?)!!,
        arrayOf(Manifest.permission.RECORD_AUDIO),
        2
      )
      false
    } else {
      true
    }
  }

  fun readAndWriteContacts(context: Context?): Boolean {
    return if (ActivityCompat.checkSelfPermission(
        context!!,
        Manifest.permission.READ_CONTACTS
      ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
        context, Manifest.permission.WRITE_CONTACTS
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        (context as Activity?)!!,
        arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS),
        3
      )
      false
    } else {
      true
    }
  }

  fun vibrate(context: Context?): Boolean {
    return if (ActivityCompat.checkSelfPermission(
        context!!,
        Manifest.permission.VIBRATE
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        (context as Activity?)!!,
        arrayOf(Manifest.permission.VIBRATE),
        4
      )
      false
    } else {
      true
    }
  }

  fun sendSms(context: Context?): Boolean {
    return if (ActivityCompat.checkSelfPermission(
        context!!,
        Manifest.permission.SEND_SMS
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        (context as Activity?)!!,
        arrayOf(Manifest.permission.SEND_SMS),
        5
      )
      false
    } else {
      true
    }
  }

  fun accessFineLocation(context: Context?): Boolean {
    return if (ActivityCompat.checkSelfPermission(
        context!!,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
        (context as Activity?)!!,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
        6
      )
      false
    } else {
      true
    }
  }
}