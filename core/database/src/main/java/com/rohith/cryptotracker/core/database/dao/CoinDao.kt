package com.rohith.cryptotracker.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rohith.cryptotracker.core.database.model.CoinEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object definition for local cached coin operations.
 */
@Dao
interface CoinDao {

    @Query("SELECT * FROM coins ORDER BY marketCapRank ASC")
    fun getCoins(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coins WHERE id = :id")
    fun getCoinById(id: String): Flow<CoinEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<CoinEntity>)

    @Query("DELETE FROM coins")
    suspend fun clearCoins()

    /**
     * Replaces the local database cache with new data as a atomic transaction.
     */
    @Transaction
    suspend fun refreshCoinsCache(coins: List<CoinEntity>) {
        clearCoins()
        insertCoins(coins)
    }
}
