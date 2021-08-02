package org.beckn.one.sandbox.bap.message.factories

import org.beckn.protocol.schemas.ProtocolOnRatingMessageFeedback

object ProtocolOnRatingMessageFeedbackFactory {

  fun create(index: Int = 1): ProtocolOnRatingMessageFeedback {
    return ProtocolOnRatingMessageFeedback(
      id = "item id 1",
      descriptor = "item descriptor",
      parentId = "item id 1 - parent id 1",
    )
  }
}