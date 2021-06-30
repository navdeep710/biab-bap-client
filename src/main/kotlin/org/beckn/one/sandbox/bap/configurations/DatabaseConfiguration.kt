package org.beckn.one.sandbox.bap.configurations

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoDatabase
import org.beckn.one.sandbox.bap.message.entities.Message
import org.beckn.one.sandbox.bap.message.entities.OnInit
import org.beckn.one.sandbox.bap.message.entities.OnSearch
import org.beckn.one.sandbox.bap.message.entities.OnSelect
import org.beckn.one.sandbox.bap.message.repositories.BecknResponseRepository
import org.beckn.one.sandbox.bap.message.repositories.GenericRepository
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollectionOfName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfiguration @Autowired constructor(
  @Value("\${database.mongo.url}") private val connectionString: String,
  @Value("\${database.mongo.name}") private val databaseName: String
) {
  @Bean
  fun database(): MongoDatabase {
    val settings = MongoClientSettings.builder()
      .applyConnectionString(ConnectionString(connectionString))
      .build()
    val client = KMongo.createClient(settings)
    return client.getDatabase(databaseName)
  }

  @Bean
  fun searchResponseRepo(@Autowired database: MongoDatabase): BecknResponseRepository<OnSearch> =
    BecknResponseRepository(database.getCollectionOfName("on_search"))

  @Bean
  fun messageResponseRepo(@Autowired database: MongoDatabase): GenericRepository<Message> =
    GenericRepository.create(database, "message_responses")

  @Bean
  fun onSelectResponseRepo(@Autowired database: MongoDatabase): BecknResponseRepository<OnSelect> =
    BecknResponseRepository(database.getCollectionOfName("on_select"))

  @Bean
  fun onInitResponseRepo(@Autowired database: MongoDatabase): BecknResponseRepository<OnInit> =
    BecknResponseRepository(database.getCollectionOfName("on_select"))
}