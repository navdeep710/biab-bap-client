package org.beckn.one.sandbox.bap.configurations

import org.beckn.one.sandbox.bap.message.entities.OnConfirmDao
import org.beckn.one.sandbox.bap.message.entities.OnInitDao
import org.beckn.one.sandbox.bap.message.entities.OnSearchDao
import org.beckn.one.sandbox.bap.message.entities.OnSelectDao
import org.beckn.one.sandbox.bap.message.mappers.GenericResponseMapper
import org.beckn.one.sandbox.bap.message.repositories.BecknResponseRepository
import org.beckn.one.sandbox.bap.message.services.MessageService
import org.beckn.one.sandbox.bap.message.services.ResponseStorageService
import org.beckn.one.sandbox.bap.message.services.ResponseStorageServiceImpl
import org.beckn.one.sandbox.bap.protocol.shared.services.PollForResponseService
import org.beckn.protocol.schemas.ProtocolOnConfirm
import org.beckn.protocol.schemas.ProtocolOnInit
import org.beckn.protocol.schemas.ProtocolOnSearch
import org.beckn.protocol.schemas.ProtocolOnSelect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProtocolServicesConfiguration {

  @Bean
  fun onSearchStorageService(
    @Autowired responseRepo: BecknResponseRepository<OnSearchDao>,
    @Autowired  mapper: GenericResponseMapper<ProtocolOnSearch, OnSearchDao>
  ): ResponseStorageService<ProtocolOnSearch> = ResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onSelectStorageService(
    @Autowired responseRepo: BecknResponseRepository<OnSelectDao>,
    @Autowired  mapper: GenericResponseMapper<ProtocolOnSelect, OnSelectDao>
  ): ResponseStorageService<ProtocolOnSelect> = ResponseStorageServiceImpl(responseRepo, mapper)


  @Bean
  fun onInitStorageService(
    @Autowired responseRepo: BecknResponseRepository<OnInitDao>,
    @Autowired  mapper: GenericResponseMapper<ProtocolOnInit, OnInitDao>
  ): ResponseStorageService<ProtocolOnInit> = ResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun onConfirmStorageService(
    @Autowired responseRepo: BecknResponseRepository<OnConfirmDao>,
    @Autowired  mapper: GenericResponseMapper<ProtocolOnConfirm, OnConfirmDao>
  ): ResponseStorageService<ProtocolOnConfirm> = ResponseStorageServiceImpl(responseRepo, mapper)

  @Bean
  fun pollForSearchResponseService(
    messageService: MessageService,
    responseStorageService: ResponseStorageService<ProtocolOnSearch>
  ) = PollForResponseService(messageService, responseStorageService)

  @Bean
  fun pollForInitResponseService(
    messageService: MessageService,
    responseStorageService: ResponseStorageService<ProtocolOnInit>
  ) = PollForResponseService(messageService, responseStorageService)

  @Bean
  fun pollForSelectResponseService(
    messageService: MessageService,
    responseStorageService: ResponseStorageService<ProtocolOnSelect>
  ) = PollForResponseService(messageService, responseStorageService)

  @Bean
  fun pollForConfirmResponseService(
    messageService: MessageService,
    responseStorageService: ResponseStorageService<ProtocolOnConfirm>
  ) = PollForResponseService(messageService, responseStorageService)
}