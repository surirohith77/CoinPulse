package com.rohith.cryptotracker.core.database.di

import android.content.Context
import androidx.room.Room
import com.rohith.cryptotracker.core.database.CryptoDatabase
import com.rohith.cryptotracker.core.database.dao.CoinDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCryptoDatabase(
        @ApplicationContext context: Context
    ): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            "crypto_tracker.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoinDao(db: CryptoDatabase): CoinDao {
        return db.coinDao
    }
}
