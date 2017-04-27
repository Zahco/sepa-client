package model;

import model.sepa.RootType;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by geoffrey on 26/04/17.
 */
public class RestApi {
    private JAXBContext jc;

    public RestApi() {
        try {
            jc = JAXBContext.newInstance(RootType.class);
        } catch (JAXBException je) {
            System.out.println("Cannot create JAXBContext " + je);
        }
    }

    public String getResume() {
        return sendRequest(UrlContainer.RESUME, "GET", null);
    }
    public String getHome() {
        return sendRequest(UrlContainer.HOME, "GET", null);
    }
    public String getStat() {
        return sendRequest(UrlContainer.STAT, "GET", null);
    }

    public String setDepot(RootType rt) throws JAXBException {
        return sendRequest(UrlContainer.DEPOT, "POST",new JAXBSource(jc, rt));
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
// indent response
//http://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(response));
            Document doc = db.parse(is);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//initialize StreamResult with File object to save to file
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource domsource = new DOMSource(doc);
            transformer.transform(domsource, result);
            response = result.getWriter().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
