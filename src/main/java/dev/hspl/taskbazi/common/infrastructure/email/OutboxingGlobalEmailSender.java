package dev.hspl.taskbazi.common.infrastructure.email;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.common.infrastructure.persistence.entity.OutboxEmailRecord;
import dev.hspl.taskbazi.common.infrastructure.persistence.repository.OutboxEmailRecordJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

// This implementation uses the Outbox Pattern to store email send requests in a database table within the transaction

@Component
@RequiredArgsConstructor
public class OutboxingGlobalEmailSender implements GlobalEmailSender {
    private final OutboxEmailRecordJPARepository repository;
    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;

    @Override
    public void sendEmailMessage(EmailAddress targetEmailAddress, GenericEmailMessage message) {
        String messageSubject = message.prepareSubject(messageSource);
        String simpleMessageBody = message.prepareSimpleBody(messageSource);
        String htmlMessageBody = message.prepareHTMLBody(messageSource,templateEngine);

        OutboxEmailRecord outboxRecord = new OutboxEmailRecord();
        outboxRecord.setTargetEmailAddress(targetEmailAddress.value());
        outboxRecord.setMessageSubject(messageSubject);
        outboxRecord.setMessageSimpleBody(simpleMessageBody);
        outboxRecord.setMessageHTMLBody(htmlMessageBody);
        repository.save(outboxRecord);
    }
}
