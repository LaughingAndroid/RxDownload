package zlc.season.rxdownload3.core

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import zlc.season.rxdownload3.database.DbActor
import zlc.season.rxdownload3.database.EmptyDbActor
import zlc.season.rxdownload3.http.OkHttpClientFactory
import zlc.season.rxdownload3.http.OkHttpClientFactoryImpl
import zlc.season.rxdownload3.notification.ForeServiceNotificationFactory
import zlc.season.rxdownload3.notification.ForeServiceNotificationFactoryImpl
import zlc.season.rxdownload3.notification.NotificationFactory
import zlc.season.rxdownload3.notification.NotificationFactoryImpl

@SuppressLint("StaticFieldLeak")
object DownloadConfig {
    internal val DEBUG = true

    internal val ANY = Any()

    internal val DOWNLOADING_FILE_SUFFIX = ".download"

    internal val TMP_DIR_SUFFIX = ".TMP"
    internal val TMP_FILE_SUFFIX = ".tmp"

    internal val RANGE_DOWNLOAD_SIZE: Long = 5 * 1024 * 1024  // 5M

    internal var maxRange = 3

    internal var maxMission = 3

    internal var defaultSavePath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).path

    internal var dbActor: DbActor = EmptyDbActor()

    internal var missionBox: MissionBox = LocalMissionBox()

    internal var enableNotification = false

    internal var notificationFactory: NotificationFactory = NotificationFactoryImpl()

    internal var okHttpClientFactory: OkHttpClientFactory = OkHttpClientFactoryImpl()

    internal var enableForegroundService = false

    internal var foreServiceNotificationFactory: ForeServiceNotificationFactory =
            ForeServiceNotificationFactoryImpl()

    internal lateinit var context: Context

    fun init(builder: Builder) {
        this.context = builder.context

        this.maxRange = builder.maxRange
        this.maxMission = builder.maxMission

        this.defaultSavePath = builder.defaultSavePath

        this.dbActor = builder.dbActor

        this.enableNotification = builder.enableNotification
        this.notificationFactory = builder.notificationFactory

        this.okHttpClientFactory = builder.okHttpClientFactory

        val enableService = builder.enableService
        if (enableService) {
            missionBox = RemoteMissionBox()
        }

        this.enableForegroundService = builder.enableForegroundService
        this.foreServiceNotificationFactory = builder.foreServiceNotificationFactory
    }

    class Builder private constructor(val context: Context) {
        internal var maxRange = 3
        internal var maxMission = 3
        internal var defaultSavePath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).path
        internal var enableService = false
        internal var dbActor: DbActor = EmptyDbActor()
        internal var enableNotification = false
        internal var notificationFactory: NotificationFactory = NotificationFactoryImpl()
        internal var okHttpClientFactory: OkHttpClientFactory = OkHttpClientFactoryImpl()
        internal var enableForegroundService = false
        internal var foreServiceNotificationFactory: ForeServiceNotificationFactory =
                ForeServiceNotificationFactoryImpl()

        companion object {
            fun create(context: Context): Builder {
                return Builder(context.applicationContext)
            }
        }

        fun setMaxRange(max: Int): Builder {
            this.maxRange = max
            return this
        }

        fun setMaxMission(max: Int): Builder {
            this.maxMission = max
            return this
        }

        fun enableService(enable: Boolean): Builder {
            this.enableService = enable
            return this
        }

        fun enableNotification(enable: Boolean): Builder {
            this.enableNotification = enable
            return this
        }

        fun setNotificationFactory(notificationFactory: NotificationFactory): Builder {
            this.notificationFactory = notificationFactory
            return this
        }

        fun setDefaultPath(path: String): Builder {
            this.defaultSavePath = path
            return this
        }

        fun setDbActor(dbActor: DbActor): Builder {
            this.dbActor = dbActor
            return this
        }

        fun setOkHttpClientFacotry(okHttpClientFactory: OkHttpClientFactory): Builder {
            this.okHttpClientFactory = okHttpClientFactory
            return this
        }

        fun enableForegroundService(enable: Boolean): Builder {
            this.enableForegroundService = enable
            return this
        }

        fun setForegroundServiceNotificationFactory(foreServiceNotificationFactory:
                                                    ForeServiceNotificationFactory): Builder {
            this.foreServiceNotificationFactory = foreServiceNotificationFactory
            return this
        }
    }
}



