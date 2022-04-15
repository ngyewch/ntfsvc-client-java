package io.github.ngyewch.ntfsvc.client.app;

import io.github.ngyewch.ntfsvc.client.DefaultNotificationServiceClient;
import io.github.ngyewch.ntfsvc.client.NotificationServiceClient;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.EnvironmentConfiguration;

public class Main {

  public static void main(String[] args)
      throws Exception {
    final Configuration config = new EnvironmentConfiguration();
    final String apiUrl = config.getString("NTFSVC_URL");
    final String apiKey = config.getString("NTFSVC_APIKEY");
    final String topic = args[0];
    final String message = args[1];

    final NotificationServiceClient notificationServiceClient = new DefaultNotificationServiceClient(apiUrl, apiKey);
    notificationServiceClient.sendNotification(topic, message);
  }
}
