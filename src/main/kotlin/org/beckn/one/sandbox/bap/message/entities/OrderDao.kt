package org.beckn.one.sandbox.bap.message.entities

import org.beckn.one.sandbox.bap.Default

data class OrderDao @Default constructor(
  val provider: SelectMessageSelectedProviderDao,
  val items: List<SelectMessageSelectedItemsDao>,
  val addOns: List<SelectMessageSelectedAddOnsDao>,
  val offers: List<SelectMessageSelectedOffersDao>,
  val billing: BillingDao,
  val fulfillment: FulfillmentDao,
  val quote: QuotationDao,
  val payment: PaymentDao,
  val id: String? = null,
  val state: String? = null,
  val createdAt: java.time.OffsetDateTime? = null,
  val updatedAt: java.time.OffsetDateTime? = null
)


data class SelectMessageSelectedProviderDao @Default constructor(
  val id: String,
  val locations: List<SelectMessageSelectedProviderLocationsDao>
)

data class SelectMessageSelectedProviderLocationsDao @Default constructor(
  val id: String
)

// TODO can be common
data class SelectMessageSelectedAddOnsDao @Default constructor(
  val id: String
)

// TODO similar to OnInitMessageInitializedItems
data class SelectMessageSelectedItemsDao @Default constructor(
  val id: String,
  val quantity: ItemQuantityAllocatedDao
)

data class SelectMessageSelectedOffersDao @Default constructor(
  val id: String
)