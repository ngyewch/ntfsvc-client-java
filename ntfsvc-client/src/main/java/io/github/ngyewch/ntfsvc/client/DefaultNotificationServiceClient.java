package io.github.ngyewch.ntfsvc.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

/**
 * Default notification service client implementation.
 */
public class DefaultNotificationServiceClient
    implements NotificationServiceClient {

  private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  private final String apiUrl;
  private final String apiKey;

  private final OkHttpClient client = new OkHttpClient();
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Construct a new DefaultNotificationServiceClient instance.
   *
   * @param apiUrl API url.
   * @param apiKey API key.
   */
  public DefaultNotificationServiceClient(String apiUrl, String apiKey) {
    super();

    this.apiUrl = apiUrl;
    this.apiKey = apiKey;
  }

  @Override
  public void sendNotification(String topic, String message)
      throws IOException {
    final SendNotificationRequest sendNotificationRequest = new SendNotificationRequest();
    sendNotificationRequest.setTopic(topic);
    sendNotificationRequest.setMessage(message);

    final RequestBody body = RequestBody.create(objectMapper.writeValueAsString(sendNotificationRequest), JSON);
    final Request request = new Request.Builder()
        .header("X-API-KEY", apiKey)
        .url(apiUrl)
        .post(body)
        .build();
    try (final Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException(String.format("HTTP %d %s", response.code(), response.message()));
      }
    }
  }

  private static class SendNotificationRequest {

    private String topic;
    private String message;

    public String getTopic() {
      return topic;
    }

    public void setTopic(String topic) {
      this.topic = topic;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}