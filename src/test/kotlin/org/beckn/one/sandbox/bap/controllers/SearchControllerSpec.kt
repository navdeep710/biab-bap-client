package org.beckn.one.sandbox.bap.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock.*
import io.kotest.core.spec.style.DescribeSpec
import org.beckn.one.sandbox.bap.dtos.ResponseStatus.ACK
import org.beckn.one.sandbox.bap.factories.BecknResponseFactory
import org.beckn.one.sandbox.bap.factories.ContextFactory
import org.beckn.one.sandbox.bap.factories.NetworkMock
import org.hamcrest.CoreMatchers.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
@TestPropertySource(locations = ["/application-test.yml"])
class SearchControllerSpec @Autowired constructor(
  val mockMvc: MockMvc,
  val objectMapper: ObjectMapper,
  val contextFactory: ContextFactory
) : DescribeSpec() {
  init {

    describe("Search") {
      NetworkMock.startAllSubscribers()

      beforeEach {
        NetworkMock.resetAllSubscribers()
      }

      it("should return error response when registry lookup fails") {
        NetworkMock.registry
          .stubFor(post("/lookup").willReturn(serverError()))

        mockMvc
          .perform(
            get("/v1/search")
              .param("searchString", "Fictional mystery books")
          )
          .andExpect(status().is5xxServerError)
          .andExpect(jsonPath("$.message.ack.status", `is`("NACK")))
          .andExpect(jsonPath("$.error.code", `is`("BAP_001")))
          .andExpect(jsonPath("$.error.message", `is`("Registry lookup returned error")))
      }

      it("should invoke Beckn /search API on first gateway") {
        val gatewaysJson = objectMapper.writeValueAsString(NetworkMock.getAllGateways())
        NetworkMock.registry
          .stubFor(post("/lookup").willReturn(okJson(gatewaysJson)))
        NetworkMock.retailBengaluruBg
          .stubFor(
            post("/search").willReturn(
              okJson(
                objectMapper.writeValueAsString(BecknResponseFactory.getDefault(contextFactory))
              )
            )
          )

        mockMvc
          .perform(
            get("/v1/search")
              .param("searchString", "Fictional mystery books")
          )
          .andExpect(status().is2xxSuccessful)
          .andExpect(jsonPath("$.message.ack.status", `is`(ACK.status)))
          .andExpect(jsonPath("$.context.message_id", `is`(notNullValue())))

        NetworkMock.retailBengaluruBg.verify(postRequestedFor(urlEqualTo("/search")))
      }
    }
  }
}