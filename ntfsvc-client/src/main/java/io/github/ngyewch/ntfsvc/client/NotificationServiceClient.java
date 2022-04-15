package io.github.ngyewch.ntfsvc.client;

import java.io.IOException;

/**
 * Notification service client.
 */
public interface NotificationServiceClient {

  /**
   * Send notification.
   *
   * @param topic   Topic.
   * @param message Message.
   * @throws IOException If an I/O exception occurred.
   */
  void sendNotification(String topic, String message)
      throws IOException;
}
