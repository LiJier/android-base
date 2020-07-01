package com.core.dao

import androidx.room.*

/**
 * Create by LiJie at 2019-06-17
 */
interface IDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: T): List<Long>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: T): Int

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg data: T): Int

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(list: List<T>): Int

    @Delete
    fun delete(data: T): Int

    @Transaction
    @Delete
    fun delete(vararg data: T): Int

    @Transaction
    @Delete
    fun delete(list: List<T>): Int

}