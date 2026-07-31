package com.rohith.cryptotracker.core.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.rohith.cryptotracker.core.database.model.CoinEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CoinDao_Impl implements CoinDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CoinEntity> __insertionAdapterOfCoinEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearCoins;

  public CoinDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCoinEntity = new EntityInsertionAdapter<CoinEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `coins` (`id`,`symbol`,`name`,`image`,`currentPrice`,`marketCap`,`marketCapRank`,`priceChangePercent24h`,`lastUpdated`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CoinEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSymbol());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getImage());
        statement.bindDouble(5, entity.getCurrentPrice());
        statement.bindDouble(6, entity.getMarketCap());
        statement.bindLong(7, entity.getMarketCapRank());
        statement.bindDouble(8, entity.getPriceChangePercent24h());
        statement.bindString(9, entity.getLastUpdated());
      }
    };
    this.__preparedStmtOfClearCoins = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM coins";
        return _query;
      }
    };
  }

  @Override
  public Object insertCoins(final List<CoinEntity> coins,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCoinEntity.insert(coins);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object refreshCoinsCache(final List<CoinEntity> coins,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> CoinDao.DefaultImpls.refreshCoinsCache(CoinDao_Impl.this, coins, __cont), $completion);
  }

  @Override
  public Object clearCoins(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearCoins.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearCoins.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CoinEntity>> getCoins() {
    final String _sql = "SELECT * FROM coins ORDER BY marketCapRank ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"coins"}, new Callable<List<CoinEntity>>() {
      @Override
      @NonNull
      public List<CoinEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfCurrentPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPrice");
          final int _cursorIndexOfMarketCap = CursorUtil.getColumnIndexOrThrow(_cursor, "marketCap");
          final int _cursorIndexOfMarketCapRank = CursorUtil.getColumnIndexOrThrow(_cursor, "marketCapRank");
          final int _cursorIndexOfPriceChangePercent24h = CursorUtil.getColumnIndexOrThrow(_cursor, "priceChangePercent24h");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final List<CoinEntity> _result = new ArrayList<CoinEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CoinEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpImage;
            _tmpImage = _cursor.getString(_cursorIndexOfImage);
            final double _tmpCurrentPrice;
            _tmpCurrentPrice = _cursor.getDouble(_cursorIndexOfCurrentPrice);
            final double _tmpMarketCap;
            _tmpMarketCap = _cursor.getDouble(_cursorIndexOfMarketCap);
            final int _tmpMarketCapRank;
            _tmpMarketCapRank = _cursor.getInt(_cursorIndexOfMarketCapRank);
            final double _tmpPriceChangePercent24h;
            _tmpPriceChangePercent24h = _cursor.getDouble(_cursorIndexOfPriceChangePercent24h);
            final String _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            _item = new CoinEntity(_tmpId,_tmpSymbol,_tmpName,_tmpImage,_tmpCurrentPrice,_tmpMarketCap,_tmpMarketCapRank,_tmpPriceChangePercent24h,_tmpLastUpdated);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<CoinEntity> getCoinById(final String id) {
    final String _sql = "SELECT * FROM coins WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"coins"}, new Callable<CoinEntity>() {
      @Override
      @Nullable
      public CoinEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfCurrentPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPrice");
          final int _cursorIndexOfMarketCap = CursorUtil.getColumnIndexOrThrow(_cursor, "marketCap");
          final int _cursorIndexOfMarketCapRank = CursorUtil.getColumnIndexOrThrow(_cursor, "marketCapRank");
          final int _cursorIndexOfPriceChangePercent24h = CursorUtil.getColumnIndexOrThrow(_cursor, "priceChangePercent24h");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final CoinEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSymbol;
            _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpImage;
            _tmpImage = _cursor.getString(_cursorIndexOfImage);
            final double _tmpCurrentPrice;
            _tmpCurrentPrice = _cursor.getDouble(_cursorIndexOfCurrentPrice);
            final double _tmpMarketCap;
            _tmpMarketCap = _cursor.getDouble(_cursorIndexOfMarketCap);
            final int _tmpMarketCapRank;
            _tmpMarketCapRank = _cursor.getInt(_cursorIndexOfMarketCapRank);
            final double _tmpPriceChangePercent24h;
            _tmpPriceChangePercent24h = _cursor.getDouble(_cursorIndexOfPriceChangePercent24h);
            final String _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            _result = new CoinEntity(_tmpId,_tmpSymbol,_tmpName,_tmpImage,_tmpCurrentPrice,_tmpMarketCap,_tmpMarketCapRank,_tmpPriceChangePercent24h,_tmpLastUpdated);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
