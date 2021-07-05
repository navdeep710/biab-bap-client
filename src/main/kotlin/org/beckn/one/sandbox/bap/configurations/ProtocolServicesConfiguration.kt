package org.beckn.one.sandbox.bap.configurations

import org.beckn.one.sandbox.bap.message.entities.OnConfirm
import org.beckn.one.sandbox.bap.message.entities.OnInitDao
import org.beckn.one.sandbox.bap.message.entities.OnSearchDao
import org.beckn.one.sandbox.bap.message.entities.OnSelectDao
import org.beckn.one.sandbox.bap.message.mappers.GenericResponseMapper
import org.beckn.one.sandbox.bap.message.repositories.BecknResponseRepository
import org.beckn.one.sandbox.bap.message.services.ResponseStorageService
import org.beckn.one.sandbox.bap.message.services.ResponseStorageServiceImpl
import org.beckn.one.sandbox.bap.schemas.ProtocolOnConfirm
import org.beckn.one.sandbox.bap.schemas.ProtocolOnInit
import org.beckn.one.sandbox.bap.schemas.ProtocolOnSearch
import org.beckn.one.sandbox.bap.schemas.ProtocolOnSelect
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
    @Autowired responseRepo: BecknResponseRepository<OnConfirm>,
    @Autowired  mapper: GenericResponseMapper<ProtocolOnConfirm, OnConfirm>
  ): ResponseStorageService<ProtocolOnConfirm> = ResponseStorageServiceImpl(responseRepo, mapper)
}