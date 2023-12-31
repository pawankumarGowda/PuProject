package cargill.com.purina.splash.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "country")
data class Country (

    @PrimaryKey
    @ColumnInfo(name = "country_id")
    val id:Int,

    @ColumnInfo(name = "country_flag")
    val flag: String? = null,

    @ColumnInfo(name = "country_language")
    val language: String? = "",

    @ColumnInfo(name = "country_code")
    val languageCode: String? = "",

    @ColumnInfo(name = "country_user_status")
    val status: Int? = 0,

    @SerializedName("mode_active")
    var modeActive : Boolean? = false


)