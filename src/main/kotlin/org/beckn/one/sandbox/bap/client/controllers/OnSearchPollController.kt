package org.beckn.one.sandbox.bap.client.controllers

import org.beckn.one.sandbox.bap.client.dtos.ClientResponse
import org.beckn.one.sandbox.bap.client.dtos.ClientSearchResponse
import org.beckn.one.sandbox.bap.client.services.GenericOnPollService
import org.beckn.one.sandbox.bap.schemas.ProtocolSearchResponse
import org.beckn.one.sandbox.bap.schemas.factories.ContextFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OnSearchPollController @Autowired constructor(
  private val onPollService: GenericOnPollService<ProtocolSearchResponse, ClientSearchResponse>,
  private val contextFactory: ContextFactory
): BaseOnPollController<ProtocolSearchResponse, ClientSearchResponse>(onPollService, contextFactory) {

  @RequestMapping("/client/v1/on_search")
  @ResponseBody
  fun onSearchV1(
    @RequestParam messageId: String
  ): ResponseEntity<out ClientResponse> = onPoll(messageId)
}