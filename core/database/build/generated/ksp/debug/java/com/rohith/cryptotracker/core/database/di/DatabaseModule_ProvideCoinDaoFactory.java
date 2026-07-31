package com.rohith.cryptotracker.core.database.di;

import com.rohith.cryptotracker.core.database.CryptoDatabase;
import com.rohith.cryptotracker.core.database.dao.CoinDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DatabaseModule_ProvideCoinDaoFactory implements Factory<CoinDao> {
  private final Provider<CryptoDatabase> dbProvider;

  public DatabaseModule_ProvideCoinDaoFactory(Provider<CryptoDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CoinDao get() {
    return provideCoinDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCoinDaoFactory create(Provider<CryptoDatabase> dbProvider) {
    return new DatabaseModule_ProvideCoinDaoFactory(dbProvider);
  }

  public static CoinDao provideCoinDao(CryptoDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCoinDao(db));
  }
}
