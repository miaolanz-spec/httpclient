//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.HashMap;
//import java.util.Map;

//public class HttpClientTest {

//    public static void main(String[] args) {
//        // Replace these values with your actual OAuth2 token and API endpoint
//        String accessToken = "NiLmPNVrj0m9cDk7tCHmuw5kpnO1_-gBXumF_9Wj0go40moCqYkFwBXJssOGxL0Tro-NnCZxe_zRjFuSZxs8KxmHjQDNB204E9u_cpXDMd05IHRMz770PlXL9YmhGuiQhMpqAIHqRDEd9m6bjBX4CrhlpPpr8eVZ4se-jjvV_lhVlkPP1wp3FXUo_-7Hnym0u9g9nvKfdQ462POkTo0YOlU1X7jH9sYWkhiXlnBX4IyYchHUewXJNo1lzILFqte7cs5ChMixoWVX-HTxC0Y4acpIs4YFRXIhD1YCNnWGS4WGWh6PJ5tWE_pnxxeKIKJAO50lK-1Wyf3clysJZvyBL1CqNIHScoaeGk6ecF4PcGinsjefjPLCc0ykXt2rVdRw-SXlDDEX5FNHrht6papz_OmwxlZ5snbCu1395YMF8MpEdWRNOJ5x8m1huYjhkTWSKB9k28Tafp6ww-51AQwY4NBvatwr-GEeW1QbLTHvIvPEkyaMp553-dr9TtHUFiqY";
//        String apiEndpoint = "https://collector.zeekit.no/externalapi/functiondata/getStructuredFunctionData";
//
//        // JSON payload for the POST request (replace with your actual payload)
//        String jsonPayload = "[3245068]";
////        HttpClientContext context = HttpClientContext.create();
////        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
////        Map<String,Object> connProperties = URLReaderUtil.getURLReaderProperties();
////        connProperties.put(URLReader.PROP_HTTPS_NO_PRESENT_CLIENT_CERTIFICATE, restds.isNoPresentCert());
////        HttpClientBuilder clientBuilder = configureClient(
////                context,
////                requestConfigBuilder,
////                new URI(url, false),
////                extractSubject(ds, req), connProperties, ((RestDataSource)ds).getKeystore(), timeout
////        );
////
////        HttpRequestBase method = null;
////        method = new HttpPost(url);
////        method.setHeader("Connection", "close");
////        requestConfigBuilder.setAuthenticationEnabled(true);
//
//
//
//        // Set up HttpClient with Bearer token
//        HttpClient httpClient = HttpClient.newHttpClient();
//
//        // Prepare the HTTP request with the Bearer token, JSON payload, and POST method
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(apiEndpoint))
//                .header("Authorization", "Bearer " + accessToken)
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
//                .build();
//
//        try {
//            // Send the request and receive the response
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//            // Print the response status code and body
//            System.out.println("Response Code: " + response.statusCode());
//            System.out.println("Response Body: " + response.body());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    public static HttpClientBuilder configureClient(HttpClientContext context, RequestConfig.Builder requestConfigBuilder, URI  uri, Subject subject, Map connProps, Map dsKeystoreMap, long timeout) throws IOException
//    {
//        HttpClientBuilder clientBuilder = HttpClients.custom();
//
//
//        if (timeout > 0) {
//            requestConfigBuilder
//                    .setConnectTimeout((int)timeout)
//                    .setSocketTimeout((int) timeout).build();
//        }
//
//
//        // Set the https proxy credentials.
//        if ("https".equalsIgnoreCase(uri.getScheme())) {
//            boolean enableClientAuthentication = "true".equals(connProps
//                    .get(URLReader.PROP_ENABLE_CLIENT_AUTHENTICATION));
//            boolean disableHostnameVerification =
//                    ("true".equals(connProps.get(
//                            URLReader.PROP_HTTPS_DISABLE_HOSTNAME_VERIFICATION)))
//                            || NET_DISABLE_HOST_VERIFICATION.getBoolean();
//            KeyStoreWrapper ksw = new KeyStoreWrapper();
//            KeyStoreWrapper tsw = new KeyStoreWrapper();
//            try {
//                com.compositesw.server.security.KeyStoreUtil
//                        .loadEffectiveDatasourceKeystores(ksw, tsw, subject,
//                                dsKeystoreMap, "data", "type", "password" );
//            } catch (Exception e) {
//                throw (IOException) new IOException(
//                        "Cannot initialize SSL protocol: "
//                                + e.getMessage()).initCause(e);
//            }
//            Object noPresentCert = connProps.get(URLReader.PROP_HTTPS_NO_PRESENT_CLIENT_CERTIFICATE);
//            KeyStore keyStore = ksw.getKeyStore();
//            if(noPresentCert != null && Boolean.valueOf(noPresentCert.toString()) && keyStore != null) {
//                keyStore = new KeyStoreWrapper().getKeyStore();
//            }
//            SSLContext sslcontext = SSLContextManager.createSSLContext(
//                    keyStore,
//                    ksw.getPassword() == null ? "" : ksw.getPassword().getEncrypted(),
//                    tsw.getKeyStore());
//            if (enableClientAuthentication) {
//                sslcontext.getDefaultSSLParameters().setNeedClientAuth(true);
//            }else{
//                sslcontext.getDefaultSSLParameters().setWantClientAuth(true);
//            }
//            SecurityUtil.setEnabledSSLProtocols(sslcontext.getDefaultSSLParameters());
//            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1", "TLSv1.1", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);
//
//            clientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
//            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register(
//                    "https", sslConnectionSocketFactory).register("http", PlainConnectionSocketFactory.getSocketFactory()).build();  //.register("http", PlainConnectionSocketFactory.getSocketFactory())
//            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
//            poolingHttpClientConnectionManager.setMaxTotal(10000);
//            poolingHttpClientConnectionManager.closeIdleConnections(10000, TimeUnit.MILLISECONDS);
//            poolingHttpClientConnectionManager.closeExpiredConnections();
//
//            clientBuilder.setConnectionManager(poolingHttpClientConnectionManager).setConnectionManagerShared(true);
//
//
//            if (!URLReader.isHostExcluded(uri.getHost(), (String) connProps
//                    .get(URLReader.PROP_HTTPS_PROXY_EXCLUSIONS))) {
//                String proxyHostname = (String) connProps
//                        .get(URLReader.PROP_HTTPS_PROXY_HOSTNAME);
//                if (proxyHostname != null) {
//                    String proxyPort = (String) connProps.get(URLReader.PROP_HTTPS_PROXY_PORT);
//                    int port = 443;
//                    try {
//                        port = Integer.parseInt(proxyPort);
//                    } catch (NumberFormatException ignore) {
//                    }
//                    if (logger.isDebug()) {
//                        logger.debug("Setting https proxy to " + proxyHostname
//                                + ":" + port);
//                    }
////                    client.getHostConfiguration().setProxy(proxyHostname, port);
//
//                    HttpHost proxy = new HttpHost(proxyHostname, port);
//                    DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//                    clientBuilder.setRoutePlanner(routePlanner);
//
//
//                    clientBuilder.setProxy(proxy);
//                    requestConfigBuilder.setProxy(proxy);
//                    String proxyUsername = (String) connProps
//                            .get(URLReader.PROP_HTTPS_PROXY_USERNAME);
//                    if (proxyUsername != null) {
//                        String proxyPassword = (String) connProps
//                                .get(URLReader.PROP_HTTPS_PROXY_PASSWORD);
//                        if (proxyPassword == null) {
//                            proxyPassword = "";
//                        } else {
//                            try {
//                                proxyPassword = EncryptedString.createFromEncryptedText(proxyPassword).getClearText();
//                            } catch (Exception e) {
//                                throw new IOException(e);
//                            }
//                        }
//                        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//                        credsProvider.setCredentials(
//                                new AuthScope(proxyHostname, port),
//                                new UsernamePasswordCredentials(proxyUsername, proxyPassword));
//                        clientBuilder.setDefaultCredentialsProvider(credsProvider);
//                    }
//                }
//            }
//        }
//
////        HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), p);
////        HostConfiguration hostConfig = client.getHostConfiguration();
////        hostConfig.setHost(host);
////        client.setHostConfiguration(hostConfig);
//
//
//
//        SecurityUtils.setDefaultSchemePriority(context, requestConfigBuilder);
//        return clientBuilder;
//    }

