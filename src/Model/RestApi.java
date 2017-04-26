package Model;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by geoffrey on 26/04/17.
 */
public class RestApi {
    public RestApi() {}

    public String getResume() {
        return sendRequest(UrlContainer.RESUME, "GET", null);
    }
    public String getHome() {
        return sendRequest(UrlContainer.HOME, "GET", null);
    }
    public String getStat() {
        return sendRequest(UrlContainer.STAT, "GET", null);
    }
    private String sendRequest(String url, String method, Source source) {
        String response = "";
        try {
            URL mainurl = new URL(url);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) mainurl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(method);
            if (source != null) {
                conn.setRequestProperty("Content-Type", "application/xml");
                conn.setRequestProperty("Accept", "application/xml");
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                OutputStream os = conn.getOutputStream();
                transformer.transform(source, new StreamResult(os));
                os.flush();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            // Print server response
            String output;
            while ((output = br.readLine()) != null) {
                response += output + "\n";
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
