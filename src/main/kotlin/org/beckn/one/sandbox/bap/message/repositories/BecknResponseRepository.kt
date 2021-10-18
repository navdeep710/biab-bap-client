package org.beckn.one.sandbox.bap.message.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.result.UpdateResult
import org.beckn.one.sandbox.bap.message.entities.*
import org.bson.conversions.Bson
import org.litote.kmongo.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.management.Query

class BecknResponseRepository<R : BecknResponseDao>(
  val collection: MongoCollection<R>
) : GenericRepository<R>(collection) {
  private val log: Logger = LoggerFactory.getLogger(this::class.java)


  fun findManyByUserId(id: String, skip: Int = 0 , limit :Int = 10): List<R> =
    collection.find(BecknResponseDao::userId eq id).limit(limit).skip(skip).toList()

  fun findByTransactionId(id: String): R? {
    return findOne(OrderDao::transactionId eq id)
  }

  fun findByUserId(id: String): R? {
    return findOne(BecknResponseDao::userId eq id)
  }

  fun updateByTransactionId(id: String, requestData: R, updateOptions: UpdateOptions): UpdateResult {
    return updateOneById(
      OrderDao::transactionId eq id,
      requestData,
      updateOptions
    )
  }
  fun updateByUserId(id: Bson, requestData: R, updateOptions: UpdateOptions): UpdateResult {
    return updateOneById(
      id,
      requestData,
      updateOptions
    )
  }
  fun findOrdersById(id: String, skip: Int = 0 , limit :Int = 10  ): List<R> =
    collection.find(OrderDao::id eq id).limit(limit).skip(skip).toList()



}