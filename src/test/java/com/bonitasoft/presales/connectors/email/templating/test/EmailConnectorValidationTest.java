package com.bonitasoft.presales.connectors.email.templating.test;

import com.bonitasoft.presales.connectors.email.templating.EmailConnector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bonitasoft.engine.connector.ConnectorValidationException;
import org.bonitasoft.engine.exception.BonitaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailConnectorValidationTest {

  private static final String SMTP_HOST = "localhost";

  private static final String ADDRESSJOHN = "john.doe@bonita.org";

  private static final String SUBJECT = "Testing EmailConnector";

  private static final String PLAINMESSAGE = "Plain Message";

  private void validateConnector(final Map<String, Object> parameters)
      throws ConnectorValidationException {
    final EmailConnector email = new EmailConnector();
    email.setInputParameters(parameters);
    email.validateInputParameters();
  }

  private Map<String, Object> getBasicSettings() {
    final Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("smtpHost", SMTP_HOST);
    parameters.put("smtpPort", 0);
    parameters.put("to", ADDRESSJOHN);
    parameters.put("subject", SUBJECT);
    parameters.put("sslSupport", false);
    parameters.put("html", false);
    return parameters;
  }

  @Test
  public void validatesSimpliestEmail() throws ConnectorValidationException {
    validateConnector(getBasicSettings());
  }

  @Test
  public void validatesEmailWithAValidFromAddress() throws ConnectorValidationException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("from", "john@bpm.com");
    validateConnector(parameters);
  }

  @Test
  public void validatesEmailWithANullValidFromAddress() throws ConnectorValidationException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("from", null);
    validateConnector(parameters);
  }

  @Test
  public void thowsExceptionDueToInvalidEmailAddressFrom() throws ConnectorValidationException {
    Assertions.assertThrows(
        ConnectorValidationException.class,
        () -> {
          final Map<String, Object> parameters = getBasicSettings();
          parameters.put("from", "@bonita.org");
          validateConnector(parameters);
        });
  }

  @Test
  public void thowsExceptionDueToNoRecipientAddress() throws ConnectorValidationException {
    Assertions.assertThrows(
        ConnectorValidationException.class,
        () -> {
          final Map<String, Object> parameters = getBasicSettings();
          parameters.remove("to");
          validateConnector(parameters);
        });
  }

  @Test
  public void validEmailEvenIfHeadersAreNull() throws ConnectorValidationException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("headers", null);
    validateConnector(parameters);
  }

  @Test
  public void validEmailWithExtraHeaders() throws ConnectorValidationException {
    List<List<String>> headers = new ArrayList<List<String>>();
    List<String> line = new ArrayList<String>();
    line.add("X-Mailer");
    line.add("Bonita");
    headers.add(line);
    line = new ArrayList<String>();
    line.add("X-Sender");
    line.add("Test");
    line = new ArrayList<String>();
    line.add("WhatIwant");
    line.add("WhatIwant");
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("headers", headers);
    validateConnector(parameters);
  }

  @Test
  public void validEmailWithANullMessage() throws ConnectorValidationException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("message", null);
    validateConnector(parameters);
  }

  @Test
  public void validEmailWithAEmptyMessage() throws ConnectorValidationException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("message", "");
    validateConnector(parameters);
  }

  @Test
  public void validEmailWithAMessage() throws ConnectorValidationException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("message", PLAINMESSAGE);
    validateConnector(parameters);
  }

  @Test
  public void validAuthentication() throws ConnectorValidationException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("usernName", null);
    parameters.put("password", null);
    validateConnector(parameters);

    parameters.put("usernName", "john");
    parameters.put("password", null);
    validateConnector(parameters);

    parameters.put("userName", null);
    parameters.put("password", "bonita");
    validateConnector(parameters);
  }

  @Test
  public void throwsExceptionWhenSmtpHostIsNull() throws ConnectorValidationException {
    Assertions.assertThrows(
        ConnectorValidationException.class,
        () -> {
          final Map<String, Object> parameters = getBasicSettings();
          parameters.put("smtpHost", null);
          validateConnector(parameters);
        });
  }

  @Test
  public void throwsExceptionWhenSmtpPortIsNull() throws ConnectorValidationException {
    Assertions.assertThrows(
        ConnectorValidationException.class,
        () -> {
          final Map<String, Object> parameters = getBasicSettings();
          parameters.put("smtpPort", null);
          validateConnector(parameters);
        });
  }

  @Test
  public void throwsExceptionWhenWrappedSmtpPortIsLessThanRange() throws BonitaException {
    Assertions.assertThrows(
        ConnectorValidationException.class,
        () -> {
          final Map<String, Object> parameters = getBasicSettings();
          parameters.put("smtpPort", -1);
          validateConnector(parameters);
        });
  }

  @Test
  public void throwsExceptionWhenWrappedSmtpPortWithGreaterThanRange() throws BonitaException {
    Assertions.assertThrows(
        ConnectorValidationException.class,
        () -> {
          final Map<String, Object> parameters = getBasicSettings();
          parameters.put("smtpPort", 65536);
          validateConnector(parameters);
        });
  }

  @Test
  public void throwsExceptionWhenSmtpPortIsLessThanRange() throws BonitaException {
    Assertions.assertThrows(
        ConnectorValidationException.class,
        () -> {
          final Map<String, Object> parameters = getBasicSettings();
          parameters.put("smtpPort", -1);
          validateConnector(parameters);
        });
  }

  @Test
  public void throwsExceptionWhenSmtpPortWithGreaterThanRange() throws BonitaException {
    Assertions.assertThrows(
        ConnectorValidationException.class,
        () -> {
          final Map<String, Object> parameters = getBasicSettings();
          parameters.put("smtpPort", 65536);
          validateConnector(parameters);
        });
  }

  @Test
  public void validEmailWithANullSubject() throws BonitaException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("subject", null);
    validateConnector(parameters);
  }

  @Test
  public void validEmailWithASubject() throws BonitaException {
    final Map<String, Object> parameters = getBasicSettings();
    parameters.put("subject", SUBJECT);
    validateConnector(parameters);
  }
}
