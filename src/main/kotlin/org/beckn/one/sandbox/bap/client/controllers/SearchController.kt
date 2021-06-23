package org.beckn.one.sandbox.bap.client.controllers

import org.beckn.one.sandbox.bap.client.dtos.ClientSearchResponse
import org.beckn.one.sandbox.bap.client.services.SearchService
import org.beckn.one.sandbox.bap.schemas.*
import org.beckn.one.sandbox.bap.schemas.factories.ContextFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController @Autowired constructor(
  val searchService: SearchService,
  val contextFactory: ContextFactory
) {
  @RequestMapping("/v1/search")
  @ResponseBody
  fun searchV1(@RequestParam(required = false) searchString: String?): ResponseEntity<ProtocolResponse> =
    searchService.search(contextFactory.create(), searchString)

  @RequestMapping("/v0/search")
  @ResponseBody
  fun searchV0(@RequestParam(required = false) searchString: String? = ""): ResponseEntity<ProtocolResponse> {
    return ResponseEntity.ok(ProtocolResponse(context = contextFactory.create(), message = ResponseMessage.ack()))
  }

  @RequestMapping("/v0/on_search")
  @ResponseBody
  fun onSearchV0(@RequestParam(required = false) messageId: String? = ""): ResponseEntity<List<ProviderWithItems>> {
    return ResponseEntity.ok(
      listOf(
        ProviderWithItems(
          provider = Provider(
            id = "p1", name = "LocalBaniya", locations = listOf("Camp", "Shastri Nagar"),
            descriptor = "Your local grocery", images = listOf("/localbaniya")
          ),
          items = listOf(
            ProtocolItem(
              id = "it-1", name = "Sugar", parentItemId = "pid-1", descriptor = ProtocolDescriptor("Very sweet"),
              price = ProtocolPrice(
                estimated = "1", computed = "2", listed = "3", offered = "4", minimum = "5", maximum = "6"
              ),
              categoryId = "cat-1", images = listOf("/imageA", "/imageB"),
              tags = mapOf("classifiedAs" to "grocery"),
              attributes = mapOf("colour" to "white")
            )
          ),
          categories = listOf(
            ProtocolCategory(id = "cat-1", name = "Groceries", descriptor = ProtocolDescriptor(name = "Grocery items")),
            ProtocolCategory(id = "cat-2", name = "Perishable Items", descriptor = ProtocolDescriptor(name = "Perishable items"))
          ),
          offers = listOf(),
        )
      )
    )
  }

  @RequestMapping("/v1/on_search")
  @ResponseBody
  fun onSearchV1(@RequestParam messageId: String): ResponseEntity<ClientSearchResponse> =
    searchService.onSearch(contextFactory.create(messageId = messageId))
}