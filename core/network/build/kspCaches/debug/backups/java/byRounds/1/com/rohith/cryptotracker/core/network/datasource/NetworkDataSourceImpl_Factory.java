package com.rohith.cryptotracker.core.network.datasource;

import com.rohith.cryptotracker.core.network.api.CoinGeckoApi;
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
public final class NetworkDataSourceImpl_Factory implements Factory<NetworkDataSourceImpl> {
  private final Provider<CoinGeckoApi> apiProvider;

  public NetworkDataSourceImpl_Factory(Provider<CoinGeckoApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public NetworkDataSourceImpl get() {
    return newInstance(apiProvider.get());
  }

  public static NetworkDataSourceImpl_Factory create(Provider<CoinGeckoApi> apiProvider) {
    return new NetworkDataSourceImpl_Factory(apiProvider);
  }

  public static NetworkDataSourceImpl newInstance(CoinGeckoApi api) {
    return new NetworkDataSourceImpl(api);
  }
}
