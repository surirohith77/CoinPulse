package com.rohith.cryptotracker.data;

import com.rohith.cryptotracker.core.database.dao.CoinDao;
import com.rohith.cryptotracker.core.network.datasource.NetworkDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class CoinRepositoryImpl_Factory implements Factory<CoinRepositoryImpl> {
  private final Provider<NetworkDataSource> networkSourceProvider;

  private final Provider<CoinDao> coinDaoProvider;

  public CoinRepositoryImpl_Factory(Provider<NetworkDataSource> networkSourceProvider,
      Provider<CoinDao> coinDaoProvider) {
    this.networkSourceProvider = networkSourceProvider;
    this.coinDaoProvider = coinDaoProvider;
  }

  @Override
  public CoinRepositoryImpl get() {
    return newInstance(networkSourceProvider.get(), coinDaoProvider.get());
  }

  public static CoinRepositoryImpl_Factory create(Provider<NetworkDataSource> networkSourceProvider,
      Provider<CoinDao> coinDaoProvider) {
    return new CoinRepositoryImpl_Factory(networkSourceProvider, coinDaoProvider);
  }

  public static CoinRepositoryImpl newInstance(NetworkDataSource networkSource, CoinDao coinDao) {
    return new CoinRepositoryImpl(networkSource, coinDao);
  }
}
