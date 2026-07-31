package com.rohith.cryptotracker.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rohith.cryptotracker.core.database.dao.CoinDao
import com.rohith.cryptotracker.core.database.model.CoinEntity

@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
abstract class CryptoDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
}