//}


import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class HttpClientTest {

    public static void main(String[] args) {
        try {
            extracted1();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    private static void extracted1() throws Exception{

        // Create a credentials provider
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("api.platts.com", 443), // Assuming HTTPS
                new UsernamePasswordCredentials("businessintelligence@oneok.com", "T47DZ+5wef%G")
        );

        // Create a HttpClient with Basic Authentication
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
//        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.platts.com/auth/api");

        // Set headers
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("appkey", "mYRMhJmbzIfzsESIQYYS");


        // Set form parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", "businessintelligence@oneok.com"));
        params.add(new BasicNameValuePair("password", "T47DZ+5wef%G"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);

        // Get the response status
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response status code: " + statusCode);

        // Get the response body
        HttpEntity responseEntity = response.getEntity();
        String responseBody = EntityUtils.toString(responseEntity);
        System.out.println("Response body: " + responseBody);

        // Ensure the response entity is fully consumed
        EntityUtils.consume(responseEntity);    }


    private static void extracted() {
        // Replace these values with your actual OAuth2 token and API endpoint
        String accessToken = "NiLmPNVrj0m9cDk7tCHmuw5kpnO1_-gBXumF_9Wj0go40moCqYkFwBXJssOGxL0Tro-NnCZxe_zRjFuSZxs8KxmHjQDNB204E9u_cpXDMd05IHRMz770PlXL9YmhGuiQhMpqAIHqRDEd9m6bjBX4CrhlpPpr8eVZ4se-jjvV_lhVlkPP1wp3FXUo_-7Hnym0u9g9nvKfdQ462POkTo0YOlU1X7jH9sYWkhiXlnBX4IyYchHUewXJNo1lzILFqte7cs5ChMixoWVX-HTxC0Y4acpIs4YFRXIhD1YCNnWGS4WGWh6PJ5tWE_pnxxeKIKJAO50lK-1Wyf3clysJZvyBL1CqNIHScoaeGk6ecF4PcGinsjefjPLCc0ykXt2rVdRw-SXlDDEX5FNHrht6papz_OmwxlZ5snbCu1395YMF8MpEdWRNOJ5x8m1huYjhkTWSKB9k28Tafp6ww-51AQwY4NBvatwr-GEeW1QbLTHvIvPEkyaMp553-dr9TtHUFiqY";
        String apiEndpoint = "https://collector.zeekit.no/externalapi/functiondata/getStructuredFunctionData";

        // JSON payload for the POST request (replace with your actual payload)
        String jsonPayload = "[3245068]";

        // Create an instance of CloseableHttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HTTP POST request
            HttpPost httpPost = new HttpPost(apiEndpoint);

            // Add Bearer token to the Authorization header
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

            // Add JSON payload to the request body
            httpPost.setEntity(new StringEntity(jsonPayload, "application/json", "UTF-8"));

            // Execute the request and get the response
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Print the response status code and body
                System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();
                String responseBody = entity != null ? EntityUtils.toString(entity) : "";
                System.out.println("Response Body: " + responseBody );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
