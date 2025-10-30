package cmahy.simple.spring.webapp.resource.impl.adapter.jmx;

import org.springframework.jmx.export.annotation.*;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;

import javax.management.Notification;

@Service
@ManagedResource
public class CustomJmxCounter implements NotificationPublisherAware {

    private Long counter = 0L;
    private NotificationPublisher notificationPublisher;

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    @ManagedAttribute
    public long getCounter() {
        return counter;
    }

    @ManagedOperation
    public Long increment(Long delta) {

        Notification notification = new Notification(
            "jmx.counter", this, counter, (counter + delta) + " new value !"
        );

        notificationPublisher.sendNotification(notification);

        counter += delta;

        return counter;
    }

}
