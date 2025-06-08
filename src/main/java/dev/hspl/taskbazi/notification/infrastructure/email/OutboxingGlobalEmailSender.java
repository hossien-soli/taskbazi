package dev.hspl.taskbazi.notification.infrastructure.email;

import dev.hspl.taskbazi.common.domain.value.EmailAddress;
import dev.hspl.taskbazi.notification.infrastructure.UserFriendlyMessage;
import dev.hspl.taskbazi.notification.infrastructure.persistence.entity.OutboxEmailRecord;
import dev.hspl.taskbazi.notification.infrastructure.persistence.repository.jpa.OutboxEmailRecordJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// This implementation uses the Outbox Pattern to store email send requests in a database table within the transaction
// calling sendEmailMessage should be inside a database transaction(or not!)
// instant email sender!! not async!!!
// if you don't want outboxing just create an AsyncGlobalEmailSender and use spring @Async annotation
// it is not good to Directly send emails inside a database transaction or connection
// or just use DirectGlobalEmailSender and delegate async management to another layer

@Component
@RequiredArgsConstructor
public class OutboxingGlobalEmailSender implements GlobalEmailSender {
    private final OutboxEmailRecordJPARepository repository;

    @Override
    public void sendEmailMessage(EmailAddress targetEmailAddress, UserFriendlyMessage message) {
        OutboxEmailRecord outboxRecord = new OutboxEmailRecord();
        outboxRecord.setTargetEmailAddress(targetEmailAddress.value());
        outboxRecord.setMessageSubject(message.subject());
        outboxRecord.setMessageSimpleBody(message.plainTextBody());
        outboxRecord.setMessageHTMLBody(message.htmlBody());
        outboxRecord.setMessageIsImportant(message.isImportant());
        repository.save(outboxRecord);
    }
}
