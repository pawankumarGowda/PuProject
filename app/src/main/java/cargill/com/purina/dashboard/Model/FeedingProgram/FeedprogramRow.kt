package cargill.com.purina.dashboard.Model.FeedingProgram

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stages")
data class FeedprogramRow(
    @ColumnInfo(name = "age_days")
    val age_days: Int,
    @ColumnInfo(name = "comments")
    val comments: String,
    @ColumnInfo(name = "expected_wt")
    val expected_wt: Double,
    @ColumnInfo(name = "feed_norms")
    val feed_norms: Double,
    @ColumnInfo(name = "feedprogram_id")
    val feedprogram_id: Int,
    @ColumnInfo(name = "image_url")
    val image_url: String,
    @ColumnInfo(name = "inclusion_rate")
    val inclusion_rate: Int,
    @ColumnInfo(name = "mortality_rate")
    val mortality_rate: Double,
    @ColumnInfo(name = "recipe_code")
    val recipe_code: String,
    @ColumnInfo(name = "recipe_name")
    val recipe_name: String,
    @PrimaryKey
    @ColumnInfo(name = "stage_no")
    val stage_no: Int,

    @ColumnInfo(name = "feed_required")
    var feed_required: Int,
    @ColumnInfo(name = "additional_feed")
    var additional_feed: Int,
    @ColumnInfo(name = "bag_price")
    var bag_price: Int,
    @ColumnInfo(name = "feed_cost")
    var feed_cost: Int,
    @ColumnInfo(name = "accumulated_cost_kg")
    var accumulated_cost_kg: Int,
    @ColumnInfo(name = "accumulated_cost_head")
    var accumulated_cost_head: Int,
    @ColumnInfo(name = "completed_feed_equivalent")
    var completed_feed_equivalent: Int,
    @ColumnInfo(name = "bookmark")
    var bookmark: Boolean
)