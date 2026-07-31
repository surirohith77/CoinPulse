package com.rohith.cryptotracker.feature.tracker;

import com.rohith.cryptotracker.core.model.CoinRepository;
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
public final class TrackerViewModel_Factory implements Factory<TrackerViewModel> {
  private final Provider<CoinRepository> repositoryProvider;

  public TrackerViewModel_Factory(Provider<CoinRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public TrackerViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static TrackerViewModel_Factory create(Provider<CoinRepository> repositoryProvider) {
    return new TrackerViewModel_Factory(repositoryProvider);
  }

  public static TrackerViewModel newInstance(CoinRepository repository) {
    return new TrackerViewModel(repository);
  }
}
