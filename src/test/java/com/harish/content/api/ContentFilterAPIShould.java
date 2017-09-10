package com.harish.content.api;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.google.gson.Gson;
import com.harish.streaming.content.ContentFilterApp;
import com.harish.streaming.content.api.response.ContentData;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {ContentFilterApp.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Slf4j
public class ContentFilterAPIShould {

  public static final int RESPONSE_CODE_OK = HttpStatus.OK_200;
  public static final String CONTENT_DATA_LOCATION_LOOKUP_URL = "/javaassignmentsrc";
  public static final String CONTENT_DATA_URL = "/uc";
  public static final String CONTENT_DATA_ID = "";
  public static final String ID_QUERY_PARAM = "id";
  public static final String ID = ID_QUERY_PARAM;

  @ClassRule
  public static WireMockClassRule wiremockRule =
      new WireMockClassRule(WireMockConfiguration.wireMockConfig().port(8080));

  private RequestSpecification requestSpecification;
  String contentLocationInfo;

  @Before
  public void setUp() throws Exception {
    requestSpecification = new RequestSpecBuilder().setPort(8181).setBasePath("/").build();
    RestAssured.port = 8181;
  }

  @Test
  public void filter_uncensored_media_when_level_censored_and_classification_censored()
      throws Exception {
    String contentData =
        new String(
            Files.readAllBytes(
                Paths.get(
                    ClassLoader.getSystemResource(
                            "__files/content_data_scenario_with_classification_as_censored.json")
                        .toURI())));
    stubFor(
        get(urlMatching(CONTENT_DATA_LOCATION_LOOKUP_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
                    .withBody(contentLocationInfo)));

    stubFor(
        get(urlPathEqualTo(CONTENT_DATA_URL))
            .withQueryParam(ID_QUERY_PARAM, equalTo(CONTENT_DATA_ID))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(contentData)));

    Response response =
        RestAssured.given()
            .spec(this.requestSpecification)
            .when()
            .get("http://localhost:8181/media?filter=censoring&level=censored")
            .thenReturn();

    Assert.assertEquals(RESPONSE_CODE_OK, response.statusCode());
    ContentData contentResponse = response.as(ContentData.class);
    contentResponse
        .getEntries()
        .stream()
        .forEach(entry -> Assert.assertEquals(1, entry.getMedia().size()));
  }

  @Test
  public void filter_uncensored_media_when_level_uncensored_and_classification_censored()
      throws Exception {
    String contentData =
        new String(
            Files.readAllBytes(
                Paths.get(
                    ClassLoader.getSystemResource(
                            "__files/content_data_scenario_with_classification_as_censored.json")
                        .toURI())));
    stubFor(
        get(urlMatching(CONTENT_DATA_LOCATION_LOOKUP_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
                    .withBody(contentLocationInfo)));

    stubFor(
        get(urlPathEqualTo(CONTENT_DATA_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(contentData)));

    Response response =
        RestAssured.given()
            .spec(this.requestSpecification)
            .when()
            .get("http://localhost:8181/media?filter=censoring&level=uncensored")
            .thenReturn();

    Assert.assertEquals(RESPONSE_CODE_OK, response.statusCode());
    ContentData contentResponse = response.as(ContentData.class);
    contentResponse
        .getEntries()
        .stream()
        .forEach(entry -> Assert.assertEquals(1, entry.getMedia().size()));
  }

  @Test
  public void filter_uncensored_media_when_level_uncensored_and_classification_empty() throws Exception {
    String contentData =
        new String(
            Files.readAllBytes(
                Paths.get(
                    ClassLoader.getSystemResource(
                            "__files/content_data_with_classification_as_empty.json")
                        .toURI())));
    stubFor(
        get(urlMatching(CONTENT_DATA_LOCATION_LOOKUP_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
                    .withBody(contentLocationInfo)));

    stubFor(
        get(urlPathEqualTo(CONTENT_DATA_URL))
            .withQueryParam(ID_QUERY_PARAM, equalTo(CONTENT_DATA_ID))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(contentData)));

    Response response =
        RestAssured.given()
            .spec(this.requestSpecification)
            .when()
            .get("http://localhost:8181/media?filter=censoring&level=uncensored")
            .thenReturn();

    Assert.assertEquals(RESPONSE_CODE_OK, response.statusCode());
    ContentData contentResponse = response.as(ContentData.class);
    Assert.assertEquals(StringUtils.EMPTY, contentResponse.getEntries().get(0).getPeg$contentClassification());
    contentResponse
        .getEntries()
        .stream()
        .forEach(entry -> Assert.assertEquals(1, entry.getMedia().size()));
  }

  @Test
  public void filter_uncensored_media_when_level_is_not_provided_and_classification_empty()
      throws URISyntaxException, IOException {
    String contentData =
        new String(
            Files.readAllBytes(
                Paths.get(
                    ClassLoader.getSystemResource(
                            "__files/content_data_with_classification_as_empty.json")
                        .toURI())));
    stubFor(
        get(urlMatching(CONTENT_DATA_LOCATION_LOOKUP_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
                    .withBody(contentLocationInfo)));

    stubFor(
        get(urlPathEqualTo(CONTENT_DATA_URL))
            .withQueryParam(ID_QUERY_PARAM, equalTo(CONTENT_DATA_ID))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(contentData)));

    Response response =
        RestAssured.given()
            .spec(this.requestSpecification)
            .when()
            .get("http://localhost:8181/media?filter=censoring")
            .thenReturn();

    Assert.assertEquals(RESPONSE_CODE_OK, response.statusCode());
    ContentData contentResponse = response.as(ContentData.class);
    Assert.assertEquals(
        StringUtils.EMPTY, contentResponse.getEntries().get(0).getPeg$contentClassification());
    contentResponse
        .getEntries()
        .stream()
        .forEach(entry -> Assert.assertEquals(1, entry.getMedia().size()));
  }

  @Test
  public void filter_uncensored_media_when_level_is_not_provided_and_classification_uncensored()
      throws URISyntaxException, IOException {
    String contentData =
        new String(Files.readAllBytes(
             Paths.get(ClassLoader.getSystemResource("__files/content_data_with_classification_as_empty.json").toURI())));
    stubFor(
        get(urlMatching(CONTENT_DATA_LOCATION_LOOKUP_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
                    .withBody(contentLocationInfo)));

    stubFor(
        get(urlPathEqualTo(CONTENT_DATA_URL))
            .withQueryParam(ID_QUERY_PARAM, equalTo(CONTENT_DATA_ID))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(contentData)));

    Response response =
        RestAssured.given()
            .spec(this.requestSpecification)
            .when()
            .get("http://localhost:8181/media?filter=censoring")
            .thenReturn();

    Assert.assertEquals(RESPONSE_CODE_OK, response.statusCode());
    ContentData contentResponse = response.as(ContentData.class);
    Assert.assertEquals(StringUtils.EMPTY, contentResponse.getEntries().get(0).getPeg$contentClassification());
    contentResponse.getEntries().stream().forEach(entry -> Assert.assertEquals(1, entry.getMedia().size()));
  }

  @Test
  public void filter_uncensored_media_when_level_is_not_provided_and_classification_censored()
      throws URISyntaxException, IOException {
    String contentData =
        new String(
            Files.readAllBytes(
                Paths.get(
                    ClassLoader.getSystemResource(
                            "__files/content_data_with_classification_as_empty.json")
                        .toURI())));
    stubFor(
        get(urlMatching(CONTENT_DATA_LOCATION_LOOKUP_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
                    .withBody(contentLocationInfo)));

    stubFor(
        get(urlPathEqualTo(CONTENT_DATA_URL))
            .withQueryParam(ID_QUERY_PARAM, equalTo(CONTENT_DATA_ID))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(contentData)));

    Response response =
        RestAssured.given()
            .spec(this.requestSpecification)
            .when()
            .get("http://localhost:8181/media?filter=censoring")
            .thenReturn();

    Assert.assertEquals(RESPONSE_CODE_OK, response.statusCode());
    ContentData contentResponse = response.as(ContentData.class);
    Assert.assertEquals(StringUtils.EMPTY, contentResponse.getEntries().get(0).getPeg$contentClassification());
    contentResponse.getEntries().stream().forEach(entry -> Assert.assertEquals(1, entry.getMedia().size()));
  }

  @Test
  public void filter_censored_media_when_level_is_censored_and_classification_censored() throws Exception {
    String contentData =
        new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("__files/content.json").toURI())));
    ContentData expectedResponse = new Gson().fromJson(contentData, ContentData.class);
    stubFor(
        get(urlMatching(CONTENT_DATA_LOCATION_LOOKUP_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
                    .withBody(contentLocationInfo)));

    stubFor(
        get(urlPathEqualTo(CONTENT_DATA_URL))
            .withQueryParam(ID_QUERY_PARAM, equalTo(CONTENT_DATA_ID))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(contentData)));

    Response response = RestAssured.given()
                        .spec(this.requestSpecification)
                        .when()
                        .get( "http://localhost:8181/media?filter=censoring&level=censored")
                        .thenReturn();

    Assert.assertEquals(RESPONSE_CODE_OK, response.statusCode());
    ContentData actualResponse = response.as(ContentData.class);
    Assert.assertFalse(expectedResponse.equals(actualResponse));
  }

  @Test
  public void filter_censored_media_when_level_is_uncensored_and_classification_censored() throws Exception {
    String contentData = new String(
            Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("__files/content.json").toURI())));
    ContentData expectedResponse = new Gson().fromJson(contentData, ContentData.class);
    stubFor(
        get(urlMatching(CONTENT_DATA_LOCATION_LOOKUP_URL))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, TEXT_HTML_VALUE)
                    .withBody(contentLocationInfo)));

    stubFor(
        get(urlPathEqualTo(CONTENT_DATA_URL))
            .withQueryParam(ID_QUERY_PARAM, equalTo(CONTENT_DATA_ID))
            .willReturn(
                aResponse()
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(contentData)));

    Response response =
        RestAssured.given()
            .spec(this.requestSpecification)
            .when()
            .get("http://localhost:8181/media?filter=censoring&level=uncensored")
            .thenReturn();

    Assert.assertEquals(RESPONSE_CODE_OK, response.statusCode());
    ContentData actualResponse = response.as(ContentData.class);
    Assert.assertFalse(expectedResponse.equals(actualResponse));
  }
}
