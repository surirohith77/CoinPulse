package com.rohith.cryptotracker.core.network.di;

import com.rohith.cryptotracker.core.network.api.CoinGeckoApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_Companion_ProvideCoinGeckoApiFactory implements Factory<CoinGeckoApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_Companion_ProvideCoinGeckoApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public CoinGeckoApi get() {
    return provideCoinGeckoApi(retrofitProvider.get());
  }

  public static NetworkModule_Companion_ProvideCoinGeckoApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_Companion_ProvideCoinGeckoApiFactory(retrofitProvider);
  }

  public static CoinGeckoApi provideCoinGeckoApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.Companion.provideCoinGeckoApi(retrofit));
  }
}
